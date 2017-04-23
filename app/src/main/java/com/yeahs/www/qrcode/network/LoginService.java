package com.yeahs.www.qrcode.network;

import android.app.Activity;

/**
 * Created by lenovo on 2017/4/22.
 */

public class LoginService {
    public static baseService baseService = null;
    private static final String loginUrl = "http://172.20.10.3:4000/login";
    private static final String registryUrl = "";
    private static final String logoutUrl = "";
    private static final String forgetPasswordUrl = "";
    public LoginService (Activity activity) {
        baseService = new baseService(activity);
    }
    public void login (SelfCallback callback) {
        baseService.post(loginUrl, callback);
    }
    public void logout (SelfCallback callback) {
        baseService.post(logoutUrl, callback);
    }
    public void registry (SelfCallback callback) {
        baseService.post(registryUrl, callback);
    }
    public void forgetPassword (SelfCallback callback) {
        baseService.post(registryUrl, callback);
    }
}
