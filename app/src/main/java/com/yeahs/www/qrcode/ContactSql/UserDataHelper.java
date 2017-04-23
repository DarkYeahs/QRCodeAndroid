package com.yeahs.www.qrcode.ContactSql;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2017/4/23.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by lenovo on 2017/3/28.
 */


public class UserDataHelper extends SQLiteOpenHelper {
    private final static String TAG = "SqliteTest";
    private Context mContext;
    public UserDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i(TAG, "my database helper constructor");
        mContext = context;
    }
    public static final String CREATE_USER = "CREATE TABLE `user` (" +
            "  `account` varchar(32) NOT NULL," +
            "  `password` varchar(64) NOT NULL," +
            "  `uid` varchar(32) NOT NULL," +
            "  `name` varchar(16) DEFAULT NULL," +
            "  `mobile` varchar(20) DEFAULT NULL," +
            "  `email` varchar(20) DEFAULT NULL," +
            "  `homepage` varchar(64) DEFAULT NULL," +
            "  `job` varchar(20) DEFAULT NULL," +
            "  `company` varchar(20) DEFAULT NULL," +
            "  `company_address` varchar(64) DEFAULT NULL," +
            "  `remark` text" +
            ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "my database helper create");
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
