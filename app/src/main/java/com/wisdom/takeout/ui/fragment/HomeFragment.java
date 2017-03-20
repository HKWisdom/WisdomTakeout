package com.wisdom.takeout.ui.fragment;

import android.animation.ArgbEvaluator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.presenter.HomePresenter;
import com.wisdom.takeout.ui.adapter.HomeAdapter;

import butterknife.BindView;

/**
 * Created by HKWisdom on 2017/3/19.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    public RecyclerView mRvHome;
    @BindView(R.id.home_tv_address)
    TextView mHomeTvAddress;
    @BindView(R.id.ll_title_search)
    LinearLayout mLlTitleSearch;
    @BindView(R.id.ll_title_container)
    LinearLayout mLlTitleContainer;
    public HomeAdapter mAdapter;
    private int sumY = 0;
    private float distance = 200f;
    ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();
    int startColor = 0x553190E8;
    int endColor = 0xff3190E8;
    int bgColor;
    private HomePresenter mHomePresenter;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        mHomePresenter = new HomePresenter(this);
        mAdapter = new HomeAdapter(mContext);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
       mHomePresenter.loadData();

    }

    @Override
    protected void initListener() {
        super.initListener();
        mRvHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvHome.setAdapter(mAdapter);
        mRvHome.setOnScrollListener(mOnScrollListener);
    }

    /**
     * RecyclerView的滚动监听
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //当前滚动的距离
            sumY += dy;
            if (sumY <= 0) {
                bgColor = startColor;
            } else if (sumY > distance) {
                bgColor = endColor;
            } else {
                bgColor = (int) mArgbEvaluator.evaluate(sumY / distance, startColor, endColor);
            }
            mLlTitleContainer.setBackgroundColor(bgColor);
        }
    };
}
