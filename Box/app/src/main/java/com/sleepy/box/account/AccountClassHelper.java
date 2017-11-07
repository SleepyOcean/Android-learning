package com.sleepy.box.account;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gehou on 2017/9/17.
 */

public class AccountClassHelper {
    static boolean isNotAccountMain = false;

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

}
