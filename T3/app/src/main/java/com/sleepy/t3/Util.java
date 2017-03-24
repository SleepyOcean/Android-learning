/*
 * Created by sleepy on 17-2-4 下午6:52
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sleepy on 2017/2/4.
 */

public class Util {
    final static int CODE_INTENT_DESIGN = 10;
    static int CurrentNum=0;
    public static String imagePath;
    public static int LOAD_MODE = 0;
    public static int column = 3;
    public static float uCurrentTangle[] = new float[100];
    public static int tangle[] = new int[100];
    public static boolean isLoad =false;

    public static void resetTangle(){
        for(int i:tangle){
            i=0;
        }
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    public static boolean isNum(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e)
        {
            return false;
        }
    }
}
