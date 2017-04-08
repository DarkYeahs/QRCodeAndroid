package com.yeahs.www.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.update_check) Button update_check_btn;
    @BindView(R.id.feed_back) Button feedback_btn;
    @BindView(R.id.logout) Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.update_check)
    protected void check_update() {
        Toast.makeText(SettingActivity.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.feed_back)
    protected void feed_back () {
        Intent intent = new Intent(SettingActivity.this, FeedBack.class);
        startActivity(intent);
    }
}
