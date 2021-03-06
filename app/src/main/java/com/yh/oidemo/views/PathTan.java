package com.yh.oidemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

public class PathTan extends View {

    private Path mPath;
    private float[] pos;
    private float[] tan;
    private Paint mPaint;
    float currentValue = 0;
    private PathMeasure mMeasure;

    public PathTan(Context context) {
        super(context);
    }

    public PathTan(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mMeasure = new PathMeasure();
        mPath.addCircle(0, 0, 200, Path.Direction.CW);
        mMeasure.setPath(mPath, false);
        pos = new float[2];
        tan = new float[2];
    }

    public PathTan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMeasure.getPosTan(mMeasure.getLength() * currentValue, pos, tan);
        // 某个点的切线逆时针方向与x轴的角度
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);

        canvas.save();
        canvas.translate(400, 400);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(pos[0], pos[1], 10, mPaint);
        canvas.rotate(degrees); // 旋转以后，这个点就在y轴（0， -200）的位置
        canvas.drawLine(0, -200, 300, -200, mPaint);
        canvas.restore();
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
}
