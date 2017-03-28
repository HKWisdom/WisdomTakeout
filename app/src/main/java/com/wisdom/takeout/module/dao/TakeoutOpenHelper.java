package com.wisdom.takeout.module.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wisdom.takeout.module.bean.RecepitAddress;
import com.wisdom.takeout.module.bean.User;

import java.sql.SQLException;

/**
 * Created by HKWisdom on 2017/3/24.
 */

public class TakeoutOpenHelper extends OrmLiteSqliteOpenHelper{
    public TakeoutOpenHelper(Context context) {
        super(context, "takeout.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, RecepitAddress.class); //新增地址表
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.createTable(connectionSource, RecepitAddress.class); //新增地址表
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
