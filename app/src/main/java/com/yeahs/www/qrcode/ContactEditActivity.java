package com.yeahs.www.qrcode;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.yeahs.www.qrcode.imageview.CircleImageView;
import com.yeahs.www.qrcode.network.ContactService;
import com.yeahs.www.qrcode.network.SelfCallback;
import com.yeahs.www.qrcode.network.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/3/23.
 */
//联系人编辑页面，自己信息编辑
public class ContactEditActivity extends BaseActivity {
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
    @BindView(R.id.user_icon) CircleImageView avatar;
    private ContactService contactService = null;
    private UserService userServicer = null;
    private String id = "";
    private String cuid = "";
    private String name = "";
    private String imagesrc = "";
    private String company = "";
    private String company_address = "";
    private String email = "";
    private String job = "";
    private String mobile = "";
    private String homepage = "";
    private String remark = "";
    private String icon = "";
    private Integer type = null;
    private Integer index = null;
    private Bitmap avatarBitmap = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("createActivity", "联系人编辑界面activity正在创建");
        setContentView(R.layout.contact_info);
        ButterKnife.bind(this);
        contactService = new ContactService(this);
        userServicer = new UserService(this);
        back.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 10);
        switch (type) {
            case 1:
                index = intent.getIntExtra("index", 10);
                ContactPerson item = CApplication.contactList.get(index);
                id = item.getId();
                cuid = item.getCuid();
                name = item.getName();
                imagesrc = item.getImagesrc();
                company = item.getCompany();
                company_address = item.getCompany_address();
                email = item.getEmail();
                job = item.getJob();
                mobile = item.getMobile();
                homepage = item.getHomepage();
                remark = item.getRemark();
                break;
            case 2:
                id = intent.getStringExtra("id");
                cuid = intent.getStringExtra("cuid");
                name = intent.getStringExtra("name");
                imagesrc = intent.getStringExtra("imagesrc");
                company = intent.getStringExtra("company");
                company_address = intent.getStringExtra("company_address");
                email = intent.getStringExtra("email");
                job = intent.getStringExtra("job");
                mobile = intent.getStringExtra("mobile");
                homepage = intent.getStringExtra("homepage");
                remark = intent.getStringExtra("remark");
                break;
        }

        Log.i("login", type.toString());
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
        ImageRequest imageRequest = new ImageRequest("http://172.20.10.3:4000/static/static/out.png",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        // TODO Auto-generated method stub
                        Log.i("login", "获取图片成功");
                        avatar.setImageBitmap(bitmap);
                    }
                }, 300, 200, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Log.i("login", "获取图片失败");
            }
        });
        Volley.newRequestQueue(this).add(imageRequest);
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
        switch (type) {
            case 1:
                contactService.editContact(new SelfCallback() {
                    @Override
                    public void onResponse(JSONObject reponse) {
                        super.onResponse(reponse);
                        Log.i("edit", reponse.toString());
                        Toast.makeText(ContactEditActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        ContactPerson item = new ContactPerson(name, imagesrc,id, cuid,email,mobile, homepage, job, company, remark, company_address);
                        CApplication.contactList.set(index, item);
                        intent.putExtra("id",id);
                        intent.putExtra("cuid", cuid);
                        intent.putExtra("name", name);
                        intent.putExtra("imagesrc", imagesrc);
                        intent.putExtra("company", company);
                        intent.putExtra("company_address", company_address);
                        intent.putExtra("email", email);
                        intent.putExtra("job", job);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("homepage", homepage);
                        intent.putExtra("remark", remark);
                        intent.putExtra("type", type);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        super.onErrorResponse(error);
                    }

                    @Override
                    public JSONObject getParams() {
                        JSONObject data = new JSONObject();
                        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                        avatarBitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
                        byte[] bytes = bStream.toByteArray();
                        String avatarBitmapString = Base64.encodeToString(bytes,Base64.DEFAULT);
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
                            data.put("avatarBitmap", avatarBitmapString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("edit", data.toString());
                        return data;
                    }
                });
                break;
            case 2:
                userServicer.editUserInfo(new SelfCallback() {
                    @Override
                    public void onResponse(JSONObject reponse) {
                        super.onResponse(reponse);
                        try {
                            Integer code = reponse.getInt("code");
                            if (code.equals(0)) {
                                Log.i("edit", reponse.toString());
                                CApplication.user.setName(name);
                                CApplication.user.setEmail(email);
                                CApplication.user.setCompany(company);
                                CApplication.user.setCompany_address(company_address);
                                CApplication.user.setHomepage(homepage);
                                CApplication.user.setJob(job);
                                CApplication.user.setMobile(mobile);
                                CApplication.user.setRemark(remark);
                                Toast.makeText(ContactEditActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("id",id);
                                intent.putExtra("cuid", cuid);
                                intent.putExtra("name", name);
                                intent.putExtra("imagesrc", imagesrc);
                                intent.putExtra("company", company);
                                intent.putExtra("company_address", company_address);
                                intent.putExtra("email", email);
                                intent.putExtra("job", job);
                                intent.putExtra("mobile", mobile);
                                intent.putExtra("homepage", homepage);
                                intent.putExtra("remark", remark);
                                intent.putExtra("type", type);
                                setResult(RESULT_OK, intent);
                                finish();
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
                            data.put("id", id);
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
    @OnClick(R.id.user_icon)
    protected void getAvatar () {
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                avatarBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                avatar.setImageBitmap(avatarBitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
    }
}
