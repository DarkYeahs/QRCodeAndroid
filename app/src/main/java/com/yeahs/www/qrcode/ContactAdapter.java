package com.yeahs.www.qrcode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2017/3/20.
 */
//自定义一个适配器给listview赋值
public class ContactAdapter extends ArrayAdapter {
    private int resourceid;
    public ContactAdapter (Context context, int textviewResource, List<ContactPerson> objects) {
        super(context, textviewResource, objects);
        this.resourceid = textviewResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ContactPerson contactPerson = (ContactPerson) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceid, parent, false);
        ImageView contactView = (ImageView) view.findViewById(R.id.contact_person_icon);
        TextView contactName = (TextView) view.findViewById(R.id.contact_person_name);
        contactView.setImageResource(contactPerson.getImagesrc());
        contactName.setText(contactPerson.getName() + " (" + contactPerson.getCompany() + contactPerson.getJob() + ")");
        return view;
    }
}
