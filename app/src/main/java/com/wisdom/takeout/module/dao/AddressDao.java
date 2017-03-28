package com.wisdom.takeout.module.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.wisdom.takeout.module.bean.RecepitAddress;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by lidongzhi on 2017/3/23.
 */

public class AddressDao {

    private  Dao<RecepitAddress, Integer> mAddressDao;

    public AddressDao(Context context) {
        TakeoutOpenHelper takeoutOpenHelper = new TakeoutOpenHelper(context);
        try {
            mAddressDao = takeoutOpenHelper.getDao(RecepitAddress.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addRecepitAddress(RecepitAddress address){
        boolean isOk = false;
        try {
            mAddressDao.create(address);
            isOk = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    public boolean deleteRecepitAddress(RecepitAddress address){
        boolean isOk = false;
        try {
            mAddressDao.delete(address);
            isOk = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    public boolean update(RecepitAddress address){
        boolean isOk = false;
        try {
            mAddressDao.update(address);
            isOk = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    public List<RecepitAddress> queryAllAddress(){
        try {
            return mAddressDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
