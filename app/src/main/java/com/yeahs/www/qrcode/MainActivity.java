package com.yeahs.www.qrcode;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yeahs.www.qrcode.ContactSql.ContactDatabaseHelper;

import com.uuzuche.lib_zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
//主Activity，联系人列表跟操作，同步
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<ContactPerson> contactList = new ArrayList<>();
    private List<ContactPerson> searchContactList = new ArrayList<>();
//    private TextView search_text;
    private ContactAdapter contactAdapter;
    private ContactDatabaseHelper contactDatabaseHelper;
    private final static String TAG = "SqliteTest";
    @BindView(R.id.search_edit) EditText search_text;
    @BindView(R.id.contact_list) ListView listView;
    @BindView(R.id.search_text_container) LinearLayout mainContent;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    private static int newVersion = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        contactDatabaseHelper = new ContactDatabaseHelper(this, "User.db", null, newVersion);
        this.createTable();
        ButterKnife.bind(this);
        init();
        initContactList();
        Log.w("contact_size", contactList.get(0).getName());
        contactAdapter = new ContactAdapter(MainActivity.this, R.layout.contact_person_item, contactList);
        Log.w("adapt",  contactAdapter.getCount() + "");
        listView.setAdapter(contactAdapter);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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
        Log.i("return", requestCode + "");
        Log.i("return", resultCode + "");
        Log.i("return", RESULT_OK + "");
        switch (requestCode) {
            case 111:
                if (resultCode == RESULT_OK){
                    String qrMsg = data.getStringExtra(CodeUtils.RESULT_STRING);
                    Intent intent = new Intent(MainActivity.this, ContactAddConfirm.class);
                    intent.putExtra("qrMsg", qrMsg);
                    startActivity(intent);
                }
        }
    }

    public void initContactList () {
        int j = 0;
        SQLiteDatabase db = contactDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < 20 ; i++) {
            ContactPerson contactPerson = new ContactPerson("张丽蓉", R.mipmap.person, ++j);
            values.put("username", "张丽蓉");
            values.put("id", j);
            db.insert("user", null, values);
            values.clear();
            ContactPerson contactPerson_item = new ContactPerson("陈业涛", R.mipmap.self, ++j);
            values.put("username", "陈业涛");
            values.put("id", j);
            db.insert("user", null, values);
            values.clear();
            contactList.add(contactPerson);
            contactList.add(contactPerson_item);
        }
    }
    protected void init () {
//        search_text = (TextView) findViewById(R.id.search_edit);
        search_text.addTextChangedListener(new SearchTextWatcher());
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                Log.i("name", contactList.get(arg2).getId() + "");
//            }
//
//        });
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
        Log.i("name", contactList.get(arg2).getId() + "");
        Intent intent = new Intent(MainActivity.this, ContactItemActivity.class);
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
        Log.i(TAG, "main create table");
        contactDatabaseHelper.getWritableDatabase();
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
