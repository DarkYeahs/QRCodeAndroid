package com.yeahs.www.qrcode;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yeahs.www.qrcode.ContactSql.ContactDatabaseHelper;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.yeahs.www.qrcode.ContactSql.UserDataHelper;
import com.yeahs.www.qrcode.network.ContactService;
import com.yeahs.www.qrcode.network.SelfCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

//主Activity，联系人列表跟操作，同步
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<ContactPerson> contactList = new ArrayList<>();
    private List<ContactPerson> searchContactList = new ArrayList<>();
//    private TextView search_text;
    private ContactAdapter contactAdapter;
    private ContactDatabaseHelper contactDatabaseHelper = null;
    private UserDataHelper userDataHelper = null;
    private ContactService contactService = null;
    @BindView(R.id.search_edit) EditText search_text;
    @BindView(R.id.contact_list) ListView listView;
    @BindView(R.id.search_text_container) LinearLayout mainContent;
    protected TextView user_name = null;
    private  NavigationView navigationView = null;

    private String cuid = "";
    private String name = "";
    private String mobile = "";
    private String email = "";
    private String remark = "";
    private String company = "";
    private String job = "";
    private String homepage = "";
    private String company_address = "";
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    private static int newVersion = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        user_name = (TextView) headerLayout.findViewById(R.id.user_name);
        contactService = new ContactService(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        userDataHelper = new UserDataHelper(this, "User.db", null, newVersion);
        contactDatabaseHelper = new ContactDatabaseHelper(this, "User.db", null, newVersion);
        createTable();
        getUserInfo();
        ButterKnife.bind(this);
        initContactList();
        CApplication.contactList = contactList;
        contactAdapter = new ContactAdapter(MainActivity.this, R.layout.contact_person_item, contactList);
        listView.setAdapter(contactAdapter);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void getUserInfo () {
        SQLiteDatabase db = userDataHelper.getReadableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String account = cursor.getString(cursor.getColumnIndex("account"));
            String company = cursor.getString(cursor.getColumnIndex("company"));
            String company_address = cursor.getString(cursor.getColumnIndex("company_address"));
            String homepage = cursor.getString(cursor.getColumnIndex("homepage"));
            String id = cursor.getString(cursor.getColumnIndex("uid"));
            String job = cursor.getString(cursor.getColumnIndex("job"));
//            int imagesrc = cursor.getInt(cursor.getColumnIndex("imagesrc"));
            String mobile = cursor.getString(cursor.getColumnIndex("mobile"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            CApplication.user.setName(name);
            CApplication.user.setEmail(email);
            CApplication.user.setAccount(account);
            CApplication.user.setCompany(company);
            CApplication.user.setCompany_address(company_address);
            CApplication.user.setHomepage(homepage);
            CApplication.user.setId(id);
            CApplication.user.setJob(job);
//            CApplication.user.setImagesrc(imagesrc);
            CApplication.user.setMobile(mobile);
            CApplication.user.setRemark(remark);
            Log.i("login name的值为", name);
            if (name.equals("null")) {
                user_name.setText(account);
            }
            else {
                user_name.setText(name);
            }
            Log.i("login", CApplication.user.getName());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivity.this, ContactItemActivity.class);
            intent.putExtra("id", CApplication.user.getId());
            intent.putExtra("name",  CApplication.user.getName());
            intent.putExtra("imagesrc",  CApplication.user.getImagesrc());
            intent.putExtra("company",  CApplication.user.getCompany());
            intent.putExtra("company_address",  CApplication.user.getCompany_address());
            intent.putExtra("email",  CApplication.user.getEmail());
            intent.putExtra("job",  CApplication.user.getJob());
            intent.putExtra("mobile",  CApplication.user.getMobile());
            intent.putExtra("homepage",  CApplication.user.getHomepage());
            intent.putExtra("remark",  CApplication.user.getRemark());
            intent.putExtra("type", 2);
            startActivity(intent);
            // Handle the camera actionn
        } else if (id == R.id.nav_scaner) {
            Log.i("clickName", "正在点击扫一扫按钮");
            Intent intent = new Intent(getApplication(), CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(getApplication(), AddContactPerson.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(getApplication(), SettingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 111:
                if (resultCode == RESULT_OK){
                    String qrMsg = data.getStringExtra(CodeUtils.RESULT_STRING);
                    Intent   intent = new Intent(MainActivity.this, ContactAddConfirm.class);
                    intent.putExtra("qrMsg", qrMsg);
                    startActivity(intent);
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_name.setText(CApplication.user.getName());
        contactList = CApplication.contactList;
        contactAdapter = new ContactAdapter(MainActivity.this, R.layout.contact_person_item, contactList);
        listView.setAdapter(contactAdapter);

    }
    @OnClick(R.id.contactSynchronize_btn)
    public void contactSynchronize () {
        cuid = CApplication.user.getId();
        String sqlString = "select * from contact_user where cuid = \"" + cuid + "\"";
        Log.i("login", "cuid:" + cuid);
        Log.i("login", "sql:" + sqlString);
        SQLiteDatabase db = contactDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlString, null);
        Log.i("login", "本地存储的数据" + cursor.getCount());
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("name"));
                email = cursor.getString(cursor.getColumnIndex("email"));
                company = cursor.getString(cursor.getColumnIndex("company"));
                company_address = cursor.getString(cursor.getColumnIndex("company_address"));
                homepage = cursor.getString(cursor.getColumnIndex("homepage"));
                job = cursor.getString(cursor.getColumnIndex("job"));
                mobile = cursor.getString(cursor.getColumnIndex("mobile"));
                remark = cursor.getString(cursor.getColumnIndex("remark"));

                contactService.addContact(new SelfCallback() {
                    @Override
                    public void onResponse(JSONObject reponse) {
                        super.onResponse(reponse);
                        Toast.makeText(MainActivity.this, "同步成功", Toast.LENGTH_SHORT).show();
//                        finish();
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
                            data.put("company_address", company_address);
                            data.put("homepage", homepage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("login", data.toString());
                        return data;
                    }
                });
            }
            if (!cursor.moveToNext()) {
                SQLiteDatabase dbWrite = contactDatabaseHelper.getWritableDatabase();
                dbWrite.delete("contact_user", "cuid = ?", new String[] {cuid});
            }
        }
        else {
            Toast.makeText(this, "当前已与服务器同步", Toast.LENGTH_SHORT).show();
        }
    }
    public void initContactList () {
//        contactDatabaseHelper.getWritableDatabase();
        SQLiteDatabase db = contactDatabaseHelper.getReadableDatabase();
        cuid = CApplication.user.getId();
        Cursor cursor = db.query("contact_user", new String[] {"*"}, "cuid = ?", new String[] {cuid}, null, null, null);
        Log.i("login", "本地存储的数据" + cursor.getCount());
        while (cursor.moveToNext()) {
            Log.i("login", "获取本地储存数据");
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String company = cursor.getString(cursor.getColumnIndex("company"));
            String company_address = cursor.getString(cursor.getColumnIndex("company_address"));
            String homepage = cursor.getString(cursor.getColumnIndex("homepage"));
            String cuid = cursor.getString(cursor.getColumnIndex("cuid"));
            String job = cursor.getString(cursor.getColumnIndex("job"));
            String mobile = cursor.getString(cursor.getColumnIndex("mobile"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            ContactPerson contact = new ContactPerson(name, "", "", cuid, email, mobile, homepage, job, company, remark, company_address);
            MainActivity.this.contactList.add(contact);
        }
        getContactList();
    }
    @OnTextChanged(R.id.search_edit)
    protected void textChange () {
        Log.w("login", "正在输入");
        Log.i("login", "contactList的size为" + contactList.size());
        searchContactList.clear();
        String searchText = search_text.getText().toString();
        Log.i("login", searchText);
        if (searchText.equals("")) {
            getNewData(searchText);
            contactAdapter = new ContactAdapter(this, R.layout.contact_person_item,
                    searchContactList);
            listView.setAdapter(contactAdapter);
        }
        else {
            contactAdapter = new ContactAdapter(this, R.layout.contact_person_item,
                    contactList);
            listView.setAdapter(contactAdapter);
        }
    }
    private void getNewData(String input_info) {
        //遍历list
        Log.i("login", "contactList的长度为");
        for (int i = 0; i < contactList.size(); i++) {
            //如果遍历到的名字包含所输入字符串
            Log.i("login", "循环遍历中");
            ContactPerson item = contactList.get(i);
            if (item.getName().contains(input_info)) {
                //将遍历到的元素重新组成一个list
                ContactPerson search_item = new ContactPerson(item.getName(), item.getImagesrc(), item.getId());
                searchContactList.add(search_item);
            }
        }
    }
    private void getContactList () {
        contactService.getContactList(new SelfCallback() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                JSONArray contactList = null;
                try {
                    contactList = response.getJSONArray("data");
                    Log.i("contactList", contactList.toString());
                    for(int i = 0;i < contactList.length();i++){
                        JSONObject contactItem = contactList.getJSONObject(i);
                        String name = contactItem.getString("name");
                        String id = contactItem.getString("id");
                        String cuid = contactItem.getString("cuid");
                        String mobile = contactItem.getString("mobile");
                        String email = contactItem.getString("email");
                        String homepage = contactItem.getString("homepage");
                        String job = contactItem.getString("job");
                        String company = contactItem.getString("company");
                        String company_address = contactItem.getString("company_address");
                        String remark = contactItem.getString("remark");
                        ContactPerson contact = new ContactPerson(name, "", id, cuid, email, mobile, homepage, job, company, remark, company_address);
                        MainActivity.this.contactList.add(contact);
                    }
                    searchContactList = MainActivity.this.contactList;
                    Log.i("login", "获得的list的长度为" + searchContactList.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("login", e.toString());
                }
                Log.i("getContact", response.toString());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }

            @Override
            public JSONObject getParams() {
                JSONObject data = new JSONObject();
                try {
                    data.put("cuid", CApplication.user.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return data;
            }
        });
    }
    @OnItemClick(R.id.contact_list)
    protected void itemTouch (AdapterView<?> arg0, View arg1, int arg2,
                              long arg3) {
        ContactPerson contactItem = searchContactList.get(arg2);
        Integer index = contactList.indexOf(contactItem);
        Intent intent = new Intent(MainActivity.this, ContactItemActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("type", 1);
        startActivity(intent);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                search_text.setFocusable(true);
                search_text.setFocusableInTouchMode(true);
                search_text.requestFocus();
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {

                return true;
            }
        }
        return false;
    }


    public void createTable() {
        userDataHelper.getWritableDatabase();
    }
}
