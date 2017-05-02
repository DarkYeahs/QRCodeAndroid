package com.yeahs.www.qrcode.network;

import android.app.Activity;

/**
 * Created by lenovo on 2017/4/22.
 */

public class UserService {
    public static baseService baseService = null;
    private static final String getUserInfoUrl = "/user/getuserinfo";
    private static final String editUserInfoUrl = "/user/editUserInfo";
    public UserService (Activity activity) {
        baseService = new baseService(activity);
    }
    public void getUserInfo (SelfCallback callback) {
        baseService.get(getUserInfoUrl, callback);
    }
    public void editUserInfo (SelfCallback callback) {
        baseService.post(editUserInfoUrl, callback);
    }
}
