package com.wisdom.takeout.module.bean;

/**
 * 选中的商品的缓存信息
 */

public class CacheSelectedInfo {

    private int sellerId;
    private int userId;
    private int typeId;
    private int goodsId;
    private int count;

    public CacheSelectedInfo(int sellerId, int userId, int typeId, int goodsId, int count) {
        this.sellerId = sellerId;
        this.userId = userId;
        this.typeId = typeId;
        this.goodsId = goodsId;
        this.count = count;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
