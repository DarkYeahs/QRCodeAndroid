package com.yeahs.www.qrcode;

/**
 * Created by lenovo on 2017/4/24.
 */

public class User {
    private String company;
    private String remark;
    private String company_address;
    private String name;
    private int imagesrc;
    private String id;

    private String account;
    private String email;
    private String mobile;
    private String homepage;
    private String job;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getName() {
        return name;
    }

    public int getImagesrc() {
        return imagesrc;
    }

    public String getId() {
        return id;
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

    public void setCompany(String company) {
        this.company = company;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagesrc(int imagesrc) {
        this.imagesrc = imagesrc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
