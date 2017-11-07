package com.sleepy.tiledesign;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gehou on 2017/10/9.
 */

public class EditorLayout extends RelativeLayout implements View.OnClickListener {
    private int lengthOfSide;
    private EditorLayoutParams params = new EditorLayoutParams();
    private List<ItemPiece> list;
    private String defaultImagePath;

    int num;
    boolean once;

    public EditorLayout(Context context) {
        super(context);
    }

    public EditorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        lengthOfSide = Math.min(getMeasuredHeight(), getMeasuredWidth());
        log("getWidth:  " + getMeasuredWidth() + "  ,   getHeight:  " + getMeasuredHeight());
        if (num == 0) {
            if (SleepyUtil.isLoad) {
                loadSingleEditWork();
            }
            if (!once) {
                init();
                once = true;
            }
        }
        num++;
        setMeasuredDimension(lengthOfSide, lengthOfSide);
    }

    private void init() {
        log("刷新界面");
        initSquareItem();
//        orderNormalize();
    }

    private void orderNormalize() {
        for (int i = 0; i < list.size(); i++) {
            ImageView image = (ImageView) getChildAt(i);
            ViewGroup.LayoutParams param = image.getLayoutParams();
            if (param == null) {
                log("null");
            }
            log("width+" + params.itemWidth);
            log("width=" + param.width);
            param.width = params.itemWidth;
            param.height = (list.get(i).getImageHeight() / list.get(i).getImageWidth()) * params.itemWidth;
            image.setLayoutParams(param);
            image.invalidate();
        }
    }

    private void resetParams() {
        num = 0;
        once = false;

        removeAllViews();
    }

    public void setOffset(int progress) {
        log("progress:>>" + progress);

    }

    public void setCol(int col) {
        params.setColumns(col);
        resetParams();
    }

    private void initSquareItem() {
        params.itemWidth = (lengthOfSide - params.margin * (params.columns - 1)) / params.columns;
        if (list == null) {
            setDefaultList();
        }
        if (list.size() != (params.row * params.columns)) {
            setDefaultList();
        }
        ImageView[] imageViews = new ImageView[params.row * params.columns];
        for (int i = 0; i < list.size(); i++) {
            ImageView imageItem = new ImageView(getContext());
            imageItem.setImageBitmap(list.get(i).getBitmap());
            ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(params.itemWidth, (list.get(i).getImageHeight() / list.get(i).getImageWidth()) * params.itemWidth);
            imageItem.setLayoutParams(param);


            imageItem.setOnClickListener(this);
            imageViews[i] = imageItem;
            imageItem.setId(i + 1);

            LayoutParams layoutParams = new LayoutParams(params.itemWidth, params.itemWidth);

            if ((i + 1) % params.columns != 0) {
                layoutParams.rightMargin = params.margin;
            }
            if (i % params.columns != 0) {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, imageViews[i - 1].getId());
            }
            if ((i + 1) > params.columns) {
                layoutParams.topMargin = params.margin;
                layoutParams.addRule(RelativeLayout.BELOW, imageViews[i - params.columns].getId());
            }
            addView(imageItem, layoutParams);
        }
        if (SleepyUtil.isLoad) {
            for (int i = 0; i < params.columns * params.columns; i++) {
                if (list.get(i).getRotatedState() != 0) {
                    rotateAnimation(getChildAt(i), true, 0);
                }
            }
            SleepyUtil.isLoad = false;
        }
    }

    private void setDefaultList() {
        list = new ArrayList<>();
        for (int i = 0; i < params.row * params.columns; i++) {
            ItemPiece item = new ItemPiece(i, defaultImagePath, 0);
            list.add(item);
        }
    }

    public void setDefaultPath(String path) {
        defaultImagePath = path;
    }

    public void saveSingleEditWork(String title) {
        PreferenceHelper.putString("name" + SleepyUtil.CurrentNum, title, getContext());
        PreferenceHelper.putString("time" + SleepyUtil.CurrentNum, SleepyUtil.getCurrentTime(), getContext());
        PreferenceHelper.putString("path" + SleepyUtil.CurrentNum, defaultImagePath, getContext());
        PreferenceHelper.putInt("column" + SleepyUtil.CurrentNum, params.columns, getContext());
        for (int i = 0; i < list.size(); i++) {
            PreferenceHelper.putInt("tangle" + SleepyUtil.CurrentNum + "_" + i, list.get(i).getRotatedState(), getContext());
            log("save tangle:   " + i + "   " + list.get(i).getRotatedState());
        }
        SleepyUtil.CurrentNum++;
        PreferenceHelper.putInt("currentNum", SleepyUtil.CurrentNum, getContext());
    }

    public void loadSingleEditWork() {
        list = new ArrayList<>();
        defaultImagePath = PreferenceHelper.getString("path" + (SleepyUtil.CurrentNum - SleepyUtil.position - 1), getContext());
        params.setColumns(PreferenceHelper.getInt("column" + (SleepyUtil.CurrentNum - SleepyUtil.position - 1), getContext()));
        for (int i = 0; i < params.columns * params.columns; i++) {
            ItemPiece item = new ItemPiece(i, defaultImagePath, PreferenceHelper.getInt("tangle" + (SleepyUtil.CurrentNum - SleepyUtil.position - 1) + "_" + i, getContext()));
            list.add(item);
        }
    }

    private void rotateAnimation(View v, boolean isLoad, int tangle) {

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimSmall = new ScaleAnimation(1.0f, (float) Math.sin(Math.PI / 4), 1.0f, (float) Math.sin(Math.PI / 4), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimSmall.setDuration(300);
        scaleAnimSmall.setFillAfter(true);

        ScaleAnimation scaleAnimBig = new ScaleAnimation(1.0f, 1.0f / (float) Math.sin(Math.PI / 4), 1.0f, 1.0f / (float) Math.sin(Math.PI / 4), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimBig.setDuration(300);
        scaleAnimBig.setStartOffset(600);
        scaleAnimBig.setFillAfter(true);

        RotateAnimation anim;
        if (isLoad) {
            anim = new RotateAnimation(0, list.get(v.getId() - 1).getRotatedState() * 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            anim = new RotateAnimation(list.get(v.getId() - 1).getRotatedState() * 90, list.get(v.getId() - 1).getRotatedState() * 90 + tangle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        anim.setDuration(300);
        anim.setStartOffset(300);
        anim.setFillAfter(false);

        animationSet.addAnimation(scaleAnimSmall);
        animationSet.addAnimation(anim);
        animationSet.addAnimation(scaleAnimBig);
        animationSet.setFillAfter(true);

        v.startAnimation(animationSet);
        if (isLoad) return;
        list.get(v.getId() - 1).setNextState();
    }

    @Override
    public void onClick(View v) {
        if (list.get(v.getId() - 1).isSquare()) {
            rotateAnimation(v, false, 90);
        } else {
            rotateAnimation(v, false, 180);
        }
    }

    private class EditorLayoutParams {
        private int columns = 3;
        private int row = 3;
        private int itemWidth;
        private int offset = 0;
        private int margin = 6;

        public void setColumns(int columns) {
            this.columns = columns;
            row = columns;
            itemWidth = (lengthOfSide - margin * (columns - 1)) / columns;
        }

        @Override
        public String toString() {
            return "column:" + columns
                    + "      itemWidth:" + itemWidth
                    + "      margin:" + margin;
        }
    }

    private void log(String s) {
        Log.d("TAG", "log: " + s);
    }

}
