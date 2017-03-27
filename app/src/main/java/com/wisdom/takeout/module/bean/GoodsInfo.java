package com.wisdom.takeout.module.bean;

import java.io.Serializable;

/**
 * Created by Apple on 2016/9/2.
 */
public class GoodsInfo implements Serializable{
    private boolean bargainPrice;
    private String form;
    private String icon;
    private int id;
    private int monthSaleNum;
    private String name;
    private boolean isNew;
    private float newPrice;
    private int oldPrice;

    private int count;//商品选择的数量
    private int typeId;//商品类型的id
    private int sellerId;//商铺的id
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GoodsInfo() {
    }

    public GoodsInfo(boolean bargainPrice, String form, String icon, int id, int monthSaleNum, String name, boolean isNew, float newPrice, int oldPrice) {
        this.bargainPrice = bargainPrice;
        this.form = form;
        this.icon = icon;
        this.id = id;
        this.monthSaleNum = monthSaleNum;
        this.name = name;
        this.isNew = isNew;
        this.newPrice = newPrice;
        this.oldPrice = oldPrice;
    }

    public boolean isBargainPrice() {
        return bargainPrice;
    }

    public void setBargainPrice(boolean bargainPrice) {
        this.bargainPrice = bargainPrice;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonthSaleNum() {
        return monthSaleNum;
    }

    public void setMonthSaleNum(int monthSaleNum) {
        this.monthSaleNum = monthSaleNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }
}
