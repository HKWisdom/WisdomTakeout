package com.wisdom.takeout.app;

import android.app.Application;

import com.wisdom.takeout.module.bean.CacheSelectedInfo;
import com.wisdom.takeout.module.bean.User;
import com.wisdom.takeout.module.network.Constants;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by HKWisdom on 2017/3/20.
 */

public class TakeoutApp extends Application {

    public static TakeoutApp sInstance;
    public static User sUser;
    private List<CacheSelectedInfo> infos = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sUser = new User();
        sUser.setId(-1);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public int queryCacheSelectedInfoByGoodsId(int goodsId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getGoodsId() == goodsId){
                count = info.getCount();
                break;
            }
        }
        return count;
    }

    public int queryCacheSelectedInfoByTypeId(int typeId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getTypeId() == typeId){
                count = count + info.getCount();
            }
        }
        return count;
    }

    public int queryCacheSelectedInfoBySellerId(int sellerId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getSellerId() == sellerId){
                count = count + info.getCount();
            }
        }
        return count;
    }

    public void addCacheSelectedInfo(CacheSelectedInfo info) {
        infos.add(info);
    }

    public void clearCacheSelectedInfo(int sellerId){
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getSellerId() == sellerId){
                infos.remove(info);
            }
        }
    }

    public void deleteCacheSelectedInfo(int goodsId) {
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if (info.getGoodsId() == goodsId) {
                infos.remove(info);
                break;
            }
        }
    }

    public void updateCacheSelectedInfo(int goodsId, int operation) {
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if (info.getGoodsId() == goodsId) {
                switch (operation) {
                    case Constants.ADD:
                        info.setCount(info.getCount() + 1);
                        break;
                    case Constants.MINUS:
                        info.setCount(info.getCount() - 1);
                        break;
                }

            }
        }
    }
}
