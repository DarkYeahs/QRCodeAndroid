package com.yeahs.www.qrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistryActivity extends AppCompatActivity {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.passwordConfirm) EditText passwordConfirm;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    private String emailString = "";
    private String passwordString = "";
    private String passwordConfirmString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        ButterKnife.bind(this);
        title.setText("注册");
        back.setVisibility(View.VISIBLE);
        back.setText("返回");
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @OnClick(R.id.registry_btn)
    protected void registry () {
        emailString = email.getText().toString();
        passwordString = password.getText().toString();
        passwordConfirmString = passwordConfirm.getText().toString();
        if (emailString.equals("")) {
            Toast.makeText(RegistryActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordString.equals("")) {
            Toast.makeText(RegistryActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordConfirmString.equals("")) {
            Toast.makeText(RegistryActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!passwordString.equals(passwordConfirmString)) {
            Toast.makeText(RegistryActivity.this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    @OnClick(R.id.button_backward)
    protected void back (){
        finish();
    }
}
