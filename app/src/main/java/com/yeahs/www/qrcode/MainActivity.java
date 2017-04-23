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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<ContactPerson> contactList = new ArrayList<>();
    private List<ContactPerson> searchContactList = new ArrayList<>();
//    private TextView search_text;
    private ContactAdapter contactAdapter;
//    private ContactDatabaseHelper contactDatabaseHelper;
    private UserDataHelper userDataHelper = null;
//    private final static String TAG = "SqliteTest";
    private ContactService contactService = null;
    @BindView(R.id.search_edit) EditText search_text;
    @BindView(R.id.contact_list) ListView listView;
    @BindView(R.id.search_text_container) LinearLayout mainContent;
    protected TextView user_name = null;
    private  NavigationView navigationView = null;
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
        createTable();
        getUserInfo();
        ButterKnife.bind(this);
        init();
        initContactList();
        contactAdapter = new ContactAdapter(MainActivity.this, R.layout.contact_person_item, contactList);
        Log.w("adapt",  contactAdapter.getCount() + "");
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
            user_name.setText(name);
            Log.i("login", cursor.getString(cursor.getColumnIndex("name")));
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

    public void initContactList () {
        getContactList();
//        int j = 0;
//        SQLiteDatabase db = contactDatabaseHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        for (int i = 0; i < 20 ; i++) {
//            ContactPerson contactPerson = new ContactPerson("张丽蓉", R.mipmap.person, ++j + "");
//            values.put("username", "张丽蓉");
//            values.put("id", j);
//            db.insert("user", null, values);
//            values.clear();
//            ContactPerson contactPerson_item = new ContactPerson("陈业涛", R.mipmap.self, ++j + "");
//            values.put("username", "陈业涛");
//            values.put("id", j);
//            db.insert("user", null, values);
//            values.clear();
//            contactList.add(contactPerson);
//            contactList.add(contactPerson_item);
//        }
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
                        ContactPerson contact = new ContactPerson(name, R.mipmap.person, id, cuid, email, mobile, homepage, job, company, remark, company_address);
                        MainActivity.this.contactList.add(contact);
                    }
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
                    data.put("cuid", "cewcecwedw");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("getContactList", data.toString());
                return data;
            }
        });
    }
    protected void init () {
        search_text.addTextChangedListener(new SearchTextWatcher());
    }
    private List<ContactPerson> getNewData(String input_info) {
        //遍历list
        for (int i = 0; i < contactList.size(); i++) {
            ContactPerson item = contactList.get(i);
            //如果遍历到的名字包含所输入字符串
            if (item.getName().contains(input_info)) {
                //将遍历到的元素重新组成一个list
                ContactPerson search_item = new ContactPerson(item.getName(), item.getImagesrc(), item.getId());
                searchContactList.add(search_item);
            }
        }
        return searchContactList;
    }
    @OnItemClick(R.id.contact_list)
    protected void itemTouch (AdapterView<?> arg0, View arg1, int arg2,
                              long arg3) {
        ContactPerson contactItem = contactList.get(arg2);
        Intent intent = new Intent(MainActivity.this, ContactItemActivity.class);
        intent.putExtra("id", contactItem.getId());
        intent.putExtra("cuid", contactItem.getCuid());
        intent.putExtra("name", contactItem.getName());
        intent.putExtra("imagesrc", contactItem.getImagesrc());
        intent.putExtra("company", contactItem.getCompany());
        intent.putExtra("company_address", contactItem.getCompany_address());
        intent.putExtra("email", contactItem.getEmail());
        intent.putExtra("job", contactItem.getJob());
        intent.putExtra("mobile", contactItem.getMobile());
        intent.putExtra("homepage", contactItem.getHomepage());
        intent.putExtra("remark", contactItem.getRemark());
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

    class SearchTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.w("textChanged", "正在输入");
            searchContactList.clear();
            if (search_text.getText() != null) {
                String input_info = search_text.getText().toString();
                searchContactList = getNewData(input_info);
                contactAdapter = new ContactAdapter(MainActivity.this, R.layout.contact_person_item,
                        searchContactList);
                listView.setAdapter(contactAdapter);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
