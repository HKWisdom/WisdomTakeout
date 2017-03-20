package com.wisdom.takeout.module.network;

import com.wisdom.takeout.module.bean.ResponseInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by HKWisdom on 2017/3/19.
 */

public interface TakeoutService {
    @GET(Content.HOME_URL)
    Call<ResponseInfo> homeList();
}
