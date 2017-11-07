package com.sleepy.tiledesign;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gehou on 2017/10/9.
 */

public class ItemPiece {
    private int index;

    private String path;
    private Bitmap bitmap;
    private int rotatedState;

    private int imageWidth;

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    private int imageHeight;

    public boolean isSquare() {
        return isSquare;
    }

    private boolean isSquare;

    public ItemPiece(int index, String path, int rotatedState) {
        this.index = index;
        this.path = path;
        this.rotatedState = rotatedState;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bitmap = BitmapFactory.decodeFile(path,options);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        if(imageHeight==imageWidth){
            isSquare = true;
        }else {
            isSquare = false;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        bitmap = BitmapFactory.decodeFile(path);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setRotatedState(int rotatedState) {
        this.rotatedState = rotatedState;
    }

    public void setNextState() {
        rotatedState++;
        rotatedState = rotatedState % 4;
    }

    public int getIndex() {
        return index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getRotatedState() {
        return rotatedState;
    }




}
