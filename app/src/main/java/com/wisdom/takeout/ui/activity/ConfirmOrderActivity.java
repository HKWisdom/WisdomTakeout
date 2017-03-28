package com.wisdom.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.GoodsInfo;
import com.wisdom.takeout.module.bean.Seller;
import com.wisdom.takeout.utils.PriceFormater;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/28.
 */
public class ConfirmOrderActivity extends AppCompatActivity {
    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.iv_location)
    ImageView mIvLocation;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_label)
    TextView mTvLabel;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.rl_location)
    RelativeLayout mRlLocation;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_seller_name)
    TextView mTvSellerName;
    @BindView(R.id.ll_select_goods)
    LinearLayout mLlSelectGoods;
    @BindView(R.id.tv_deliveryFee)
    TextView mTvDeliveryFee;
    @BindView(R.id.tv_CountPrice)
    TextView mTvCountPrice;
    @BindView(R.id.tvSubmit)
    TextView mTvSubmit;
    private Seller mSeller;
    private List<GoodsInfo> mShopCar;
    private float mPriceCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initIntent();

    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mSeller = (Seller) intent.getSerializableExtra("seller");
            mShopCar = (List<GoodsInfo>) intent.getSerializableExtra("shopCar");
            mTvSellerName.setText(mSeller.getName());
            mTvDeliveryFee.setText(PriceFormater.format(Float.parseFloat(mSeller.getDeliveryFee())));

            mPriceCount = Float.parseFloat(mSeller.getDeliveryFee());
            for (GoodsInfo goodsInfo : mShopCar) {
                mPriceCount += goodsInfo.getCount() * goodsInfo.getNewPrice();
            }
            mTvCountPrice.setText(PriceFormater.format(mPriceCount));
        }
    }

    @OnClick({R.id.ib_back, R.id.rl_location, R.id.tvSubmit})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.rl_location:
                intent = new Intent(this,RecepitAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.tvSubmit:
                intent = new Intent(this,OnLinePaymentActivity.class);
                intent.putExtra("seller", mSeller);
                intent.putExtra("cartList", (Serializable) mShopCar);
                intent.putExtra("countPrice", mPriceCount);
                startActivity(intent);
                break;
        }

    }
}
