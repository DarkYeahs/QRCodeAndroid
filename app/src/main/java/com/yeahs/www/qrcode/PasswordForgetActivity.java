package com.yeahs.www.qrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yeahs.www.qrcode.network.LoginService;
import com.yeahs.www.qrcode.network.SelfCallback;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordForgetActivity extends AppCompatActivity {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.passwordConfirm) EditText passwordConfirm;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    private LoginService loginService = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);
        ButterKnife.bind(this);
        loginService = new LoginService(this);
        title.setText("找回密码");
        back.setVisibility(View.VISIBLE);
        back.setText("返回");
    }

    @OnClick(R.id.button_backward)
    protected void back() {
        finish();
    }
    @OnClick(R.id.confirm_btn)
    public void getPassword () {
        loginService.forgetPassword(new SelfCallback() {
            @Override
            public void onResponse(JSONObject reponse) {
                super.onResponse(reponse);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }

            @Override
            public JSONObject getParams() {
                return super.getParams();
            }
        });
    }
}
