package com.wisdom.takeout.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wisdom.takeout.R;
import com.wisdom.takeout.app.TakeoutApp;
import com.wisdom.takeout.presenter.OrderPresenter;
import com.wisdom.takeout.ui.adapter.OrderRvAdapter;

import butterknife.BindView;

/**
 * Created by HKWisdom on 2017/3/19.
 */
public class OrderFragment extends BaseFragment {

    @BindView(R.id.rv_order_list)
    public RecyclerView mRvOrderList;
    @BindView(R.id.srl_order)
    public SwipeRefreshLayout mSrlOrder;
    public OrderRvAdapter mAdapter;
    private OrderPresenter mOrderPresenter;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_order, null);
    }

    @Override
    protected void init() {
        super.init();
        mOrderPresenter = new OrderPresenter(this);
        mAdapter = new OrderRvAdapter(mContext);

    }

    @Override
    protected void initData() {
        super.initData();
        int mId = TakeoutApp.sUser.getId();
        if (mId == -1) {
            Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }else {
            mOrderPresenter.getOrderGoodInfo(mId);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRvOrderList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRvOrderList.setAdapter(mAdapter);
        mSrlOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int mId = TakeoutApp.sUser.getId();
                if (mId == -1) {
                    Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    mOrderPresenter.getOrderGoodInfo(mId);
                }
            }
        });
    }
}
