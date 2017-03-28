package com.wisdom.takeout.presenter;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.wisdom.takeout.app.TakeoutApp;
import com.wisdom.takeout.module.bean.ResponseInfo;
import com.wisdom.takeout.module.bean.User;
import com.wisdom.takeout.module.dao.TakeoutOpenHelper;
import com.wisdom.takeout.ui.activity.LoginActivity;

import java.sql.SQLException;
import java.sql.Savepoint;

import retrofit2.Call;

/**
 * Created by HKWisdom on 2017/3/24.
 */

public class LoginPresenter extends BasePresenter {
    private static final String TAG = "LoginPresenter";

    private LoginActivity mLoginActivity;

    public LoginPresenter(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
    }

    /**
     * 提交信息到服务器
     * @param phone
     * @param type
     */
    public void loginByPhone(String phone,int type) {
        Call<ResponseInfo> userCall = mService.loginByPhone(phone);
        userCall.enqueue(mCallback);
    }
    @Override
    public void parseJson(String data) {
        Gson gson = new Gson();
        User user = gson.fromJson(data, User.class);
        TakeoutApp.sUser = user;

        boolean isLoginOk = false;
        //创建takeout数据库
        TakeoutOpenHelper takeOutHelper = new TakeoutOpenHelper(mLoginActivity);
        SQLiteDatabase database = takeOutHelper.getWritableDatabase();
        //获取数据库连接connection以后才能做CRUD
        AndroidDatabaseConnection connection = new AndroidDatabaseConnection(database,true);
        //使用conncetion直接保存一个user,使用DAO层
        Savepoint savepoint = null;
        try {
             savepoint = connection.setSavePoint("start");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Dao<User, Integer> userDao = takeOutHelper.getDao(User.class);

            //取消自动提交
            userDao.setAutoCommit(connection,false);

            User oldUser = userDao.queryForId(user.getId());
            if (oldUser != null) {
                //老用户
                userDao.update(user);
                Log.d(TAG, "parseJson: " + "老用户");
            }else {
                //新用户
                userDao.create(user);
                Log.d(TAG, "parseJson: " + "新用户");
            }
            userDao.createIfNotExists(user);
            //提交完成这个事务
            connection.commit(savepoint);

            isLoginOk = true;

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "parseJson: " + "创建用户失败");
            try {
                //创建失败,回滚
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        mLoginActivity.onLoginResult(isLoginOk);

    }
}
