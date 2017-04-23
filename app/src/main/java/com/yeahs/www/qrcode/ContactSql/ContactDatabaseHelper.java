package com.yeahs.www.qrcode.ContactSql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lenovo on 2017/3/28.
 */
//数据库的创建，操作，SQLite,
public class ContactDatabaseHelper extends SQLiteOpenHelper {
    private final static String TAG = "SqliteTest";
    private Context mContext;
    public ContactDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i(TAG, "my database helper constructor");
        mContext = context;
    }

    public static final String CREATE_USER = "create table user ("
            + "userid integer primary key autoincrement, "
            + "username text, "
            + "id text)";

    public static final String CREATE_BOOK = "create table book ("
            + "bookid integer primary key autoincrement, "
            + "bookname text, "
            + "bookpage integer)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "my database helper create");
        db.execSQL(CREATE_USER);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    // 升級
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "oldVersion: " + oldVersion + ", newVersion: " + newVersion);
        switch (newVersion) {
            case 2:
                db.execSQL(CREATE_BOOK);
            case 1:
                Log.i(TAG, "Hello world.");
            default:
        }
    }
}