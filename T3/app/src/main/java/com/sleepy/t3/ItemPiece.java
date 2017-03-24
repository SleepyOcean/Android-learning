/*
 * Created by sleepy on 17-2-4 下午7:43
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.graphics.Bitmap;

public class ItemPiece {
    private int index ;
    private Bitmap bitmap ;

    public float getCurrentTangle() {
        return currentTangle;
    }

    public void setCurrentTangle(float currentTangle) {
        this.currentTangle = currentTangle;
    }

    private float currentTangle;

    public ItemPiece()
    {
        currentTangle=0;
    }

    public ItemPiece(int index, Bitmap bitmap,int currentTangle)
    {
        this.index = index;
        this.bitmap = bitmap;
        this.currentTangle = currentTangle;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    public void resetTangle(){
        currentTangle = 0;
    }

    @Override
    public String toString()
    {
        return "ItemPiece [index=" + index + ", bitmap=" + bitmap + "]";
    }


}
