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
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yeahs.www.qrcode.ContactSql.ContactDatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//联系人信息展示类
public class ContactItemActivity extends AppCompatActivity {
    @BindView(R.id.contact_person_icon) ImageView iconImage;
    @BindView(R.id.button_backward) Button back;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_forward) Button handle;
//    @BindView(R.id.pop_icon) ImageView popIconImage;
    private final static String TAG = "SqliteTest";
    private ContactDatabaseHelper dbHelper;
    private static int newVersion = 1;
    protected ImageView popIconImage = null;
    public Bitmap mBitmap = null;
    private PopupWindow mPopupWindow;
    private String iconText = "{\"name\": \"Ye抽我抽我抽我ahs\",\"mobile\": \"93202983\",\"remark\": \"nwd财务处往往成为kn\",\"company\": \"测试公司\",\"job\": \"测试错误错误错误错误职位\",\"homepage\": \"测试职财务处我看我能看位\",\"addr\": \"侧hi持物会常务会成为hi贺成为测测为此我擦我擦我擦词我吃完iu\",\"userid\": \"cne92ei2ndiu2h3d2dj92q\"}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "contactItemActivity create");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_item);
        ButterKnife.bind(this);
        back.setVisibility(View.VISIBLE);
        handle.setVisibility(View.VISIBLE);
        title.setText("张丽蓉");
        handle.setText("编辑");
        mBitmap = CodeUtils.createImage(iconText, 400, 400, BitmapFactory.decodeResource(getResources(), R.drawable.person));
        iconImage.setImageBitmap(mBitmap);
        View popupView = getLayoutInflater().inflate(R.layout.popupwindow, null);
        mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        dbHelper = new ContactDatabaseHelper(this, "User.db", null, newVersion);
        this.queryData();
    }

    public void queryData() {
        Log.i("queryData", "这里是queryData函数开始");
        StringBuffer stringBuffer = new StringBuffer();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.i("queryData", "循环遍历中");
                // 遍历Cursor对象，取出数据并打印
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(2); //index从0开始的，password位于第三
                stringBuffer.append("username: " + username + ", id: " + password + "\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.i(TAG, stringBuffer.toString());
        Log.i("queryData", "这里是queryData函数结束");
    }
    @OnClick(R.id.button_backward)
    protected  void back () {
        finish();
    }
    @OnClick(R.id.button_forward)
    protected void editBtn () {
        Log.i("edit_btn", "正在点击编辑按钮");
        Intent intent = new Intent(ContactItemActivity.this, ContactEditActivity.class);
        startActivity(intent);
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
