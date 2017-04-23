package com.yeahs.www.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yeahs.www.qrcode.network.ContactService;
import com.yeahs.www.qrcode.network.SelfCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//二维码扫描结果处理

public class ContactAddConfirm extends AppCompatActivity {
    @BindView(R.id.name_value) TextView nameValue;
    @BindView(R.id.mobile_value) TextView mobileValue;
    @BindView(R.id.remark_value) TextView remarkValue;
    @BindView(R.id.company_value) TextView companyValue;
    @BindView(R.id.job_value) TextView jobValue;
    @BindView(R.id.addr_value) TextView addrValue;
    @BindView(R.id.homepage_value) TextView homepageValue;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    @BindView(R.id.button_forward) Button confitmBtn;
    private String contactMsg = null;
    private Contact contactObject = null;
    private ContactService contactService = null;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add_confirm);
        ButterKnife.bind(this);
        contactService = new ContactService(this);
        back.setText("返回");
        confitmBtn.setText("确认");
        confitmBtn.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        contactMsg = intent.getStringExtra("qrMsg");
        Log.e("confitm", contactMsg);
        try {
            contactObject = gson.fromJson(contactMsg, Contact.class);
            nameValue.setText(contactObject.name);
            title.setText(contactObject.name);
            mobileValue.setText(contactObject.mobile);
            remarkValue.setText(contactObject.remark);
            companyValue.setText(contactObject.company);
            jobValue.setText(contactObject.job);
            addrValue.setText(contactObject.addr);
            homepageValue.setText(contactObject.homepage);
        } catch (Exception e) {
            Toast.makeText(ContactAddConfirm.this, "二维码格式错误", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    @OnClick(R.id.button_backward)
    protected void back () {
        finish();
    }
    @OnClick(R.id.button_forward)
    protected  void addConfirm () {
        contactService.addContact(new SelfCallback() {
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
                JSONObject data = new JSONObject();
                try {
                    data.put("cuid", contactObject.cuid);
                    data.put("name", contactObject.name);
                    data.put("mobile", contactObject.mobile);
                    data.put("email", contactObject.email);
                    data.put("remark", contactObject.remark);
                    data.put("company", contactObject.company);
                    data.put("job", contactObject.job);
                    data.put("company_address", contactObject.addr);
                    data.put("homepage", contactObject.homepage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("edit", data.toString());
                return data;
            }
        });
    }
}
//联系人信息类
class Contact {
    public String name;
    public String email;
    public String mobile;
    public String remark;
    public String company;
    public String job;
    public String homepage;
    public String addr;
    public String cuid;
}
