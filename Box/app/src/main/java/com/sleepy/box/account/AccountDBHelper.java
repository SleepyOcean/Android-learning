package com.sleepy.box.account;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gehou on 2017/9/16.
 */

public class AccountDBHelper extends SQLiteOpenHelper {
    public AccountDBHelper(Context context) {
        super(context, "box_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE account(" +
                "id INTEGER PRIMARY KEY," +
                "category TEXT DEFAULT NONE," +
                "account TEXT DEFAULT NONE," +
                "password TEXT DEFAULT NONE," +
                "state TEXT DEFAULT NONE," +
                "time TEXT DEFAULT NONE)");
        String sql = "insert into account values (0,0,0,0,0,0)"; //id 自增加
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
