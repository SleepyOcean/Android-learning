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
    static String status = "--";;
    static String temperature= "--";;
    static String humidity= "--";;
    static String uV= "--";;
    static String power= "--";;
    static String volt= "--";;
    static String electricity= "--";;
    static String dirt= "--";;
    static String tm;

//    static void initData(){
//        status = "--";
//        temperature = "--";
//        humidity = "--";
//        uV = "--";
//        volt = "--";
//        electricity = "--";
//        power = "--";
//        dirt = "--";
//
//    }
    static void parseData() {
        int[] buff = new int[BYTE_LEN];

        Log.d("TAG", "string len: %%%%%%%%%%%%" + data.length());
        for (int i = 0; i < BYTE_LEN; i++) {
            tm = data.substring(i*2, i*2 + 2);
            Log.d("TAG", "tm: >>>>"+tm);
            buff[i] = Integer.valueOf(tm, 16);
        }

        for (int i = 0; i < buff.length; i++) {
            if (buff[i] == Integer.valueOf("d1", 16)) {
                temperature = String.valueOf(buff[++i])+" ℃";
                humidity = String.valueOf(buff[++i]) + " %";
            } else if (buff[i] == Integer.valueOf("d2", 16)) {
                uV = String.valueOf(buff[++i] + (buff[++i] / 100));
            } else if (buff[i] == Integer.valueOf("d3", 16)) {
                volt = String.valueOf(buff[++i] + ((float)buff[++i] / 100))+" V";
            } else if (buff[i] == Integer.valueOf("d4", 16)) {
                electricity = String.valueOf(buff[++i] + ((float)buff[++i] / 100))+" A";
            } else if (buff[i] == Integer.valueOf("d5", 16)) {
                power = String.valueOf(buff[++i])+" W";
            } else if (buff[i] == Integer.valueOf("d6", 16)) {
                dirt = String.valueOf(buff[++i]) + " %";
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
//            if (buff[i] == Integer.valueOf("d1", 16)) {
//                temperature = buff[i+1];
//                humidity = buff[i+2];
//            } else if (buff[i] == Integer.valueOf("d2", 16)) {
//                uV = buff[i+1] + (buff[i+2] / 100);
//            } else if (buff[i] == Integer.valueOf("d3", 16)) {
//                volt = buff[i+1] + (buff[i+2] / 100);
//            } else if (buff[i] == Integer.valueOf("d4", 16)) {
//                electricity = buff[i+1] + (buff[i+2] / 100);
//            } else if (buff[i] == Integer.valueOf("d5", 16)) {
//                power = buff[i+1];
//            } else if (buff[i] == Integer.valueOf("d6", 16)) {
//                dirt = buff[i+1];
//            } else if (buff[i] == Integer.valueOf("d7", 16)) {
//                switch (buff[i+1]) {
//                    case 1:
//                        status = "优";
//                        break;
//                    case 2:
//                        status = "良";
//                        break;
//                    case 3:
//                        status = "差";
//                        break;
//                }
//            }
        }


        int i = -1;
        for (int d : buff) {
            Log.d("TAG", "parseData:~~~~~~~~~~~~~~~ " + (++i) + "#######" + d);
        }
        showData();
    }

    public static void showData() {
        Log.d("ShowData", "\t" + temperature + "\t"+humidity+"\t"+ uV+"\t"+volt+ "\t"+electricity+ "\t"+power+ "\t"+dirt+ "\t"+status+"\n");
    }
}
