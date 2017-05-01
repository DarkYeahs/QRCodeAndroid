package com.yeahs.www.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yeahs.www.qrcode.ContactSql.ContactDatabaseHelper;
import com.yeahs.www.qrcode.network.ContactService;
import com.yeahs.www.qrcode.network.SelfCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//联系人信息展示类
public class ContactItemActivity extends BaseActivity {
    @BindView(R.id.contact_person_icon) ImageView iconImage;
    @BindView(R.id.button_backward) Button back;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.name_value) TextView nameView;
    @BindView(R.id.mobile_value) TextView mobileView;
    @BindView(R.id.remark_value) TextView remarkView;
    @BindView(R.id.email_value) TextView emailView;
    @BindView(R.id.company_value) TextView companyView;
    @BindView(R.id.job_value) TextView jobView;
    @BindView(R.id.homepage_value) TextView homepageView;
    @BindView(R.id.company_address_value) TextView companyAddressView;
    @BindView(R.id.button_forward) Button handle;
    @BindView(R.id.delete_btn) Button del_btn;
    private final static String TAG = "SqliteTest";
    private ContactDatabaseHelper dbHelper;
    private static int newVersion = 1;
    private ContactPerson contactPerson = null;
    protected ImageView popIconImage = null;
    public Bitmap mBitmap = null;
    private PopupWindow mPopupWindow;
    protected ContactService contactService = null;
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
    private Integer type = null;
    private Integer index = null;
    private String iconText = "{\"name\": \"Ye抽我抽我抽我ahs\",\"mobile\": \"93202983\",\"remark\": \"nwd财务处往往成为kn\",\"company\": \"测试公司\",\"job\": \"测试错误错误错误错误职位\",\"homepage\": \"测试职财务处我看我能看位\",\"addr\": \"侧hi持物会常务会成为hi贺成为测测为此我擦我擦我擦词我吃完iu\",\"userid\": \"cne92ei2ndiu2h3d2dj92q\"}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "contactItemActivity create");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_item);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        contactService = new ContactService(this);
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
                if (imagesrc.equals("null")) {
                    mBitmap = CodeUtils.createImage(iconText, 400, 400, BitmapFactory.decodeResource(getResources(), R.drawable.person));
                    iconImage.setImageBitmap(mBitmap);
                }
                else {

                }
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
                del_btn.setVisibility(View.INVISIBLE);
                break;
        }
        back.setVisibility(View.VISIBLE);
        handle.setVisibility(View.VISIBLE);
        title.setText(name);
        handle.setText("编辑");
        nameView.setText(name);
        mobileView.setText(mobile);
        remarkView.setText(remark);
        emailView.setText(email);
        companyView.setText(company);
        jobView.setText(job);
        homepageView.setText(homepage);
        companyAddressView.setText(company_address);
        View popupView = getLayoutInflater().inflate(R.layout.popupwindow, null);
        mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
    }
    @OnClick(R.id.button_backward)
    protected  void back () {
        finish();
    }
    @OnClick(R.id.button_forward)
    protected void editBtn () {
        Log.i("edit_btn", "正在点击编辑按钮");
        Intent intent = new Intent(ContactItemActivity.this, ContactEditActivity.class);
        intent.putExtra("type", type);
        switch (type) {
            case 1:
                intent.putExtra("index", index);
                break;
            case 2:
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
                break;
        }
        startActivityForResult(intent, 1);
    }
    @OnClick(R.id.delete_btn)
    protected void del () {
        Log.i("login 删除的id为", id);
        contactService.delContact(new SelfCallback(){
            @Override
            public void onResponse(JSONObject reponse) {
                super.onResponse(reponse);
                Log.i("edit", index + "");
                Toast.makeText(ContactItemActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                ContactPerson item = new ContactPerson(name, imagesrc,id, cuid,email,mobile, homepage, job, company, remark, company_address);
                CApplication.contactList.remove(index);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("edit", data.toString());
                return data;
            }
        });
    }
    @OnClick(R.id.contact_person_icon)
    protected void popImage (View  v) {
        Log.i("iconClick", "正在点击icon");
        Log.i("iconClick", v.toString());
        popIconImage = (ImageView) mPopupWindow.getContentView().findViewById(R.id.pop_icon);
        RelativeLayout pop_window = (RelativeLayout) mPopupWindow.getContentView().findViewById(R.id.pop_window);
        popIconImage.setImageBitmap(mBitmap);
        mPopupWindow.showAtLocation(findViewById(R.id.contact_item), Gravity.NO_GRAVITY, 0,getStatusBarHeight(ContactItemActivity.this));
//        popIconImage = (ImageView) findViewById(R.id.pop_icon);
//        popIconImage.setImageBitmap(mBitmap);
        pop_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPopupWindow.isShowing()){
                    mPopupWindow.dismiss();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("login", name);
        if (type.equals(2)) {
            id = data.getStringExtra("id");
            cuid = data.getStringExtra("cuid");
            name = data.getStringExtra("name");
            imagesrc = data.getStringExtra("imagesrc");
            company = data.getStringExtra("company");
            company_address = data.getStringExtra("company_address");
            email = data.getStringExtra("email");
            job = data.getStringExtra("job");
            mobile = data.getStringExtra("mobile");
            homepage = data.getStringExtra("homepage");
            remark = data.getStringExtra("remark");
            nameView.setText(name);
            mobileView.setText(mobile);
            remarkView.setText(remark);
            emailView.setText(email);
            companyView.setText(company);
            jobView.setText(job);
            homepageView.setText(homepage);
            companyAddressView.setText(company_address);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (type.equals(1)) {
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
            nameView.setText(name);
            mobileView.setText(mobile);
            remarkView.setText(remark);
            emailView.setText(email);
            companyView.setText(company);
            jobView.setText(job);
            homepageView.setText(homepage);
            companyAddressView.setText(company_address);
        }
    }

    /**
     * 获取状态通知栏高度
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        Log.d(TAG, "statusBarHeight:"+frame.top+"px");
        return frame.top;
    }
}
