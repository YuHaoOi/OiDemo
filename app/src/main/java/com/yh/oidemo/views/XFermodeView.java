package com.yh.oidemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

// https://blog.csdn.net/iispring/article/details/50472485
public class XFermodeView extends View {

    private final Paint paint;

    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    public XFermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置背景色
        canvas.drawColor(Color.BLUE);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        int r = getWidth() / 3;
        //正常绘制黄色的圆形
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(r, r, r, paint);
        //使用CLEAR作为PorterDuffXfermode绘制绿色的矩形
        paint.setXfermode(xfermode);
        paint.setColor(Color.GREEN);
        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);
        //最后将画笔去除Xfermode
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}
