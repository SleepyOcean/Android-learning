/*
 * Created by sleepy on 17-2-4 下午7:43
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.graphics.Bitmap;

public class ItemPiece {
    private int index ;
    private Bitmap bitmap ;

    public ItemPiece()
    {
    }

    public ItemPiece(int index, Bitmap bitmap)
    {
        this.index = index;
        this.bitmap = bitmap;
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

    @Override
    public String toString()
    {
        return "ItemPiece [index=" + index + ", bitmap=" + bitmap + "]";
    }


}
