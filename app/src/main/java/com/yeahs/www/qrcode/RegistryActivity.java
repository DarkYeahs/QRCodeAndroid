package com.yeahs.www.qrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//注册
public class RegistryActivity extends BaseActivity {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.passwordConfirm) EditText passwordConfirm;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    private String emailString = "";
    private String passwordString = "";
    private String passwordConfirmString = "";
    private final LoginService loginService = new LoginService(this);
    protected LoadingDialog loadingDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this, R.style.LoadingDialog);
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

        loadingDialog.show();
        loginService.registry(new SelfCallback() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                loadingDialog.dismiss();
                try {
                    Integer code = response.getInt("code");
                    String msg = response.getString("msg");
                    if (code.equals(0)) {
                        Toast.makeText(RegistryActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegistryActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
                loadingDialog.dismiss();
                Toast.makeText(RegistryActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
            }

            @Override
            public JSONObject getParams() {
                JSONObject data = new JSONObject();
                try {
                    data.put("account", emailString);
                    data.put("password", passwordString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return data;
            }
        });
    }
    @OnClick(R.id.button_backward)
    protected void back (){
        finish();
    }
}
