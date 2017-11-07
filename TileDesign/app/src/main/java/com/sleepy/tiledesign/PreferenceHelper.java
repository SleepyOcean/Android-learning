package com.sleepy.tiledesign;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gehou on 2017/10/24.
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
