/*
 * Created by sleepy on 17-3-13 下午10:59
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sleepy on 2017/3/13.
 */

public class PreferenceHelper {

    private static String IDENTIFY="com.sleepy";

    public static void putString(String key, String value, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IDENTIFY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(String key,Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IDENTIFY,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    public static void putInt(String key, int value, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IDENTIFY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static int getInt(String key,Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IDENTIFY,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);
    }

    public static void removeData(String key, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IDENTIFY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
