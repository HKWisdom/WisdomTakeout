package com.wisdom.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.GoodsInfo;
import com.wisdom.takeout.module.bean.Seller;
import com.wisdom.takeout.utils.PayResult;
import com.wisdom.takeout.utils.PriceFormater;
import com.wisdom.takeout.utils.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/28.
 */
public class OnLinePaymentActivity extends AppCompatActivity {

    private static final int SDK_PAY_FLAG = 1;
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

    /**商户PID*/
    public static final String PARTNER = "2088221626451032";

    /**商户收款账号*/
    public static final String SELLER = "3393900637@qq.com";

    /**商户私钥(PKCS8格式编码)*/
    public static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMStleK6i5AOGkd5NPUYFlkZJuk56aLhWR8U1ataEGWALOA3d8n3XaIO9tQEzJJUVJIKbCDIR9D1zPS9LPchloqb0OHk9y9I4SJss1ZQZayDWW179ojpOzTqhmto7d566+mebddjRcNiUwD2bDGmcWXigCyAvncHKaW4nuO9iuVfAgMBAAECgYEAu/48PasXycthHR5jGz855VJgWh/sDa+e01HD5vTApXR98Je0XY2fp07sab5omBoZeDqUHkWyN68riGfmuhYV4JyWppub5iayzxHS2N5761PmB+2RxxD6bF7k+cy1qG84sZw0t3qUaChCJiizK5LAd9uNSv50n9Dr7MwHz3OgHcECQQDgofT1SdwQ3WJdXUIkeYhe0LaXbO3hiT5LmfO685TQMov2efYgBjfa22VvFxJd1tei2ml6mYWIx+1zAfe+xwu/AkEA4CRS7xH1tbEJknLVbpWwWjHCaA0bd155y/9/Bkvm0taOtm4pdddm5Mrz+w+dh1mYivWy01c0NLy9I6mmWUUOYQJAWOzEZDYRADwjrII2pOnXqnFFVzywDxCdsKAJdIDo8GKSNciiPps3kVQ5G3kutCdQxg9gokAUNMmwnk6xHLz/UQJBALEXgf8prXz0d5+h40gQNNnOXs9fK8hQeOLY5z/OUH1c0D0LJO7aVY2HXOWMHOaHv6JrJfMc/z57sOSwcaIukYECQQCUhwTfT5hjn1F1yzvCb/l9HugT3qPlgtHCWAtWiPzFS3nbTFmB0szkoW0/ao9uddsMI5MMGN88xy34aN1FPhi5";

    /**支付宝公钥*/
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";


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
                pay();
                break;
        }
    }

    public void pay() {
        // 订单
        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OnLinePaymentActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OnLinePaymentActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OnLinePaymentActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OnLinePaymentActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, PRIVATE_KEY);
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
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
