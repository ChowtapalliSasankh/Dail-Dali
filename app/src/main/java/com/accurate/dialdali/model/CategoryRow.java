package com.accurate.dialdali.model;

public class CategoryRow
{

    private int id;
    private String address;
    private long  mobile_no;
    private String image1;
    private String name;
    private String category_name;

    public CategoryRow(int id, String address, int mobile_no, String image1, String name, String category_name) {
        this.id = id;
        this.address = address;
        this.mobile_no = mobile_no;
        this.image1 = image1;
        this.name = name;
        this.category_name = category_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(int mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    /*int imageurl;
    String name;
    String mobile;
    String address;
    String category;

    public CategoryRow(int imageurl, String name, String mobile, String address,String category) {
        this.imageurl = imageurl;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.category = category;
    }

    public int getImageurl() {
        return imageurl;
    }

    public void setImageurl(int imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }*/
}
