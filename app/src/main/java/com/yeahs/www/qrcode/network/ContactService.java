package com.yeahs.www.qrcode.network;

import android.app.Activity;

/**
 * Created by lenovo on 2017/4/22.
 */

public class ContactService {
    public static baseService baseService = null;
    private static final String addContactUrl = "http://172.20.10.3:4000/contact/addcontact";
    private static final String editContactUrl = "http://172.20.10.3:4000/contact/editcontact";
    private static final String delContactUrl = "";
    private static final String getContactListUrl = "http://172.20.10.3:4000/contact/getcontactlist";
    public ContactService (Activity activity) {
        baseService = new baseService(activity);
    }
    public void addContact (SelfCallback callback) {
        baseService.post(addContactUrl, callback);
    }
    public void editContact (SelfCallback callback) {
        baseService.post(editContactUrl, callback);
    }
    public void delContact (SelfCallback callback) {
        baseService.post(delContactUrl, callback);
    }
    public void getContactList (SelfCallback callback) {
        baseService.get(getContactListUrl, callback);
    }
}
