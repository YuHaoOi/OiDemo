package com.yh.oidemo.flowlayout;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

public class TagView extends FrameLayout implements Checkable {
    private boolean isChecked;

    /**
     * 代表选中状态的集合
     */
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    public TagView(Context context) {
        super(context);
    }

    public View getTagView() {
        return getChildAt(0);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        // 我们要把状态给加进去，就是上面定义的状态
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            // 如果选中，将父类的结果和选中状态合并之后返回
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            // 刷新子控件的选中状态，最终调用onCreateDrawableState刷新状态
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}