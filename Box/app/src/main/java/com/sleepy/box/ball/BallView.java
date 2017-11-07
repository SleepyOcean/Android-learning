package com.sleepy.box.ball;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by gehou on 2017/10/11.
 */

public class BallView extends View {
    Paint paint;
    private ValueAnimator valueAnimator;
    int x;
    int y;
    int firstX;
    int firstY;
    int vx;
    int vy;
    int t;
    int r;
    int width;
    int height;
    private int acc = 5;
    private int g = 1;
    boolean isTouchDown;

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public void setG(int g) {
        this.g = g;
    }

    public BallView(Context context) {
        super(context);
    }


    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.CYAN);
        initAnimation();
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        x = width / 2;
        y = height / 2;
        vx = 15;
        vy = 15;
        t = 0;
        r = 100;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("TAG", "onTouchEvent: >>>>>>>>>>>>>>>>>>" + "X:    " + x+ "   Y:   " + y);
        canvas.drawCircle(x, y, r, paint);
    }

    private void calculateCoordinate() {
        vy = vy + g * t;
        x = x + vx * t;
        y = y + vy * t;
        if (y <= r || y >= height - r) {
            vy = -vy;
            if (y <= r) {
                y = r;
            }
            if (y >= height - r) {
                y = height - r;
            }
        }
        if (x <= r || x >= width - r) {
            vx = -vx;
            if (x <= r) {
                x = r;
            }
            if (x >= width - r) {
                x = width - r;
            }
        }
        t = (int) valueAnimator.getAnimatedValue();
//        Log.d("TAG", "calculateCoordinate: >>>>>>>>>>>>>>."+"vx=" + vx + "    vy=" + vy);
//        Log.d("TAG", "calculateCoordinate: >>>>>>>>>>>>>>."+"x=" + x + "    y=" + y);
    }

    private void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, 10);
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new CustomInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if(!isTouchDown){
                    calculateCoordinate();
                    invalidate();
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstX = X;
                firstY = Y;
                isTouchDown = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isTouchDown = false;
                vx = (int) ((x - firstX) / (event.getEventTime() - event.getDownTime())) * acc;
                vy = (int) ((y - firstY) / (event.getEventTime() - event.getDownTime())) * acc;

                break;
            default:
                break;
        }
        x = X;
        y = Y;
        invalidate();
        return true;
    }

    private class CustomInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float v) {
            return 0.2f;
        }
    }
}
