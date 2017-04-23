package com.yeahs.www.qrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//忘记密码
public class PasswordForgetActivity extends AppCompatActivity {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.passwordConfirm) EditText passwordConfirm;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);
        ButterKnife.bind(this);
        title.setText("找回密码");
        back.setVisibility(View.VISIBLE);
        back.setText("返回");
    }

    @OnClick(R.id.button_backward)
    protected void back() {
        finish();
    }
}
