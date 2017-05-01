package com.yeahs.www.qrcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lenovo on 2017/4/24.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        CApplication.activitys.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        CApplication.activitys.removeActivity(this);
    }
}
