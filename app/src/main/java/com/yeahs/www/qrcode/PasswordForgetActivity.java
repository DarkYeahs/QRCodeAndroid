package com.yeahs.www.qrcode;


import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yeahs.www.qrcode.dialog.LoadingDialog;
import com.yeahs.www.qrcode.network.LoginService;
import com.yeahs.www.qrcode.network.SelfCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//忘记密码
public class PasswordForgetActivity extends BaseActivity {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.check_num) EditText check_num;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.passwordConfirm) EditText passwordConfirm;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    private LoginService loginService = null;
    protected String emailString = null;
    protected String passwordString = "";
    protected String passwordConfirmString = "";
    protected String checkNumString = "";
    protected LoadingDialog loadingDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);
        ButterKnife.bind(this);

        loadingDialog = new LoadingDialog(this, R.style.LoadingDialog);
        loginService = new LoginService(this);
        title.setText("找回密码");
        back.setVisibility(View.VISIBLE);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @OnClick(R.id.button_backward)
    protected void back() {
        finish();
    }
    @OnClick(R.id.confirm_btn)
    public void getPassword () {
        emailString = email.getText().toString();
        passwordConfirmString = passwordConfirm.getText().toString();
        passwordString = password.getText().toString();
        checkNumString = check_num.getText().toString();
        if (emailString.equals("")) {
            Toast.makeText(PasswordForgetActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordString.equals("")) {
            Toast.makeText(PasswordForgetActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passwordString.equals(passwordConfirmString)) {
            Toast.makeText(PasswordForgetActivity.this, "两次输入密码要相同", Toast.LENGTH_SHORT).show();
            return;
        }
        if(checkNumString.equals("070713")) {
            Toast.makeText(PasswordForgetActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();
        loginService.forgetPassword(new SelfCallback() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                loadingDialog.dismiss();
                try {
                    Integer code = response.getInt("code");
                    String msg = response.getString("msg");
                    if (code.equals(0)) {
                        Toast.makeText(PasswordForgetActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(PasswordForgetActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }

            @Override
            public JSONObject getParams() {
                JSONObject data = new JSONObject();
                try {
                    data.put("password", passwordString);
                    data.put("account", emailString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return data;
            }
        });
    }
}
