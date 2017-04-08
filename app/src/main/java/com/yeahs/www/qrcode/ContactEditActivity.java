package com.yeahs.www.qrcode;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by lenovo on 2017/3/23.
 */

public class ContactEditActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("createActivity", "联系人编辑界面activity正在创建");
        setContentView(R.layout.contact_info);
    }
}
