package com.yeahs.www.qrcode;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/3/23.
 */
//联系人编辑页面，自己信息编辑
public class ContactEditActivity extends AppCompatActivity {
//    @BindView(R.id.button_forward) Button handle;
    @BindView(R.id.button_backward) Button back;
    @BindView(R.id.text_title) TextView title;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("createActivity", "联系人编辑界面activity正在创建");
        setContentView(R.layout.contact_info);
        ButterKnife.bind(this);
        back.setVisibility(View.VISIBLE);
        back.setText("返回");
        title.setText("张丽蓉");
    }
    @OnClick(R.id.button_backward)
    protected void back () {
        finish();
    }
}
