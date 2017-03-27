package com.wisdom.takeout.module.bean;

import java.util.List;

/**
 * Created by Apple on 2016/9/2.
 */
public class GoodsTypeInfo {
    private int id;
    private String info;
    private List<GoodsInfo> list;
    private String name;
    private int count;//同一种类型选中的数量
    private int redDotCount;

    public int getRedDotCount() {
        return redDotCount;
    }

    public void setRedDotCount(int redDotCount) {
        this.redDotCount = redDotCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GoodsTypeInfo() {
    }

    public GoodsTypeInfo(int id, String info, List<GoodsInfo> list, String name) {
        this.id = id;
        this.info = info;
        this.list = list;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<GoodsInfo> getList() {
        return list;
    }

    public void setList(List<GoodsInfo> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
