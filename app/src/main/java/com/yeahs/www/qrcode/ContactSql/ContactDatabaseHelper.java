package com.yeahs.www.qrcode.ContactSql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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

    public static final String CREATE_CONTACT_USER = "CREATE TABLE `contact_user` (" +
            "  `cuid` varchar(32) NOT NULL," +
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
        db.execSQL(CREATE_CONTACT_USER);
        Log.i("login", "数据库创建");
    }

    // 升級
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "oldVersion: " + oldVersion + ", newVersion: " + newVersion);
//        switch (newVersion) {
//            case 2:
//                db.execSQL(CREATE_BOOK);
//            case 1:
//                Log.i(TAG, "Hello world.");
//            default:
//        }
    }
}