package com.yeahs.www.qrcode;

/**
 * Created by lenovo on 2017/3/20.
 */
//联系人信息
public class ContactPerson {
    private String name;
    private String imagesrc;
    private String id;
    private String cuid;
    private String email;
    private String mobile;
    private String homepage;
    private String job;

    public String getCuid() {
        return cuid;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getJob() {
        return job;
    }

    public String getCompany() {
        return company;
    }

    public String getRemark() {
        return remark;
    }

    public String getCompany_address() {
        return company_address;
    }

    private String company;
    private String remark;
    private String company_address;
    public ContactPerson() {}
    public ContactPerson(String name, String imagesrc, String id) {
        this.name = name;
        this.imagesrc = imagesrc;
        this.id = id;
    }

    public ContactPerson(String name, String imagesrc, String id, String cuid, String email, String mobile, String homepage, String job, String company, String remark, String company_address) {
        this.name = name;
        this.imagesrc = imagesrc;
        this.id = id;
        this.cuid = cuid;
        this.email = email;
        this.mobile = mobile;
        this.homepage = homepage;
        this.job = job;
        this.company = company;
        this.remark = remark;
        this.company_address = company_address;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagesrc() {
        return imagesrc;
    }

}
