package com.wisdom.takeout.presenter;

import android.util.Log;
import android.widget.Toast;

import com.wisdom.takeout.app.TakeoutApp;
import com.wisdom.takeout.module.bean.ResponseInfo;
import com.wisdom.takeout.module.network.Content;
import com.wisdom.takeout.module.network.TakeoutService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HKWisdom on 2017/3/19.
 */

public abstract class BasePresenter {

    public Retrofit mMRetrofit;
    public TakeoutService mService;
    private static final String TAG = "BasePresenter";

    public BasePresenter() {
        mMRetrofit = new Retrofit.Builder()
                .baseUrl(Content.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mMRetrofit.create(TakeoutService.class);
    }

    Callback<ResponseInfo> mCallback = new Callback<ResponseInfo>() {
        @Override
        public void onResponse(Call<ResponseInfo> call, retrofit2.Response<ResponseInfo> response) {
            ResponseInfo info = response.body();
            if (info.getCode().equals("0")) {
                //请求数据成功,解析数据
                Log.d(TAG, "onResponse: " + response.message());
                parseJson(info.getData());
            }else {
                Log.d(TAG, "onResponse: errorerrorerrorerrorerrorerrorerrorerrorerror");

            }
        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            //请求数据失败
            Toast.makeText(TakeoutApp.sInstance, "请求数据失败", Toast.LENGTH_SHORT).show();
        }
    };

    public abstract void parseJson(String data);
}
