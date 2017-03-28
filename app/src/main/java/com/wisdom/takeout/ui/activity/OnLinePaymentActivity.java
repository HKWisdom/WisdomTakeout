package com.wisdom.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.GoodsInfo;
import com.wisdom.takeout.module.bean.Seller;
import com.wisdom.takeout.utils.PriceFormater;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/28.
 */
public class OnLinePaymentActivity extends AppCompatActivity {

    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_residualTime)
    TextView mTvResidualTime;
    @BindView(R.id.tv_order_name)
    TextView mTvOrderName;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.tv_order_detail)
    TextView mTvOrderDetail;
    @BindView(R.id.iv_triangle)
    ImageView mIvTriangle;
    @BindView(R.id.ll_order_toggle)
    RelativeLayout mLlOrderToggle;
    @BindView(R.id.tv_receipt_connect_info)
    TextView mTvReceiptConnectInfo;
    @BindView(R.id.tv_receipt_address_info)
    TextView mTvReceiptAddressInfo;
    @BindView(R.id.ll_goods)
    LinearLayout mLlGoods;
    @BindView(R.id.ll_order_detail)
    LinearLayout mLlOrderDetail;
    @BindView(R.id.tv_pay_money)
    TextView mTvPayMoney;
    @BindView(R.id.iv_pay_alipay)
    ImageView mIvPayAlipay;
    @BindView(R.id.cb_pay_alipay)
    CheckBox mCbPayAlipay;
    @BindView(R.id.tv_selector_other_payment)
    TextView mTvSelectorOtherPayment;
    @BindView(R.id.ll_hint_info)
    LinearLayout mLlHintInfo;
    @BindView(R.id.iv_pay_wechat)
    ImageView mIvPayWechat;
    @BindView(R.id.cb_pay_wechat)
    CheckBox mCbPayWechat;
    @BindView(R.id.iv_pay_qq)
    ImageView mIvPayQq;
    @BindView(R.id.cb_pay_qq)
    CheckBox mCbPayQq;
    @BindView(R.id.iv_pay_fenqile)
    ImageView mIvPayFenqile;
    @BindView(R.id.cb_pay_fenqile)
    CheckBox mCbPayFenqile;
    @BindView(R.id.ll_other_payment)
    LinearLayout mLlOtherPayment;
    @BindView(R.id.bt_confirm_pay)
    Button mBtConfirmPay;
    private String mCountPrice;
    private List<GoodsInfo> mShopCar;
    private Seller mSeller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        ButterKnife.bind(this);
        initIntent();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mSeller = (Seller) intent.getSerializableExtra("seller");
            mShopCar = (List<GoodsInfo>) intent.getSerializableExtra("cartList");
            mCountPrice = intent.getStringExtra("countPrice");

            mTvOrderName.setText("来自" + mSeller.getName() + "订单");
            mTvOrderDetail.setText("您一共订了" + mShopCar.size() + "件快餐");

            mTvPayMoney.setText(PriceFormater.format(0.01f));

            for (GoodsInfo goodsInfo : mShopCar) {
                TextView tv = new TextView(this);
                tv.setText(goodsInfo.getName() + "  ￥" + goodsInfo.getNewPrice() + " * " + goodsInfo.getCount());
                mLlGoods.addView(tv);
            }
        }
    }

    @OnClick({R.id.ib_back, R.id.iv_triangle, R.id.bt_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.iv_triangle:
                //展开订单
                showOrderDetail();
                break;
            case R.id.bt_confirm_pay:
                //付钱

                break;
        }
    }

    private void showOrderDetail() {
        //显示抽屉view
        if(mLlOrderDetail.getVisibility() == View.GONE){
            mLlOrderDetail.setVisibility(View.VISIBLE);
        }else{
            mLlOrderDetail.setVisibility(View.GONE);
        }
    }
}
