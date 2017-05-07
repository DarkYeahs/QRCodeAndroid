package com.yeahs.www.qrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yeahs.www.qrcode.dialog.LoadingDialog;
import com.yeahs.www.qrcode.network.SelfCallback;
import com.yeahs.www.qrcode.network.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordFixedActivity extends BaseActivity {
    @BindView(R.id.oldpassword) EditText oldPasswordView;
    @BindView(R.id.newpassword) EditText newpasswordView;
    @BindView(R.id.newpasswordConfirm) EditText newpasswordConfirmView;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    protected String oldPassword = "";
    protected String newPassword = "";
    protected String newPasswordConfirm = "";
    protected UserService userService = null;
    protected LoadingDialog loadingDialog = null;
    private String uid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_fixed);
        ButterKnife.bind(this);
        title.setText("修改密码");
        back.setVisibility(View.VISIBLE);
        userService = new UserService(this);
        loadingDialog = new LoadingDialog(this, R.style.LoadingDialog);
        oldPasswordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newpasswordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newpasswordConfirmView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        uid = CApplication.user.getId();
    }
    @OnClick(R.id.button_backward)
    protected void back () {
        finish();
    }
    @OnClick(R.id.confirm_btn)
    protected void edit () {
        oldPassword = oldPasswordView.getText().toString();
        newPassword = newpasswordView.getText().toString();
        newPasswordConfirm = newpasswordConfirmView.getText().toString();
        if (oldPassword.equals("")) {
            Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.equals("")) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPasswordConfirm.equals("")) {
            Toast.makeText(this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPasswordConfirm.equals(newPassword)) {
            Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();
        userService.editUserInfo(new SelfCallback() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                loadingDialog.dismiss();
                try {
                    Integer code = response.getInt("code");
                    String msg = response.getString("msg");
                    if (code.equals(0)) {
                        Toast.makeText(PasswordFixedActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(PasswordFixedActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PasswordFixedActivity.this, "网络问题，请重试", Toast.LENGTH_SHORT).show();
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
                    data.put("id", uid);
                    data.put("password", oldPassword);
                    data.put("newpassword", newPassword);
                    data.put("uid", CApplication.user.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return data;
            }
        });
    }
}
