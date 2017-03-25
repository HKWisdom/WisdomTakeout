package com.wisdom.takeout.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wisdom.takeout.module.bean.Order;
import com.wisdom.takeout.module.bean.ResponseInfo;
import com.wisdom.takeout.ui.fragment.OrderFragment;

import java.util.List;

import retrofit2.Call;

/**
 * Created by HKWisdom on 2017/3/24.
 */

public class OrderPresenter extends BasePresenter{
    //5a4497a676e94ac28b61bc00

    public OrderFragment mOrderFragment;

    public OrderPresenter(OrderFragment orderFragment) {
        mOrderFragment = orderFragment;
    }

    public void getOrderGoodInfo(int id) {
        Call<ResponseInfo> responseInfoCall = mService.orderGoodInfo(id);
        responseInfoCall.enqueue(mCallback);
    }
    @Override
    public void parseJson(String data) {
        Gson gson = new Gson();
        List<Order> orderList = gson.fromJson(data, new TypeToken<List<Order>>() {
        }.getType());

        mOrderFragment.mAdapter.setOrderList(orderList);
        mOrderFragment.mSrlOrder.setRefreshing(false);
    }
}
