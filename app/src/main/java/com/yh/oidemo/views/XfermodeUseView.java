package com.yh.oidemo.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class XfermodeUseView extends View {
    /**
     * 文字图片
     */
    private Bitmap mTextBitmap = null;
    /**
     * 文字Canvas
     */
    private Canvas mTextCanvas = null;

    /**
     * 光效图片
     */
    private Bitmap mLightBitmap = null;
    /**
     * 光效Canvas
     */
    private Canvas mLightCanvas = null;

    private Paint mTextPaint;
    private Paint mLightPaint;
    private Paint mPaint;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public XfermodeUseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(40);
        mTextPaint.setColor(Color.BLACK);
        mLightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLightPaint.setColor(Color.RED);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        // 为了简单，这里创建的图片都是整个屏幕那么大
        mTextBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mTextCanvas = new Canvas(mTextBitmap);
        // 在中间画一段文字
        String text = "红红火火恍恍惚惚";
        float textSize = mTextPaint.measureText(text);
        mTextCanvas.drawText(text, (width - textSize) / 2, height / 2, mTextPaint);
        mLightBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mLightCanvas = new Canvas(mLightBitmap);
        // 画光效，其实就是一个红色的圆
        mLightCanvas.drawCircle(width / 2, height / 2, 70, mLightPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 先画一次原文字
        canvas.drawBitmap(mTextBitmap, 0, 0, mPaint);
        // 保存画布
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.ALL_SAVE_FLAG);
        // 画光效的文字, 除文字外，其余是透明的图片，这个已经在画布上了，是目标像素DST
        canvas.drawBitmap(mTextBitmap, 0, 0, mPaint);
        mPaint.setXfermode(mXfermode);
        // 设置了Xfermode, 绘制的红色图片在内存中，是源像素SRC，要与目标像素合成
        // 这里是SRC_IN[Sa * Da, Sc * Da]=[合成透明度, 合成颜色]
        // 效果是：除文字外，全透明，文字部分：Da不透明，颜色用Sc是红色
        canvas.drawBitmap(mLightBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
