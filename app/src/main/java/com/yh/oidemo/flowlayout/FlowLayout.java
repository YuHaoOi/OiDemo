package com.yh.oidemo.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.text.TextUtilsCompat;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.View;
import android.view.ViewGroup;

import com.yh.oidemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FlowLayout extends ViewGroup {
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;

    protected List<List<View>> mAllViews = new ArrayList<>();
    protected List<Integer> mLineHeight = new ArrayList<>();
    protected List<Integer> mLineWidth = new ArrayList<>();
    private int mGravity;
    private List<View> lineViews = new ArrayList<>();

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        mGravity = ta.getInt(R.styleable.FlowLayout_tag_gravity, LEFT);
        int layoutDirection = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
        if (layoutDirection == LayoutDirection.RTL) {
            if (mGravity == LEFT) {
                mGravity = RIGHT;
            } else {
                mGravity = LEFT;
            }
        }
        ta.recycle();
    }

    /**
     * 在onMeasure里，测量所有子View的宽高，以及确定ViewGroup自己的宽高。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取系统传递过来测量出的宽度 高度，以及相应的测量模式。
        //如果测量模式为 EXACTLY( 确定的dp值，match_parent)，则可以调用setMeasuredDimension()设置，
        //如果测量模式为 AT_MOST(wrap_content),则需要经过计算再去调用setMeasuredDimension()设置
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // wrap_content情况下存储最后计算出的宽度和高度
        int width = 0;
        int height = 0;

        // 当前行的宽度
        int lineWidth = 0;
        // 当前行的高度
        int lineHeight = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            //先测量子View
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            //获取子View的LayoutParams，(子View的LayoutParams的对象类型，取决于其ViewGroup的generateLayoutParams()方法的返回的对象类型，这里返回的是MarginLayoutParams)
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //如果当前的行宽度大于 父控件允许的最大宽度 则要换行
            //父控件允许的最大宽度 如果要适配 padding 这里要- getPaddingLeft() - getPaddingRight()
            //即为测量出的宽度减去父控件的左右边距
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //通过比较当前行宽和以前存储的最大行宽,得到最新的最大行宽,用于设置父控件的宽度
                width = Math.max(width, lineWidth);
                //父控件的高度增加了，为当前高度+当前行的高度
                height += lineHeight;
                //换行后刷新当前行宽高数据：因为新的一行就这一个View，所以为当前这个view占用的宽高
                lineWidth = childWidth;
                lineHeight = childHeight;
            } else {
                //不换行：叠加当前行宽和比较当前行高:
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //如果已经是最后一个View,要比较当前行的宽度和最大宽度，叠加一共的高度
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        //适配padding,如果是wrap_content,则除了子控件本身占据的控件，还要在加上父控件的padding
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );

    }

    /**
     * 布局父控件位置以及子控件的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        mLineWidth.clear();
        lineViews.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            //如果当前行已经放不下该子View了 需要换行放置
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                mLineHeight.add(lineHeight);
                mLineWidth.add(lineWidth);
                mAllViews.add(lineViews);

                // 换行后，新行的开始宽高位置
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                // 存放当前行的控件
                lineViews = new ArrayList<>();
            }
            // 计算新增一个控件后，当前行的宽高
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);

        }

        // 存放每行的高度
        mLineHeight.add(lineHeight);
        // 存放每行的宽度
        mLineWidth.add(lineWidth);
        // 存放每行的控件列表
        mAllViews.add(lineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        for (int i = 0, lineNum = mAllViews.size(); i < lineNum; i++) {
            // 第i行的控件列表
            lineViews = mAllViews.get(i);
            // 第i行的高度
            lineHeight = mLineHeight.get(i);
            // 第i行的宽度
            int currentLineWidth = mLineWidth.get(i);
            switch (mGravity) {
                case LEFT:
                    left = getPaddingLeft();
                    break;
                case CENTER:
                    left = (width - currentLineWidth) / 2 + getPaddingLeft();
                    break;
                case RIGHT:
                    // 适配了rtl，需要补偿一个padding值
                    left = width - (currentLineWidth + getPaddingLeft()) - getPaddingRight();
                    // 适配了rtl，需要把lineViews里面的数组倒序排
                    Collections.reverse(lineViews);
                    break;
            }

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                //布局子View
                child.layout(lc, tc, rc, bc);

                // 放了一个子View后，计算当前行新的左边起点
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            // 一行放完了，新增高度放下一行
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}