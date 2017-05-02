package com.yeahs.www.qrcode.network;

import android.app.Activity;

/**
 * Created by lenovo on 2017/4/22.
 */

public class LoginService {
    public static baseService baseService = null;
    private static final String loginUrl = "/login";
    private static final String registryUrl = "/login/registry";
    private static final String autoLoginUrl = "/login/autologin";
    private static final String logoutUrl = "/login/autologin";
    private static final String forgetPasswordUrl = "/login/forgetpassword";
    public LoginService (Activity activity) {
        baseService = new baseService(activity);
    }
    public void login (SelfCallback callback) {
        baseService.post(loginUrl, callback);
    }
    public void autologin (SelfCallback callback) {
        baseService.post(autoLoginUrl, callback);
    }
    public void logout (SelfCallback callback) {
        baseService.post(logoutUrl, callback);
    }
    public void registry (SelfCallback callback) {
        baseService.post(registryUrl, callback);
    }
    public void forgetPassword (SelfCallback callback) {
        baseService.post(forgetPasswordUrl, callback);
    }
}
