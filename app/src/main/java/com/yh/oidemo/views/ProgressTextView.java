package com.yh.oidemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class ProgressTextView extends View {

    //进度条最大值
    private float maxCount = 100;
    //进度条当前值
    private float currentCount = 30;
    private Paint mPaint;
    private int mWidth, mHeight;
    //背景色
    private int bgColor = Color.GRAY;
    //有进度部分颜色
    private int selectColor = Color.RED;
    //文字的颜色
    private int textColor = Color.WHITE;
    private RectF bgRect;
    private RectF rectProgressBg;
    Rect bounds = new Rect();

    public ProgressTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ProgressTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    //在控件大小发生改变时调用。所以这里初始化会被调用一次
    //作用：获取控件的宽和高度
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bgRect = new RectF(0, 0, mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(bgColor);
        int round = mHeight / 2;
        // 绘制背景色
        canvas.drawRoundRect(bgRect, round, round, mPaint);

        // 绘制进度条颜色
        float section = currentCount / maxCount;
        rectProgressBg = new RectF(0, 0, (mWidth) * section, mHeight);
        if (section != 0.0f) {
            mPaint.setColor(selectColor);
        } else {
            mPaint.setColor(Color.TRANSPARENT);
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);

        // 绘制文字
        mPaint.setColor(textColor);
        mPaint.setTextSize(sp2px(9));
        String text = (int) currentCount + "/" + (int) maxCount;
        // 计算文字的矩形框大小
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        // 距离着色的进度条末尾
        float length = mWidth * section - bounds.width() - dipToPx(10);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        //基线的坐标为0，如何计算基线位置看了图了解
        //注意top是一个负数， bottom是一个正数
        int baseline = (mHeight - fontMetricsInt.bottom + fontMetricsInt.top) / 2 - fontMetricsInt.top;
        // 着色的进度条的宽度大于文字的宽带，绘制到进度条内
        if (length > 0) {
            canvas.drawText(text, length, baseline, mPaint);
        } else {
            canvas.drawText(text, mWidth * section + dipToPx(10), baseline, mPaint);
        }
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    private float sp2px(float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources()
                .getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //MeasureSpec.EXACTLY，精确尺寸
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        //MeasureSpec.AT_MOST，最大尺寸，只要不超过父控件允许的最大尺寸即可，MeasureSpec.UNSPECIFIED未指定尺寸
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(20);
        } else {
            mHeight = heightSpecSize;
        }
        //设置控件实际大小
        setMeasuredDimension(mWidth, mHeight);
    }


    /**
     * 设置当前的进度值
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = Math.min(currentCount, maxCount);
        invalidate();
    }

    public void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(8000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                currentCount = animatedValue;
                invalidate();
            }
        });
        valueAnimator.start();
    }
}