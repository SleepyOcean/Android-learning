package com.sleepy.box.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gehou on 2017/9/26.
 */

public class LogoView extends View {
    private Paint paint;

    private Path outerCircle;
    private Path innerCircle;
    private Path triangle1;
    private Path triangle2;
    private Path drawPath;

    private PathMeasure pathMeasure;

    private long duration = 3000;
    private ValueAnimator valueAnimator;

    private float distance;

    private Handler handler;
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private Animator.AnimatorListener animatorListener;

    private float width;
    private float height;
    private State currentState;

    private enum State {
        CIRCLE,
        TRIANGLE,
        FINISH
    }


    public LogoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.save();
        canvas.translate(width / 2, height / 2);
        switch (currentState) {
            case CIRCLE:
                drawPath.reset();
                pathMeasure.setPath(innerCircle,false);
                pathMeasure.getSegment(0,distance*pathMeasure.getLength(),drawPath,true);
                canvas.drawPath(drawPath,paint);
                pathMeasure.setPath(outerCircle,false);
                drawPath.reset();
                pathMeasure.getSegment(0,distance*pathMeasure.getLength(),drawPath,true);
                canvas.drawPath(drawPath,paint);
                break;
            case TRIANGLE:
                canvas.drawPath(innerCircle,paint);
                canvas.drawPath(outerCircle,paint);
                drawPath.reset();
                pathMeasure.setPath(triangle1,false);
                float stopD = distance*pathMeasure.getLength();
                float startD = stopD - (0.5f - Math.abs(0.5f-distance))*200;
                pathMeasure.getSegment(startD,stopD,drawPath,true);
                canvas.drawPath(drawPath,paint);
                drawPath.reset();
                pathMeasure.setPath(triangle2,false);
                pathMeasure.getSegment(startD,stopD,drawPath,true);
                canvas.drawPath(drawPath,paint);
                break;
            case FINISH:
                canvas.drawPath(innerCircle,paint);
                canvas.drawPath(outerCircle,paint);
                drawPath.reset();
                pathMeasure.setPath(triangle1,false);
                pathMeasure.getSegment(0,distance*pathMeasure.getLength(),drawPath,true);
                canvas.drawPath(drawPath,paint);
                drawPath.reset();
                pathMeasure.setPath(triangle2,false);
                pathMeasure.getSegment(0,distance*pathMeasure.getLength(),drawPath,true);
                canvas.drawPath(drawPath,paint);
                break;
        }
        canvas.restore();
    }

    private void init() {
        initPaint();
        initPath();
        initHandler();
        initAnimatorListener();
        initAnimator();

        currentState = State.CIRCLE;
        valueAnimator.start();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.BEVEL);
        paint.setShadowLayer(15, 0, 0, Color.YELLOW);
    }

    private void initPath() {
        outerCircle = new Path();
        innerCircle = new Path();
        triangle1 = new Path();
        triangle2 = new Path();
        drawPath = new Path();

        pathMeasure = new PathMeasure();

        RectF innerRect = new RectF(-220, -220, 220, 220);
        RectF outerRect = new RectF(-280, -280, 280, 280);
        innerCircle.addArc(innerRect, 150, -359.9f);
        outerCircle.addArc(outerRect, 60, -359.9f);

        pathMeasure.setPath(innerCircle, false);

        float[] pos = new float[2];
        pathMeasure.getPosTan(0, pos, null);
        triangle1.moveTo(pos[0], pos[1]);
        pathMeasure.getPosTan((1f / 3f) * pathMeasure.getLength(), pos, null);
        triangle1.lineTo(pos[0], pos[1]);
        pathMeasure.getPosTan((2f / 3f) * pathMeasure.getLength(), pos, null);
        triangle1.lineTo(pos[0], pos[1]);
        triangle1.close();

        pathMeasure.getPosTan((2f / 3f) * pathMeasure.getLength(), pos, null);
        Matrix matrix = new Matrix();
        matrix.postRotate(-180);
        triangle1.transform(matrix, triangle2);

    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (currentState) {
                    case CIRCLE:
                        currentState = State.TRIANGLE;
                        valueAnimator.start();
                        break;
                    case TRIANGLE:
                        currentState = State.FINISH;
                        valueAnimator.start();
                        break;
                }
            }
        };
    }


    private void initAnimatorListener() {
        animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                distance = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        };

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
    }

    private void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(duration);
        valueAnimator.addUpdateListener(animatorUpdateListener);
        valueAnimator.addListener(animatorListener);
    }


}
