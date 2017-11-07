package com.sleepy.box.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.sleepy.box.R;

/**
 * Created by gehou on 2017/9/26.
 */

public class WaveView extends View {
    private Context mContext;
    /**
     * y=A*sin(ωx+φ)+k
     * <p>
     * A—振幅越大，波形在y轴上最大与最小值的差值越大
     * ω—角速度， 控制正弦周期(单位角度内震动的次数)
     * φ—初相，反映在坐标系上则为图像的左右移动。这里通过不断改变φ,达到波浪移动效果
     * k—偏距，反映在坐标系上则为图像的上移或下移。
     */
    private float φ;
    private double ω;
    private int A;
    private int K;

    private float waveSpeed = 3f;
    private int waveColor = 0xaa9966CC;
    private double offset;

    /**
     * 是否直接开启波形
     */
    private boolean waveStart;

    private Path path;
    private Paint paint;

    private static final int TOP = 0;
    private static final int BOTTOM = 1;

    private int waveFillType;

    private ValueAnimator valueAnimator;

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttrs(attrs);
        K=A;
        initPaint();
        initAnimation();
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.RadarWaveView);

        waveFillType = array.getInt(R.styleable.RadarWaveView_waveFillType, BOTTOM);
        A = array.getDimensionPixelOffset(R.styleable.RadarWaveView_waveAmplitude, dp2px(10));
        waveColor = array.getColor(R.styleable.RadarWaveView_waveColor, waveColor);
        waveSpeed = array.getFloat(R.styleable.RadarWaveView_waveSpeed, waveSpeed);
        offset = array.getFloat(R.styleable.RadarWaveView_waveOffset, 0);
        waveStart = array.getBoolean(R.styleable.RadarWaveView_waveStart, true);

        array.recycle();
    }

    private void initPaint() {
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(waveColor);
    }

    private void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, getWidth());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });
        if (waveStart) {
            valueAnimator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSin(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ω = 2 * Math.PI / getWidth();
    }

    private void drawSin(Canvas canvas) {
        switch (waveFillType) {
            case TOP:
                fillTop(canvas);
                break;
            case BOTTOM:
                fillBottom(canvas);
                break;
        }
    }

    private void fillTop(Canvas canvas) {
        φ -= waveSpeed/100;
        float y;

        path.reset();
        path.moveTo(0,getHeight());

        for (float x = 0;x<=getWidth();x+=5){
            y = (float) (A*Math.sin(ω*x+φ+offset)+K);
            path.lineTo(x,getHeight()-y);
        }

        path.lineTo(getWidth(),0);
        path.lineTo(0,0);
        path.close();

        canvas.drawPath(path,paint);
    }

    private void fillBottom(Canvas canvas) {
        φ -= waveSpeed/100;
        float y;

        path.reset();
        path.moveTo(0,0);

        for (float x = 0;x<=getWidth();x+=20){
            y = (float) (A*Math.sin(ω*x+φ+offset )+K);
            path.lineTo(x,y);
        }

        path.lineTo(getWidth(),getHeight());
        path.lineTo(0,getHeight());
        path.close();

        canvas.drawPath(path,paint);
    }

    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

}
