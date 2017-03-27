package com.wisdom.takeout.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.Seller;
import com.wisdom.takeout.presenter.GoodInfoPresenter;
import com.wisdom.takeout.ui.activity.BusinessActivity;
import com.wisdom.takeout.ui.adapter.GoodTypeRvAdapter;
import com.wisdom.takeout.ui.adapter.GoodsAdapter;
import com.wisdom.takeout.ui.adapter.MyGoodsAdapter;

import butterknife.BindView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by HKWisdom on 2017/3/25.
 */
public class GoodsInfoFragment extends BaseFragment {
    @BindView(R.id.rv_goods_type)
    RecyclerView mRvGoodsType;
    @BindView(R.id.slhlv)
    public StickyListHeadersListView mSlhlv;
    public GoodTypeRvAdapter mAdapter;
    public GoodInfoPresenter mPresenter;
    public MyGoodsAdapter mGoodsAdapter;
    public GoodsAdapter mGoodsAdapter1;

    @Override
    protected View initView() {
        mAdapter = new GoodTypeRvAdapter(mContext,this);
        mPresenter = new GoodInfoPresenter(this);
        return View.inflate(mContext, R.layout.fragment_goods, null);
    }

    @Override
    protected void initData() {
        super.initData();
        //请求数据
        Seller seller = ((BusinessActivity) getActivity()).mSeller;
        mPresenter.getGoodInfoData((int) seller.getId());

    }

    @Override
    protected void initListener() {
        super.initListener();

        mRvGoodsType.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mRvGoodsType.setAdapter(mAdapter);
        mGoodsAdapter = new MyGoodsAdapter(mContext);
        mGoodsAdapter1 = new GoodsAdapter(mContext,this);
        mSlhlv.setAdapter(mGoodsAdapter1);
    }
}
