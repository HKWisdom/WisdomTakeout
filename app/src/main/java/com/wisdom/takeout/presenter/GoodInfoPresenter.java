package com.wisdom.takeout.presenter;

import android.widget.AbsListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wisdom.takeout.module.bean.GoodsInfo;
import com.wisdom.takeout.module.bean.GoodsTypeInfo;
import com.wisdom.takeout.module.bean.ResponseInfo;
import com.wisdom.takeout.ui.fragment.GoodsInfoFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by HKWisdom on 2017/3/25.
 */

public class GoodInfoPresenter extends BasePresenter {

    private GoodsInfoFragment mGoodsInfoFragment;
    private List<GoodsInfo> mGoodsInfosList;
    private List<GoodsTypeInfo> mTypeInfoList;

    public GoodInfoPresenter(GoodsInfoFragment goodsInfoFragment) {
        mGoodsInfoFragment = goodsInfoFragment;
    }

    public void getGoodInfoData(int sellerId) {
        Call<ResponseInfo> responseInfoCall = mService.businessGoodInfo(sellerId);
        responseInfoCall.enqueue(mCallback);
    }
    @Override
    public void parseJson(String data) {
        mGoodsInfosList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            String list = jsonObject.getString("list");
            Gson gson = new Gson();
            mTypeInfoList = gson.fromJson(list, new TypeToken<List<GoodsTypeInfo>>() {}.getType());

            mGoodsInfoFragment.mAdapter.setInfoList(mTypeInfoList);

            for (int i = 0; i < mTypeInfoList.size(); i++) {

                GoodsTypeInfo goodsTypeInfo = mTypeInfoList.get(i);
                List<GoodsInfo> goodsInfos = goodsTypeInfo.getList();

                for (int j = 0; j < goodsInfos.size(); j++) {
                    GoodsInfo goodsInfo = goodsInfos.get(j);
                    goodsInfo.setTypeId(goodsTypeInfo.getId());
                    goodsInfo.setTypeName(goodsTypeInfo.getName());
                }

                mGoodsInfosList.addAll(goodsInfos);
            }
            mGoodsInfoFragment.mGoodsAdapter1.setGoodsInfoList(mGoodsInfosList);

            mGoodsInfoFragment.mSlhlv.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    //获取到listview第一个条目对应类别的id
                    int typeId = mGoodsInfosList.get(firstVisibleItem).getTypeId();
                    //根据typeId获取条目index
                    int newIndex = getTypeIndexByType(typeId);
                    //将newIndex赋值给selectedPosition
                    if (newIndex != mGoodsInfoFragment.mAdapter.selectPosition) {
                        mGoodsInfoFragment.mAdapter.selectPosition = newIndex;
                        mGoodsInfoFragment.mAdapter.notifyDataSetChanged();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<GoodsInfo> getShopCarList() {
        List<GoodsInfo> shopCarList = new ArrayList<>();
        for (int i = 0; i < mGoodsInfosList.size(); i++) {
            GoodsInfo goodsInfo = mGoodsInfosList.get(i);
            if (goodsInfo.getCount() > 0) {
                shopCarList.add(goodsInfo);
            }
        }
        return shopCarList;
    }

    //根据typeId获取左侧列表对应的index
    public int getTypeIndexByType(int typeId) {
        int index = 0;
        for (int i = 0; i < mTypeInfoList.size(); i++) {
            GoodsTypeInfo goodsTypeInfo = mTypeInfoList.get(i);
            if (goodsTypeInfo.getId() == typeId) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int setIndexByTypeId(int typeId) {
        int index = 0;
        for (int i = 0; i < mGoodsInfosList.size(); i++) {
            GoodsInfo goodsInfo = mGoodsInfosList.get(i);
            if (goodsInfo.getTypeId() == typeId) {
                index = i;
                break;
            }
        }
        return index;
    }
}
