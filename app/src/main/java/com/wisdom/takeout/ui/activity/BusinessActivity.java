package com.wisdom.takeout.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.GoodsInfo;
import com.wisdom.takeout.module.bean.Seller;
import com.wisdom.takeout.ui.adapter.BusinessPageAdapter;
import com.wisdom.takeout.ui.fragment.CommentsFragment;
import com.wisdom.takeout.ui.fragment.GoodsInfoFragment;
import com.wisdom.takeout.ui.fragment.SellerFragment;
import com.wisdom.takeout.utils.PriceFormater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/25.
 */
public class BusinessActivity extends BaseActivity {
    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ib_menu)
    ImageButton mIbMenu;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.bottomSheetLayout)
    BottomSheetLayout mBottomSheetLayout;
    @BindView(R.id.imgCart)
    ImageView mImgCart;
    @BindView(R.id.tvSelectNum)
    TextView mTvSelectNum;
    @BindView(R.id.tvCountPrice)
    TextView mTvCountPrice;
    @BindView(R.id.tvSendPrice)
    TextView mTvSendPrice;
    @BindView(R.id.tvDeliveryFee)
    TextView mTvDeliveryFee;
    @BindView(R.id.tvSubmit)
    TextView mTvSubmit;
    @BindView(R.id.bottom)
    LinearLayout mBottom;
    private BusinessPageAdapter mAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    public Seller mSeller;

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_business, null);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        processIntent();
        initFragment();
        mAdapter = new BusinessPageAdapter(getSupportFragmentManager());
        mAdapter.setFragmentList(mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick({R.id.ib_back, R.id.tvSubmit,R.id.bottom})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_back://返回
                finish();
                break;
            case R.id.tvSubmit:
                break;
            case R.id.bottom://弹出购物车

                break;
            default:
                break;
        }
    }

    private void processIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mSeller = (Seller) intent.getSerializableExtra("seller");
        }
    }

    private void initFragment() {
        mFragmentList.add(new GoodsInfoFragment());
        mFragmentList.add(new CommentsFragment());
        mFragmentList.add(new SellerFragment());
    }

    //刷新购物车的ui
    public void updateShopCarUi(List<GoodsInfo> goodsInfo) {
        int count = 0;
        int countPrice = 0;
        for (GoodsInfo info : goodsInfo) {
            count += info.getCount();
            countPrice += info.getCount() * info.getNewPrice();
        }
        if(count>0){
            mTvSelectNum.setVisibility(View.VISIBLE);
        }else{
            mTvSelectNum.setVisibility(View.GONE);
        }
        mTvSelectNum.setText(count + "");
        mTvCountPrice.setText(PriceFormater.format(countPrice));
    }
}
