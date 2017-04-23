package com.yeahs.www.qrcode.network;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/4/21.
 */
public class SelfCallback implements Callback {
    public void onResponse (JSONObject reponse){
    };
    public void onErrorResponse (VolleyError error) {
    };
    public JSONObject getParams () {
        JSONObject  params = new JSONObject();
        return params;
    };
}
