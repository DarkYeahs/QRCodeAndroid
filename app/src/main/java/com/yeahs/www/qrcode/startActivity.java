package com.yeahs.www.qrcode;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class startActivity extends AppCompatActivity {

    private Timer timer;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        jump();
    }
    protected void jump () {
        task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(startActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        timer = new Timer();
        // 参数：
        // 3000，延时1秒后执行。
        timer.schedule(task, 3000);
    }
}
