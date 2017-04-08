package com.yeahs.www.qrcode;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yeahs.www.qrcode.ContactSql.ContactDatabaseHelper;

import butterknife.OnClick;

public class ContactItemActivity extends AppCompatActivity {

    private final static String TAG = "SqliteTest";
    private ContactDatabaseHelper dbHelper;
    private static int newVersion = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "contactItemActivity create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_item);
        dbHelper = new ContactDatabaseHelper(this, "User.db", null, newVersion);
        this.queryData();
    }

    public void queryData() {
        Log.i("queryData", "这里是queryData函数开始");
        StringBuffer stringBuffer = new StringBuffer();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.i("queryData", "循环遍历中");
                // 遍历Cursor对象，取出数据并打印
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(2); //index从0开始的，password位于第三
                stringBuffer.append("username: " + username + ", id: " + password + "\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.i(TAG, stringBuffer.toString());
        Log.i("queryData", "这里是queryData函数结束");
    }
    @OnClick(R.id.contact_edit_btn)
    public void editBtn () {
        Log.i("edit_btn", "正在点击编辑按钮");
        Intent intent = new Intent(ContactItemActivity.this, ContactEditActivity.class);
        startActivity(intent);
    }
}
