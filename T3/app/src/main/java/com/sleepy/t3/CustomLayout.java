/*
 * Created by sleepy on 17-2-4 下午7:43
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gehou on 2016/12/28.
 */

public class CustomLayout extends RelativeLayout implements View.OnClickListener, View.OnLongClickListener {
    private int mMargin = 3;
    private int mPadding;

    private int num = 0;


    public int getColumn() {
        return mColumn;
    }

    private int mColumn = 4;

    public void setmColumn(int mColumn) {
        this.mColumn = mColumn;
        resetParams();

    }

    private void resetParams() {
        num = 0;
        once = false;

        removeAllViews();
    }

    private int mWidth;
    private int mItemWidth;
    private ImageView[] mItems;
    private boolean once;

    public List<ItemPiece> getItemBitmaps() {
        return mItemBitmaps;
    }

    private List<ItemPiece> mItemBitmaps;


    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    private Bitmap mBitmap;
    private Bitmap mNewBitmap;


//    private float currentTangle[] = new float[100];

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
        if (num == 0) {
            if (!once) {
                initBitmap();
                initItem();
                if(Util.isLoad) {
                    loadTangleRecord(Util.tangle);
                    loadRecord();
                }
                once = true;
//                Log.d("TAG", "onMeasure: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        num++;
//        Log.d("TAG", "onMeasure:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> times="+num);
        setMeasuredDimension(mWidth, mWidth);

    }

    private void init() {
        mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        mPadding = min(getPaddingLeft(), getPaddingRight(), getPaddingBottom(), getPaddingTop());


    }

    private int min(int... params) {
        int min = params[0];

        for (int param : params) {
            if (param < min)
                min = param;
        }
        return min;
    }

    private void initItem() {
        mItemWidth = (mWidth - mPadding * 2 - mMargin * (mColumn - 1))
                / mColumn;
        mItems = new ImageView[mColumn * mColumn];

        for (int i = 0; i < mItems.length; i++) {
            ImageView item = new ImageView(getContext());
            item.setOnClickListener(this);
            item.setOnLongClickListener(this);
            item.setImageBitmap(mItemBitmaps.get(i).getBitmap());

            mItems[i] = item;
            item.setId(i + 1);


            item.setTag(i + "_" + mItemBitmaps.get(i).getIndex());

            LayoutParams lp = new LayoutParams(
                    mItemWidth, mItemWidth);


            if ((i + 1) % mColumn != 0) {
                lp.rightMargin = mMargin;
            }

            if (i % mColumn != 0) {
                lp.addRule(RelativeLayout.RIGHT_OF,
                        mItems[i - 1].getId());
            }

            if ((i + 1) > mColumn) {
                lp.topMargin = mMargin;
                lp.addRule(RelativeLayout.BELOW,
                        mItems[i - mColumn].getId());
            }
            addView(item, lp);
        }
    }

    private void initBitmap() {
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tiletest2);
        }

        mItemBitmaps = ItemPieceUtil.splitImage(mBitmap, mColumn);

    }

    @Override
    public void onClick(View v) {
        rotateAnimation(v);

//        rotateImg(v.getId() - 1, 90);

//        rotateImgByopenCV(v.getId()-1);
//        mItemBitmaps = ItemPieceUtil.setImageView(mColumn,mItemBitmaps.get(v.getId()));
//        initItem();
    }

    public void loadRecord() {
        for (int i = 0; i < mItemBitmaps.size(); i++) {
            if ((int) mItemBitmaps.get(i).getCurrentTangle() != 0)
                rotateAnimation(getChildAt(i),mItemBitmaps.get(i).getCurrentTangle());
//            rotateImg(i,(int)mItemBitmaps.get(i).getCurrentTangle());
        }
    }

    public void resetTangle() {
        for (int i = 0; i < mItemBitmaps.size(); i++) {
            mItemBitmaps.get(i).setCurrentTangle(0);
        }
    }

    public void loadTangleRecord(int[] tang) {
        for (int i = 0; i < mItemBitmaps.size(); i++) {
            mItemBitmaps.get(i).setCurrentTangle((float) tang[i]);
//            Log.d("TAG", "loadTangleRecord: #####################>>mItemBitmaps.get(i).getCurrentTangle():" + mItemBitmaps.get(i).getCurrentTangle());
//            Log.d("TAG", "loadTangleRecord: #####################>>tang:" + tang[i]);
//            Log.d("TAG", "loadTangleRecord: #####################>>Util.tangle:" + Util.tangle[i]);
        }
    }

    private void rotateAnimation(final View v,float tangle) {

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimSmall = new ScaleAnimation(1.0f, (float) Math.sin(Math.PI / 4), 1.0f, (float) Math.sin(Math.PI / 4), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimSmall.setDuration(300);
        scaleAnimSmall.setFillAfter(true);

        ScaleAnimation scaleAnimBig = new ScaleAnimation(1.0f, 1.0f / (float) Math.sin(Math.PI / 4), 1.0f, 1.0f / (float) Math.sin(Math.PI / 4), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimBig.setDuration(300);
        scaleAnimBig.setStartOffset(600);
        scaleAnimBig.setFillAfter(true);

        RotateAnimation anim = new RotateAnimation(0,  tangle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(300);
        anim.setStartOffset(300);
        anim.setFillAfter(false);

        animationSet.addAnimation(scaleAnimSmall);
        animationSet.addAnimation(anim);
        animationSet.addAnimation(scaleAnimBig);
        animationSet.setFillAfter(true);

        v.startAnimation(animationSet);

    }

    private void rotateAnimation(final View v) {

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimSmall = new ScaleAnimation(1.0f, (float) Math.sin(Math.PI / 4), 1.0f, (float) Math.sin(Math.PI / 4), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimSmall.setDuration(300);
        scaleAnimSmall.setFillAfter(true);

        ScaleAnimation scaleAnimBig = new ScaleAnimation(1.0f, 1.0f / (float) Math.sin(Math.PI / 4), 1.0f, 1.0f / (float) Math.sin(Math.PI / 4), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimBig.setDuration(300);
        scaleAnimBig.setStartOffset(600);
        scaleAnimBig.setFillAfter(true);

        RotateAnimation anim = new RotateAnimation(mItemBitmaps.get(v.getId() - 1).getCurrentTangle(), mItemBitmaps.get(v.getId() - 1).getCurrentTangle() + 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(300);
        anim.setStartOffset(300);
        anim.setFillAfter(false);

        animationSet.addAnimation(scaleAnimSmall);
        animationSet.addAnimation(anim);
        animationSet.addAnimation(scaleAnimBig);
        animationSet.setFillAfter(true);

        v.startAnimation(animationSet);

        mItemBitmaps.get(v.getId() - 1).setCurrentTangle(mItemBitmaps.get(v.getId() - 1).getCurrentTangle() + 90);
    }

    public void rotateImg(int itemId, final int orientationDegree) {
        Matrix m = new Matrix();
        mBitmap = mItemBitmaps.get(itemId).getBitmap();
        m.setRotate(orientationDegree, (float) mBitmap.getWidth() / 2, (float) mBitmap.getHeight() / 2);
        try {
            mNewBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), m, true);
            mItemBitmaps.get(itemId).setBitmap(mNewBitmap);
//            System.out.println("完成设置" + itemId);
        } catch (OutOfMemoryError ex) {
        }
    }


//    public void rotateImgByopenCV(int itemId){
//        System.out.println("进入旋转方法");
//        Mat matOriginal = new Mat();
//        Mat matRotate = new Mat();
//        Mat matHandled = new Mat();
//        mBitmap = mItemBitmaps.get(itemId).getBitmap();
//        mNewBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Utils.bitmapToMat(mBitmap,matOriginal);
//
//
//        System.out.println("开始旋转");
//
//        matRotate = Imgproc.getRotationMatrix2D(new Point(matOriginal.rows()/2,matOriginal.cols()/2),90,1);
//        Imgproc.warpAffine(matOriginal,matHandled,matRotate,matHandled.size());
//        System.out.println("完成旋转");
//
//        Utils.matToBitmap(matHandled,mNewBitmap);
//
//        System.out.println("开始设置");
//        mItemBitmaps.get(itemId).setBitmap(mNewBitmap);
//        System.out.println("完成设置");
//    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(getContext(), "Holo", Toast.LENGTH_SHORT).show();
        return true;
    }

}
