package com.yh.oidemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class PathPainterEffect extends View {

    private Paint mPaint;
    private Path mPath;
    private PathEffect mEffect;
    private float fraction = 0;
    private float length;

    public PathPainterEffect(Context context) {
        super(context);
    }

    public PathPainterEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(100, 100);
        mPath.lineTo(100, 500);
        mPath.lineTo(400, 300);
        mPath.close();

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        PathMeasure mPathMeasure = new PathMeasure(mPath, false);
        length = mPathMeasure.getLength();
    }

    public PathPainterEffect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    public void startAnim() {
        ValueAnimator mAnimator = ValueAnimator.ofFloat(1, 0);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(2000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                fraction = (float) valueAnimator.getAnimatedValue();
                //利用偏移量实现部分绘制效果
                mEffect = new DashPathEffect(new float[]{length, length}, fraction * length);
                mPaint.setPathEffect(mEffect);
                invalidate();
            }
        });
        mAnimator.start();
    }
}
