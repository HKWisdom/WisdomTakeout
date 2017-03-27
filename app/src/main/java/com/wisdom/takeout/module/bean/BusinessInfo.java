package com.wisdom.takeout.module.bean;

import java.util.List;

/**
 * Created by Apple on 2016/9/2.
 */
public class BusinessInfo {
    private List<GoodsTypeInfo> list;

    public BusinessInfo() {
    }

    public BusinessInfo(List<GoodsTypeInfo> list) {
        this.list = list;
    }

    public List<GoodsTypeInfo> getList() {
        return list;
    }

    public void setList(List<GoodsTypeInfo> list) {
        this.list = list;
    }
}
