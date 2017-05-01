package com.yeahs.www.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//设置
public class SettingActivity extends BaseActivity {

    @BindView(R.id.update_check) Button update_check_btn;
    @BindView(R.id.feed_back) Button feedback_btn;
    @BindView(R.id.logout) Button logout_btn;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        ButterKnife.bind(this);
        back.setText("返回");
        back.setVisibility(View.VISIBLE);
        title.setText("设置");
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
    @OnClick(R.id.button_backward)
    protected void back () {
        finish();
    }
    @OnClick(R.id.logout)
    protected void logout () {
        Log.i("login", CApplication.activitys.activities.size() + "");
        CApplication.activitys.finishAll();
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }
}
