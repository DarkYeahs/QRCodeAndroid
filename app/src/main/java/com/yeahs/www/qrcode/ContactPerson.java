package com.yeahs.www.qrcode;

/**
 * Created by lenovo on 2017/3/20.
 */
//联系人信息
public class ContactPerson {
    private String name;
    private int imagesrc;
    private int id;
    public ContactPerson(String name, int imagesrc, int id) {
        this.name = name;
        this.imagesrc = imagesrc;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImagesrc() {
        return imagesrc;
    }
}
