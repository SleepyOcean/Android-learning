/*
 * Created by sleepy on 17-2-4 下午6:15
 * Copyright (c) 2017.
 */

package com.sleepy.t3;


import android.graphics.Bitmap;

public class BeanListItem {
    private String name;
    private String date;
    private String path;

    public BeanListItem() {
    }

    public BeanListItem(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
