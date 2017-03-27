package com.wisdom.takeout.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.GoodsInfo;
import com.wisdom.takeout.module.bean.GoodsTypeInfo;
import com.wisdom.takeout.ui.activity.BusinessActivity;
import com.wisdom.takeout.ui.fragment.GoodsInfoFragment;
import com.wisdom.takeout.utils.PriceFormater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/27.
 */
public class CartRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GoodsInfo> mGoodsInfoList = new ArrayList<>();

    public CartRvAdapter(Context context) {
        mContext = context;
    }

    public List<GoodsInfo> getGoodsInfoList() {
        return mGoodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bindView(mGoodsInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mGoodsInfoList != null) {
            return mGoodsInfoList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_type_all_price)
        TextView mTvTypeAllPrice;
        @BindView(R.id.ib_minus)
        ImageButton mIbMinus;
        @BindView(R.id.tv_count)
        TextView mTvCount;
        @BindView(R.id.ib_add)
        ImageButton mIbAdd;
        @BindView(R.id.ll)
        LinearLayout mLl;
        private GoodsInfo mGoodsInfo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.ib_add, R.id.ib_minus})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ib_add:

                    doAddOperation();

                    break;
                case R.id.ib_minus:

                    doMinusOperation();

                    break;
                default:
                    break;
            }
        }

        private void doAddOperation() {
            //1:改变自身的count
            int count = mGoodsInfo.getCount();
            count++;
            mGoodsInfo.setCount(count);
            notifyDataSetChanged();

            //2:改变底部的count
            ((BusinessActivity) mContext).updateShopCarUi(mGoodsInfoList);

            //3:改变左侧的count
            processRedDot(true);

            //4:改变右侧的count
            GoodsInfoFragment goodsInfoFragment = (GoodsInfoFragment) ((BusinessActivity) mContext).mFragmentList.get(0);
            goodsInfoFragment.mGoodsAdapter1.notifyDataSetChanged();
        }


        private void doMinusOperation() {
            //1:改变自身的count
            int count = mGoodsInfo.getCount();
                count--;
                mGoodsInfo.setCount(count);
            if (count == 0) {
                mGoodsInfoList.remove(mGoodsInfo);
                if (mGoodsInfoList.size() == 0) {
                    ((BusinessActivity) mContext).showOrDissmissCart();
                }
            }
                notifyDataSetChanged();



            //2:改变底部的count
            ((BusinessActivity) mContext).updateShopCarUi(mGoodsInfoList);

            //3:改变左侧的count
            processRedDot(false);

            //4:改变右侧的count
            GoodsInfoFragment goodsInfoFragment = (GoodsInfoFragment) ((BusinessActivity) mContext).mFragmentList.get(0);
            goodsInfoFragment.mGoodsAdapter1.notifyDataSetChanged();

        }


        private void processRedDot(boolean isAdd) {
            int typeId = mGoodsInfo.getTypeId();
            GoodsInfoFragment goodsInfoFragment = (GoodsInfoFragment) ((BusinessActivity) mContext).mFragmentList.get(0);
            int typeIndexByType = goodsInfoFragment.mPresenter.getTypeIndexByType(typeId);
            GoodsTypeInfo goodsTypeInfo = goodsInfoFragment.mAdapter.getInfoList().get(typeIndexByType);
            int redDotCount = goodsTypeInfo.getRedDotCount();
            if (isAdd) {
                redDotCount++;
            } else {
                redDotCount--;
            }
            goodsTypeInfo.setRedDotCount(redDotCount);
            goodsInfoFragment.mAdapter.notifyDataSetChanged();
        }

        public void bindView(GoodsInfo goodsInfo) {
            this.mGoodsInfo = goodsInfo;
            mTvName.setText(goodsInfo.getName());
            float allPrice = goodsInfo.getCount() * goodsInfo.getNewPrice();
            mTvTypeAllPrice.setText(PriceFormater.format(allPrice));
            mTvCount.setText(goodsInfo.getCount() + "");
        }
    }
}
