package com.yeahs.www.qrcode;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yeahs.www.qrcode.ContactSql.UserDataHelper;
import com.yeahs.www.qrcode.network.LoginService;
import com.yeahs.www.qrcode.network.SelfCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
//启动页
public class startActivity extends BaseActivity {

    private Timer timer;
    private TimerTask task;
    private LoginService loginService = null;
    private UserDataHelper userDataHelper = null;
    private String password = "";
    private String uid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        loginService = new LoginService(this);
        userDataHelper = new UserDataHelper(this, "User.db", null, 1);
        jump();
    }
    protected void jump () {
        SQLiteDatabase db = userDataHelper.getReadableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            password = cursor.getString(cursor.getColumnIndex("password"));
            uid = cursor.getString(cursor.getColumnIndex("uid"));
            loginService.autologin(new SelfCallback() {
                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    try {
                        Integer code = response.getInt("code");
                        String msg = response.getString("msg");
                        if (code.equals(0)) {
                            Intent intent = new Intent(startActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {;
                            Intent intent = new Intent(startActivity.this, loginActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Intent intent = new Intent(startActivity.this, loginActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    super.onErrorResponse(error);
                    Intent intent = new Intent(startActivity.this, loginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public JSONObject getParams() {
                    JSONObject data = new JSONObject();
                    try {
                        data.put("uid", uid);
                        data.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return data;
                }
            });
        }
        else {
            Intent intent = new Intent(startActivity.this, loginActivity.class);
            startActivity(intent);
        }
//        task = new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(startActivity.this, loginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        };
//
//        timer = new Timer();
//        // 参数：
//        // 3000，延时1秒后执行。
//        timer.schedule(task, 3000);
    }
}
