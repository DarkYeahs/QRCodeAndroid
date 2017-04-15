package com.yeahs.www.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class loginActivity extends AppCompatActivity {

    @BindView(R.id.password) EditText password;
    @BindView(R.id.text_title) TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        title.setText("登录");
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    @OnClick(R.id.registry_btn)
    protected void registry() {
        Intent intent = new Intent(loginActivity.this, RegistryActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.forget_btn)
    protected void passwordForget() {
        Intent intent = new Intent(loginActivity.this, PasswordForgetActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.login_btn)
    protected void login () {
        Intent intent = new Intent(loginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
//    public boolean onKeyDown(int kCode, KeyEvent kEvent) {
//        switch (kCode) {
//            case KeyEvent.KEYCODE_DPAD_LEFT: {
//                return true;
//            }
//
//            case KeyEvent.KEYCODE_DPAD_UP: {
//                return true;
//            }
//
//            case KeyEvent.KEYCODE_DPAD_RIGHT: {
//                return true;
//            }
//
//            case KeyEvent.KEYCODE_DPAD_DOWN: {
//                return true;
//            }
//            case KeyEvent.KEYCODE_DPAD_CENTER: {
//                return true;
//            }
//            case KeyEvent.KEYCODE_BACK: {
//                return false;
//            }
//        }
//        return super.onKeyDown(kCode, kEvent);
//    }
}
