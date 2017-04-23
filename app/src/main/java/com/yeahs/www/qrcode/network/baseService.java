package com.yeahs.www.qrcode.network;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by lenovo on 2017/4/21.
 */

public class baseService {
    public static Activity selActivity = null;
    public baseService (Activity activity) {
        selActivity = activity;
    }
    public void get (String url, SelfCallback callback) {
        final SelfCallback getCallback = callback;
        JSONObject data = getCallback.getParams();
        Iterator it = data.keys();
        String param = "";
        while (it.hasNext()) {
            String key = (String) it.next();
            try {
                String value = data.getString(key);
                param += key + "=" + value;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        url += "?" + param;
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getCallback.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCallback.onErrorResponse(error);
            }
        });
        Volley.newRequestQueue(selActivity).add(stringRequest);
    }
    public void post (String url, SelfCallback callback) {
        final SelfCallback postCallback = callback;
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, postCallback.getParams(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        postCallback.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                postCallback.onErrorResponse(error);
            }
        });
        Volley.newRequestQueue(selActivity).add(stringRequest);
    }
}


