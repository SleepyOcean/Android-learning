package com.sleepy.box.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by gehou on 2017/9/27.
 */

public class PathTestView extends View {
    float distance;
    int currentView;

    public PathTestView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentView++;
                currentView %= 3;
                invalidate();
            }
        });
        loadingAnim();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.translate(getWidth() / 2, getHeight() / 2);


        switch (currentView) {
            case 0:
                loadingAnim(canvas, paint);
                break;
            case 1:
                rectFTest(canvas, paint);
                break;
            case 2:
                getTang(canvas, paint);
                break;
        }


    }

    private void getTang(Canvas canvas, Paint paint) {
        Path path = new Path();
        int radius = 100;
        path.addArc(-radius, -radius, radius, radius, 150, 359.9f);
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);

        Path triangle1 = new Path();
        float[] pos = new float[2];
        pathMeasure.getPosTan(0, pos, null);
        triangle1.moveTo(pos[0], pos[1]);
        pathMeasure.getPosTan((1f / 3f) * pathMeasure.getLength(), pos, null);
        triangle1.lineTo(pos[0], pos[1]);
        pathMeasure.getPosTan((2f / 3f) * pathMeasure.getLength(), pos, null);
        triangle1.lineTo(pos[0], pos[1]);
        triangle1.close();

        pathMeasure.setPath(triangle1, false);
        Path dst = new Path();
        float stopD = distance * pathMeasure.getLength();
        float startD = 0;
        pathMeasure.getSegment(startD, stopD, dst, true);

        canvas.drawPath(dst, paint);
    }

    private void loadingAnim(Canvas canvas, Paint paint) {
        Path path = new Path();
        int radius = 100;
        path.addArc(-radius, -radius, radius, radius, 0, 359.9f);

        Path dst = new Path();
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);
        float length = pathMeasure.getLength();
        float stopD = distance * length;
        float startD = stopD - (((4 * radius) / length) * stopD - ((4 * radius) / (length * length)) * (stopD * stopD)); //转动条长度随stopD呈抛物线变化

        pathMeasure.getSegment(startD, stopD, dst, true);

        canvas.drawPath(dst, paint);

    }

    private void loadingAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                distance = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        };
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.start();
    }

    private void rectFTest(Canvas canvas, Paint paint) {
        Path path = new Path();
        RectF rect = new RectF(-220, -220, 220, 220);
        path.addRect(rect, Path.Direction.CW);
        Path circlePath = new Path();
        circlePath.addArc(rect, 150, 359.9f);

        paint.setTextSize(60);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("信用良好",0,0,paint);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        Path dst = new Path();
        Path dstBg = new Path();
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(circlePath,false);
        pathMeasure.getSegment(0,2*distance*pathMeasure.getLength()/3,dst,true);
        pathMeasure.getSegment(0,2*pathMeasure.getLength()/3,dstBg,true);

        paint.setColor(Color.LTGRAY);
        canvas.drawPath(dstBg,paint);

        paint.setColor(Color.CYAN);
        canvas.drawPath(dst, paint);

    }

    private void pathMeasureTest(Canvas canvas, Paint paint) {
        Path path1 = new Path();
        path1.addRect(300, 300, 700, 700, Path.Direction.CW);
        canvas.drawPath(path1, paint);


        PathMeasure pathMeasure = new PathMeasure();

        pathMeasure.setPath(path1, false);

        Path dstPath = new Path();
        pathMeasure.getSegment(0, 1200, dstPath, false);
        paint.setColor(Color.BLACK);
        canvas.drawPath(dstPath, paint);
    }
}
