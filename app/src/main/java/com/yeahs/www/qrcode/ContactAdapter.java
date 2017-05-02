package com.yeahs.www.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by lenovo on 2017/3/20.
 */
//自定义一个适配器给listview赋值
public class ContactAdapter extends ArrayAdapter {
    private int resourceid;
    protected ImageView contactView = null;
    protected View view = null;
    protected TextView contactName = null;
    protected ContactPerson contactPerson = null;
    public ContactAdapter (Context context, int textviewResource, List<ContactPerson> objects) {
        super(context, textviewResource, objects);
        this.resourceid = textviewResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        contactPerson = (ContactPerson) getItem(position);
        view = LayoutInflater.from(getContext()).inflate(resourceid, parent, false);
        contactView = (ImageView) view.findViewById(R.id.contact_person_icon);
        contactName = (TextView) view.findViewById(R.id.contact_person_name);
        contactView.setImageResource(R.drawable.ic_head);
        contactName.setText(contactPerson.getName() + " (" + contactPerson.getCompany() + contactPerson.getJob() + ")");
        return view;
    }
}
