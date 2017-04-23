package com.yeahs.www.qrcode.network;

import android.app.Activity;

/**
 * Created by lenovo on 2017/4/22.
 */

public class FeedbackService {
    public static baseService baseService = null;
    private static final String feedbackUrl = "";
    public FeedbackService (Activity activity) {
        baseService = new baseService(activity);
    }
    public void feedback (SelfCallback callback) {
        baseService.post(feedbackUrl, callback);
    }
}
