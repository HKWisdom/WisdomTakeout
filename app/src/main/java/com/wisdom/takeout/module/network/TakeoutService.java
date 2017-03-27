package com.wisdom.takeout.module.network;

import com.wisdom.takeout.module.bean.ResponseInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HKWisdom on 2017/3/19.
 */

public interface TakeoutService {
    @GET(Constants.HOME_URL)
    Call<ResponseInfo> homeList();

    @GET(Constants.LOGIN_URL)
    Call<ResponseInfo> loginByPhone(@Query("phone") String phone);

    @GET(Constants.ORDER_URL)
    Call<ResponseInfo> orderGoodInfo(@Query("user") int userId);

    @GET(Constants.BUSINESS_URL)
    Call<ResponseInfo> businessGoodInfo(@Query("sellerId") int sellerId);
}
