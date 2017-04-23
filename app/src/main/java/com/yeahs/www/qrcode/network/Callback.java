package com.yeahs.www.qrcode.network;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by lenovo on 2017/4/21.
 */

public interface Callback {
    void onResponse (JSONObject response);
    void onErrorResponse (VolleyError error);
    JSONObject getParams ();
}
