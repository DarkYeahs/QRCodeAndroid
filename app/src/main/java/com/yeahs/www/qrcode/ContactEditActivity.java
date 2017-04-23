package com.yeahs.www.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yeahs.www.qrcode.network.ContactService;
import com.yeahs.www.qrcode.network.SelfCallback;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.button_forward) Button confirmBtn;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.name_value) EditText nameView;
    @BindView(R.id.mobile_value) EditText mobileView;
    @BindView(R.id.remark_value) EditText remarkView;
    @BindView(R.id.email_value) EditText emailView;
    @BindView(R.id.company_value) EditText companyView;
    @BindView(R.id.job_value) EditText jobView;
    @BindView(R.id.homepage_value) EditText homepageView;
    @BindView(R.id.company_address_value) EditText companyAddressView;
    private ContactService contactService = null;
    private String id = "";
    private String cuid = "";
    private String name = "";
    private int imagesrc = 0;
    private String company = "";
    private String company_address = "";
    private String email = "";
    private String job = "";
    private String mobile = "";
    private String homepage = "";
    private String remark = "";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("createActivity", "联系人编辑界面activity正在创建");
        setContentView(R.layout.contact_info);
        ButterKnife.bind(this);
        contactService = new ContactService(this);
        back.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        cuid = intent.getStringExtra("cuid");
        name = intent.getStringExtra("name");
        imagesrc = intent.getIntExtra("imagesrc", 10);
        company = intent.getStringExtra("company");
        company_address = intent.getStringExtra("company_address");
        email = intent.getStringExtra("email");
        job = intent.getStringExtra("job");
        mobile = intent.getStringExtra("mobile");
        homepage = intent.getStringExtra("homepage");
        remark = intent.getStringExtra("remark");
        nameView.setText(name);
        mobileView.setText(mobile);
        remarkView.setText(remark);
        emailView.setText(email);
        companyView.setText(company);
        jobView.setText(job);
        homepageView.setText(homepage);
        companyAddressView.setText(company_address);
        back.setText("返回");
        confirmBtn.setText("确认");

        title.setText(name);
    }
    @OnClick(R.id.button_backward)
    protected void back () {
        finish();
    }
    @OnClick(R.id.button_forward)
    public void editConfirm () {
        name = nameView.getText().toString();
//        imagesrc = .getIntExtra("imagesrc", 10);
        company = companyView.getText().toString();
        company_address = companyAddressView.getText().toString();
        email = emailView.getText().toString();
        job = jobView.getText().toString();
        mobile = mobileView.getText().toString();
        homepage = homepageView.getText().toString();
        remark = remarkView.getText().toString();
        contactService.editContact(new SelfCallback() {
            @Override
            public void onResponse(JSONObject reponse) {
                super.onResponse(reponse);
                Log.i("edit", reponse.toString());
                Toast.makeText(ContactEditActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }

            @Override
            public JSONObject getParams() {
                JSONObject data = new JSONObject();
                try {
                    data.put("id", id);
                    data.put("cuid", cuid);
                    data.put("name", name);
                    data.put("mobile", mobile);
                    data.put("email", email);
                    data.put("remark", remark);
                    data.put("company", company);
                    data.put("job", job);
                    data.put("company_address", company_address);
                    data.put("homepage", homepage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("edit", data.toString());
                return data;
            }
        });
    }
}
