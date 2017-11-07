/*
 * Created by sleepy on 17-5-8 下午11:20
 * Copyright (c) 2017.
 */

package com.sleepy.ebdatatransfer03;

import android.util.Log;

/**
 * Created by sleepy on 2017/5/8.
 */

public class DataUtil {
    private static final int BYTE_LEN = 32;
    static String data;
    static String status;
    static String temperature;
    static String humidity;
    static String uV;
    static String power;
    static String volt;
    static String electricity;
    static String dirt;
    private static String tmpString;
    static boolean isTaskOn = false;
    static boolean isTaskPause = false;

    static void resetData() {
        status = "--";
        temperature = "-- ℃";
        humidity = "-- %";
        uV = "--";
        volt = "-- V";
        electricity = "-- A";
        power = "-- W";
        dirt = "积尘率 --";
        data = "";
    }

    static void parseData() {
        int[] buff = new int[BYTE_LEN];

        Log.d("TAG", "string len: %%%%%%%%%%%%" + data.length());
        for (int i = 0; i < BYTE_LEN; i++) {
            tmpString = data.substring(i * 2, i * 2 + 2);
            Log.d("TAG", "tmpString: >>>>" + tmpString);
            buff[i] = Integer.valueOf(tmpString, 16);
        }

        for (int i = 0; i < buff.length; i++) {
            if (buff[i] == Integer.valueOf("d1", 16)) {
                temperature = String.valueOf(buff[++i]) + " ℃";
                humidity = String.valueOf(buff[++i]) + " %";
            } else if (buff[i] == Integer.valueOf("d2", 16)) {
                switch (buff[++i] ) {
                    case 1:
                        uV = "强";break;
                    case 2:
                        uV = "中";break;
                    case 3:
                        uV = "弱";break;
                }
            } else if (buff[i] == Integer.valueOf("d3", 16)) {
                volt = String.valueOf(buff[++i] + ((float) buff[++i] / 100)) + " V";
            } else if (buff[i] == Integer.valueOf("d4", 16)) {
                electricity = String.valueOf(buff[++i] + ((float) buff[++i] / 100)) + " A";
            } else if (buff[i] == Integer.valueOf("d5", 16)) {
                power = String.valueOf(buff[++i]) + " W";
            } else if (buff[i] == Integer.valueOf("d6", 16)) {
                dirt = "积尘率 " + String.valueOf(buff[++i]) + " %";
            } else if (buff[i] == Integer.valueOf("d7", 16)) {
                switch (buff[++i]) {
                    case 1:
                        status = "优";
                        break;
                    case 2:
                        status = "良";
                        break;
                    case 3:
                        status = "差";
                        break;
                }
            }

        }

        showData();
    }

    private static void showData() {
        Log.d("ShowData", "\t" + temperature + "\t" + humidity + "\t" + uV + "\t" + volt + "\t" + electricity + "\t" + power + "\t" + dirt + "\t" + status + "\n");
    }
}
