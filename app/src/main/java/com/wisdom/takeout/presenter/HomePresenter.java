package com.wisdom.takeout.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wisdom.takeout.module.bean.ResponseInfo;
import com.wisdom.takeout.module.bean.Seller;
import com.wisdom.takeout.ui.fragment.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

/**
 * Created by HKWisdom on 2017/3/20.
 */

public class HomePresenter extends BasePresenter {

    private HomeFragment mHomeFragment;

    public HomePresenter(HomeFragment homeFragment) {
        this.mHomeFragment = homeFragment;
    }

    /**
     * 加载数据
     */
    public void loadData() {
        Call<ResponseInfo> homeList = mService.homeList();
        homeList.enqueue(mCallback);
    }

    /**
     * 解析数据
     * @param json
     */
    @Override
    public void parseJson(String json) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(json);

            String nearbySellerList = jsonObject.getString("nearbySellerList");

            List<Seller> nearbyList = gson.fromJson(nearbySellerList, new TypeToken<List<Seller>>() {
            }.getType());

            String otherSellerList = jsonObject.getString("otherSellerList");

            List<Seller> otherList = gson.fromJson(otherSellerList, new TypeToken<List<Seller>>() {
            }.getType());

            //设置数据
            mHomeFragment.mAdapter.setData(nearbyList,otherList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
