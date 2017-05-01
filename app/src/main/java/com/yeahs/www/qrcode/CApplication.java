package com.yeahs.www.qrcode;

/**
 * Created by lenovo on 2017/3/22.
 */

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 16/9/7.
 */

public class CApplication extends Application{
    public static User user = new User();
    protected static ActivityController activitys = new ActivityController();
    public static List<ContactPerson> contactList = new ArrayList<ContactPerson>();
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }
}
