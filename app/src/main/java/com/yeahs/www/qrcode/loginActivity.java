package com.yeahs.www.qrcode;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yeahs.www.qrcode.ContactSql.UserDataHelper;
import com.yeahs.www.qrcode.dialog.LoadingDialog;
import com.yeahs.www.qrcode.network.SelfCallback;
import com.yeahs.www.qrcode.network.LoginService;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//登录
public class loginActivity extends BaseActivity {

    @BindView(R.id.password) EditText passwordView;
    @BindView(R.id.email) EditText emailView;
    @BindView(R.id.text_title) TextView title;
    private String email = "";
    private String password = "";
    private final LoginService loginService = new LoginService(this);
    protected LoadingDialog loadingDialog = null;
    private UserDataHelper userDataHelper = null;
    private static int newVersion = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        title.setText("登录");
        loadingDialog = new LoadingDialog(this, R.style.LoadingDialog);
        passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        userDataHelper = new UserDataHelper(this, "User.db", null, newVersion);
        userDataHelper.getWritableDatabase();
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
        email = emailView.getText().toString();
        password = passwordView.getText().toString();
        if (email.equals("")) {
            Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();
        loginService.login(new SelfCallback() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                try {
                    Integer code = response.getInt("code");
                    Log.i("login", response.toString());
                    Log.i("login", response.toString());
                    String msg = response.getString("msg");
                    loadingDialog.dismiss();
//                    if (code.equals(0)) {
                    if (code.equals(0)) {
                        JSONArray data = response.getJSONArray("data");
                        JSONObject user = data.getJSONObject(0);
                        SQLiteDatabase db = userDataHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        db.delete("user", null, null);
                        Log.i("login user", user.toString());
                        values.put("uid", user.getString("uid"));
                        values.put("account", user.getString("account"));
                        values.put("email", user.getString("account"));
                        values.put("password", user.getString("password"));
                        values.put("name", user.getString("name"));
                        values.put("mobile", user.getString("mobile"));
                        values.put("homepage", user.getString("homepage"));
                        values.put("job", user.getString("job"));
                        values.put("company", user.getString("company"));
                        values.put("company_address", user.getString("company_address"));
                        values.put("remark", user.getString("remark"));
                        db.insert("user", null, values);
                        Intent intent = new Intent(loginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(loginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
                Log.i("login", "访问失败");
                loadingDialog.dismiss();
                Toast.makeText(loginActivity.this, "网络问题，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public JSONObject getParams() {
                JSONObject data = new JSONObject();
                try {
                    data.put("email", email);
                    data.put("password", password);
                    Log.i("login", data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return data;
            }
        });
    }
}
