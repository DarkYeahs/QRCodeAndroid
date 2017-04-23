package com.yeahs.www.qrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//添加联系人
public class AddContactPerson extends AppCompatActivity {

    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;

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
}
