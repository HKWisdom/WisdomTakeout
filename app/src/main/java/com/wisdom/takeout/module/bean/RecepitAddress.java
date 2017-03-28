package com.wisdom.takeout.module.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by lidongzhi on 2017/3/23.
 */
@DatabaseTable(tableName = "t_address")
public class RecepitAddress implements Serializable{
    @DatabaseField(generatedId = true) //自动增长
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "sex")
    private String sex;
    @DatabaseField(columnName = "phone")
    private String phone;
    @DatabaseField(columnName = "phoneOther")
    private String phoneOther;
    @DatabaseField(columnName = "address")
    private String address;
    @DatabaseField(columnName = "detailAddress")
    private String detailAddress;
    @DatabaseField(columnName = "label")
    private String label;
    //标明地址属于谁,支持多用户
    @DatabaseField(columnName = "userId")
    private String userId;

    public RecepitAddress() {
    }

    public RecepitAddress(int id, String name, String sex, String phone, String phoneOther, String address, String detailAddress, String label, String userId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.phoneOther = phoneOther;
        this.address = address;
        this.detailAddress = detailAddress;
        this.label = label;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneOther() {
        return phoneOther;
    }

    public void setPhoneOther(String phoneOther) {
        this.phoneOther = phoneOther;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
