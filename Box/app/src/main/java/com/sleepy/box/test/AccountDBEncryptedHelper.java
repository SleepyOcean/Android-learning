//package com.sleepy.box.func.account;
//
//import android.content.Context;
//
//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
//
///**
// * Created by gehou on 2017/9/22.
// */
//
//public class AccountDBEncryptedHelper extends SQLiteOpenHelper {
//    public static final String DB_PWD="mimahenbudao";//数据库密码
//
//
//    public AccountDBEncryptedHelper(Context context) {
//        super(context, "box_db", null, 1);
//        //不可忽略的 进行so库加载
//        SQLiteDatabase.loadLibs(context);
//    }
//
//    public AccountDBEncryptedHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
//
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE account(" +
//                "id INTEGER PRIMARY KEY," +
//                "category TEXT DEFAULT NONE," +
//                "account TEXT DEFAULT NONE," +
//                "password TEXT DEFAULT NONE," +
//                "state TEXT DEFAULT NONE," +
//                "time TEXT DEFAULT NONE)");
//        String sql = "insert into account values (0,0,0,0,0,0)"; //id 自增加
//        sqLiteDatabase.execSQL(sql);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//}
