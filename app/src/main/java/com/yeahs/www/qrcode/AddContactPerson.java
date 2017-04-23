package com.yeahs.www.qrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yeahs.www.qrcode.network.ContactService;
import com.yeahs.www.qrcode.network.SelfCallback;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContactPerson extends AppCompatActivity {

    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    private ContactService contactService = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_person);
        ButterKnife.bind(this);
        back.setVisibility(View.VISIBLE);
        back.setText("返回");
        title.setText("添加联系人");
    }

    @OnClick(R.id.button_backward)
    protected void back () {
        finish();
    }
    @OnClick(R.id.button_forward)
    public void addContact () {
        contactService.addContact(new SelfCallback() {
            @Override
            public void onResponse(JSONObject reponse) {
                super.onResponse(reponse);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }

            @Override
            public JSONObject getParams() {
                return super.getParams();
            }
        });
    }
}
