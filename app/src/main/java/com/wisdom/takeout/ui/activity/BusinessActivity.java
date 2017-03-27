package com.wisdom.takeout.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.GoodsInfo;
import com.wisdom.takeout.module.bean.GoodsTypeInfo;
import com.wisdom.takeout.module.bean.Seller;
import com.wisdom.takeout.ui.adapter.BusinessPageAdapter;
import com.wisdom.takeout.ui.adapter.CartRvAdapter;
import com.wisdom.takeout.ui.fragment.CommentsFragment;
import com.wisdom.takeout.ui.fragment.GoodsInfoFragment;
import com.wisdom.takeout.ui.fragment.SellerFragment;
import com.wisdom.takeout.utils.PriceFormater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wisdom.takeout.R.id.rvCart;

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
    public List<Fragment> mFragmentList = new ArrayList<>();
    public Seller mSeller;
    private View mBottomSheetView;
    private RecyclerView mRvCart;
    private CartRvAdapter mCartRvAdapter;

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

    @OnClick({R.id.ib_back, R.id.tvSubmit, R.id.bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back://返回
                finish();
                break;
            case R.id.tvSubmit:
                break;
            case R.id.bottom://弹出购物车
                showOrDissmissCart();
                break;
            default:
                break;
        }
    }

    public void showOrDissmissCart() {
        if (mBottomSheetView == null) {
            //加载要显示的布局
            mBottomSheetView = LayoutInflater.from(this).inflate(R.layout.cart_list, (ViewGroup) getWindow().getDecorView(), false);
            mRvCart = (RecyclerView) mBottomSheetView.findViewById(rvCart);
            mCartRvAdapter = new CartRvAdapter(this);
            mRvCart.setLayoutManager(new LinearLayoutManager(this));
            mRvCart.setAdapter(mCartRvAdapter);

            TextView tvClear = (TextView) mBottomSheetView.findViewById(R.id.tvClear);
            tvClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出dialog
                    showPromptDialog();
                }
            });
        }


        //判断BottomSheetLayout内容是否显示
        if (mBottomSheetLayout.isSheetShowing()) {
            //关闭内容显示
            mBottomSheetLayout.dismissSheet();
        } else {
            //显示BottomSheetLayout里面的内容
            //得到购物车的商品
            List<GoodsInfo> shopCarList = ((GoodsInfoFragment) mFragmentList.get(0)).mPresenter.getShopCarList();
            mCartRvAdapter.setGoodsInfoList(shopCarList);
            if (shopCarList.size() > 0) {
                mBottomSheetLayout.showWithSheetView(mBottomSheetView);
            }
        }
    }

    private void showPromptDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("确定清空购物车么");
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearShopClear();
            }
        });

        dialog.show();
    }

    /**
     * 清空购物车
     */
    private void clearShopClear() {
        //1:清空购物车自己
        GoodsInfoFragment goodsInfoFragment = (GoodsInfoFragment) mFragmentList.get(0);
        goodsInfoFragment.mPresenter.clearShopCarList();
        mCartRvAdapter.notifyDataSetChanged();
        //购物车消失
        showOrDissmissCart();

        //2:清空底部
        List<GoodsInfo> shopCarList = goodsInfoFragment.mPresenter.getShopCarList();
        updateShopCarUi(shopCarList);

        //3:清空左侧
        processRedDot(goodsInfoFragment);

        //4:清空右侧
        goodsInfoFragment.mGoodsAdapter1.notifyDataSetChanged();
    }

    private void processRedDot(GoodsInfoFragment goodsInfoFragment) {
        List<GoodsTypeInfo> typeInfoList = goodsInfoFragment.mPresenter.getTypeInfoList();
        for (GoodsTypeInfo goodsTypeInfo : typeInfoList) {
            goodsTypeInfo.setRedDotCount(0);
        }
        goodsInfoFragment.mAdapter.notifyDataSetChanged();
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
        if (count > 0) {
            mTvSelectNum.setVisibility(View.VISIBLE);
        } else {
            mTvSelectNum.setVisibility(View.GONE);
        }
        mTvSelectNum.setText(count + "");
        mTvCountPrice.setText(PriceFormater.format(countPrice));
    }
}
