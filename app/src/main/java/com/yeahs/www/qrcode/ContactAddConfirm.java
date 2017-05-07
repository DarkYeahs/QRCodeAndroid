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
    private ContactPerson contactObject = null;
    private ContactService contactService = null;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add_confirm);
        ButterKnife.bind(this);
        contactService = new ContactService(this);
        confitmBtn.setText("确认");
        confitmBtn.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        contactMsg = intent.getStringExtra("qrMsg");
        Log.e("confitm", contactMsg);
        try {
            contactObject = gson.fromJson(contactMsg, ContactPerson.class);
            nameValue.setText(contactObject.getName());
            title.setText(contactObject.getName());
            mobileValue.setText(contactObject.getMobile());
            remarkValue.setText(contactObject.getRemark());
            companyValue.setText(contactObject.getCompany());
            jobValue.setText(contactObject.getJob());
            addrValue.setText(contactObject.getCompany_address());
            homepageValue.setText(contactObject.getHomepage());
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
                ContactPerson item = new ContactPerson(contactObject.getName(), "", "1", contactObject.getCuid(),contactObject.getEmail(),contactObject.getMobile(), contactObject.getHomepage(), contactObject.getJob(), contactObject.getCompany(), contactObject.getRemark(), contactObject.getCompany_address());
                CApplication.contactList.add(item);
                Toast.makeText(ContactAddConfirm.this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
                Toast.makeText(ContactAddConfirm.this, "网络问题，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public JSONObject getParams() {
                JSONObject data = new JSONObject();
                try {
                    data.put("cuid", contactObject.getCuid());
                    data.put("name", contactObject.getName());
                    data.put("mobile", contactObject.getMobile());
                    data.put("email", contactObject.getEmail());
                    data.put("remark", contactObject.getRemark());
                    data.put("company", contactObject.getCompany());
                    data.put("job", contactObject.getJob());
                    data.put("company_address", contactObject.getCompany_address());
                    data.put("homepage", contactObject.getHomepage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("edit", data.toString());
                return data;
            }
        });
    }
}
