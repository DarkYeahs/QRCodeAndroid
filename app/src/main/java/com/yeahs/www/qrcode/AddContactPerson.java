package com.yeahs.www.qrcode;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yeahs.www.qrcode.ContactSql.ContactDatabaseHelper;
import com.yeahs.www.qrcode.ContactSql.UserDataHelper;
import com.yeahs.www.qrcode.network.ContactService;
import com.yeahs.www.qrcode.network.SelfCallback;
import com.yeahs.www.qrcode.network.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//添加联系人
public class AddContactPerson extends AppCompatActivity {

    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;

    @BindView(R.id.name_value) EditText nameView;
    @BindView(R.id.mobile_value) EditText mobileView;
    @BindView(R.id.remark_value) EditText remarkView;
    @BindView(R.id.company_value) EditText companyView;
    @BindView(R.id.job_value) EditText jobView;
    @BindView(R.id.homepage_value) EditText homepageView;
    @BindView(R.id.company_address_value) EditText companyAddressView;
    private ContactService contactService = null;
    private String cuid = "";
    private String name = "";
    private String mobile = "";
    private String email = "";
    private String remark = "";
    private String company = "";
    private String job = "";
    private String addr = "";
    private String homepage = "";
    private String company_address = "";
    private ContactDatabaseHelper contactDatabaseHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_person);
        ButterKnife.bind(this);
        back.setVisibility(View.VISIBLE);
        back.setText("返回");
        title.setText("添加联系人");
        cuid = CApplication.user.getId();
        Log.i("login", cuid);
        contactService = new ContactService(this);
        contactDatabaseHelper = new ContactDatabaseHelper(this, "User.db", null, 1);
        contactDatabaseHelper.getWritableDatabase();
    }

    @OnClick(R.id.button_backward)
    protected void back () {
        finish();
    }
    @OnClick(R.id.save_btn)
    public void addContact () {
        name = nameView.getText().toString();
        mobile = mobileView.getText().toString();
        remark = remarkView.getText().toString();
        company = companyView.getText().toString();
        job = jobView.getText().toString();
        homepage = homepageView.getText().toString();
        company_address = companyAddressView.getText().toString();
        if (name.equals("")) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobile.equals("")) {
            Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (remark.equals("")) {
            Toast.makeText(this, "备注不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (company.equals("")) {
            Toast.makeText(this, "公司不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (job.equals("")) {
            Toast.makeText(this, "职位不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (homepage.equals("")) {
            Toast.makeText(this, "个人主页不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (company_address.equals("")) {
            Toast.makeText(this, "公司地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Boolean netFlag = Tools.isNetworkConnected(this);
        if (netFlag) {
            contactService.addContact(new SelfCallback() {
                @Override
                public void onResponse(JSONObject reponse) {
                    super.onResponse(reponse);
                    ContactPerson item = new ContactPerson(name, "", "1", cuid,email,mobile, homepage, job, company, remark, company_address);
                    CApplication.contactList.add(item);
                    Toast.makeText(AddContactPerson.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    super.onErrorResponse(error);
                }

                @Override
                public JSONObject getParams(){
                    JSONObject data = new JSONObject();
                    try {
                        data.put("cuid", cuid);
                        data.put("name", name);
                        data.put("mobile", mobile);
                        data.put("email", email);
                        data.put("remark", remark);
                        data.put("company", company);
                        data.put("job", job);
                        data.put("company_address", addr);
                        data.put("homepage", homepage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("edit", data.toString());
                    return data;
                }
            });
        }
        else {
            Log.i("login", "网络未连接");
            ContactPerson item = new ContactPerson(name, "", "1", cuid,email,mobile, homepage, job, company, remark, company_address);
            saveContact();
            CApplication.contactList.add(item);
            Toast.makeText(AddContactPerson.this, "添加成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    protected void saveContact() {
        SQLiteDatabase db = contactDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cuid", cuid);
        values.put("email", email);
        values.put("name", name);
        values.put("mobile", mobile);
        values.put("homepage", homepage);
        values.put("job", job);
        values.put("company", company);
        values.put("company_address", company_address);
        values.put("remark", remark);
        db.insert("contact_user", null, values);
        Log.i("contact_user", "保存成功");
    }
}
