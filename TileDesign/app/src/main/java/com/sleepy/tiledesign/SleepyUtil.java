package com.sleepy.tiledesign;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gehou on 2017/10/9.
 */

public class SleepyUtil {

    final static int CODE_INTENT_DESIGN = 10;
    static int CurrentNum=0;
    public static String imagePath;
    public static int LOAD_MODE = 0;
    public static int column = 3;
    public static int position = 0;
    public static float uCurrentTangle[] = new float[100];
    public static int tangle[] = new int[100];
    public static boolean isLoad =false;


    public static void resetTangle(){
        for(int i:tangle){
            i=0;
        }
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
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


    public static int[] getImageWidthHeight(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth, options.outHeight};
    }

    public static int[] getImageWidthHeight(Resources res,int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(res,id,options);
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth, options.outHeight};
    }
}
