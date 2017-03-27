package com.wisdom.takeout.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by lidongzhi on 2017/3/22.
 */

public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private GoodsInfoFragment mGoodsFragment;
    private Context mContext;
    private List<GoodsInfo> mGoodsInfoList = new ArrayList<>();

    public GoodsAdapter(Context context, GoodsInfoFragment goodsInfoFragment) {
        mContext = context;
        this.mGoodsFragment = goodsInfoFragment;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.item_type_header, parent, false);
        GoodsInfo goodsInfo = mGoodsInfoList.get(position); //可乐
        String typeName = goodsInfo.getTypeName(); //饮料
        ((TextView) headerView).setText(typeName);
        //TODO:typeName为空，typeId为0,需要手动赋值
        return headerView;
    }

    @Override
    public long getHeaderId(int position) {
        GoodsInfo goodsInfo = mGoodsInfoList.get(position); //可乐
        int typeId = goodsInfo.getTypeId();
        return typeId;
    }

    @Override
    public int getCount() {
        if (mGoodsInfoList != null) {
            return mGoodsInfoList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mGoodsInfoList != null) {
            return mGoodsInfoList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bindView(mGoodsInfoList.get(position));
        return convertView;
    }

    class ViewHolder {
        private static final long SHOW_DURATION = 1000;
        @BindView(R.id.iv_icon)
        ImageView mIvIcon;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_zucheng)
        TextView mTvZucheng;
        @BindView(R.id.tv_yueshaoshounum)
        TextView mTvYueshaoshounum;
        @BindView(R.id.tv_newprice)
        TextView mTvNewprice;
        @BindView(R.id.tv_oldprice)
        TextView mTvOldprice;
        @BindView(R.id.ib_minus)
        ImageButton mIbMinus;
        @BindView(R.id.tv_count)
        TextView mTvCount;
        @BindView(R.id.ib_add)
        ImageButton mIbAdd;
        private GoodsInfo mGoodsInfo;

        @OnClick({R.id.ib_add, R.id.ib_minus})
        public void OnClick(View view) {

            boolean isAdd = false;

            switch (view.getId()) {
                case R.id.ib_add:
                    doAddOperation();

                    isAdd = true;

                    break;
                case R.id.ib_minus:
                    doMinusOperation();
                    break;
            }

            //左侧红点处理
            processRedDot(isAdd);

            //处理购物车的红点
            processRedCar();
        }

        private void processRedCar() {
            //找出那些商品是购物车的
            List<GoodsInfo> shopCarList = mGoodsFragment.mPresenter.getShopCarList();
                //遍历购物车,把所有的count累加
            ((BusinessActivity) mGoodsFragment.getActivity()).updateShopCarUi(shopCarList);
        }

        private void processRedDot(boolean isAdd) {
            //先得到左侧列表,再拿到对应的类别,找到红点的控件
            int typeId = mGoodsInfo.getTypeId();//得到对应的左侧的typeId
            int indexByType = mGoodsFragment.mPresenter.getTypeIndexByType(typeId);
            //得到对应的商品
            GoodsTypeInfo goodsTypeInfo = mGoodsFragment.mAdapter.getInfoList().get(indexByType);
            int redDotCount = goodsTypeInfo.getRedDotCount();
            if (isAdd) {
                redDotCount++;
            }else {
                redDotCount--;
            }
            goodsTypeInfo.setRedDotCount(redDotCount);

            //刷新左侧列表
            mGoodsFragment.mAdapter.notifyDataSetChanged();

        }

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        //减
        private void doMinusOperation() {
            //1.构建动画，显示出来-号以及数量
            AnimationSet animationSet = getHideAnimation();
            //2.改变数据count值
            int count = mGoodsInfo.getCount();
            if (count == 1) {
                //3.从有到无，让-号和mtvCount执行动画,显示view

                mTvCount.setVisibility(View.INVISIBLE);
                mIbMinus.setVisibility(View.INVISIBLE);
                mTvCount.startAnimation(animationSet);
                mIbMinus.startAnimation(animationSet);

            } else {
                //只改变count值

            }
            count--;
            mGoodsInfo.setCount(count);
            //4.刷新UI
            mTvCount.setText(count + "");
        }

        //加
        private void doAddOperation() {
            //1.构建动画，显示出来-号以及数量
            AnimationSet animationSet = getShowAnimation();
            //2.改变数据count值
            int count = mGoodsInfo.getCount();
            if (count == 0) {
                //3.从无到有，让-号和mtvCount执行动画,显示view

                mTvCount.setVisibility(View.VISIBLE);
                mIbMinus.setVisibility(View.VISIBLE);
                mTvCount.startAnimation(animationSet);
                mIbMinus.startAnimation(animationSet);
            } else {
                //只改变count值

            }
            count++;
            mGoodsInfo.setCount(count);
            //4.刷新UI
            mTvCount.setText(count + "");
        }

        public void bindView(GoodsInfo goodsInfo) {
            this.mGoodsInfo = goodsInfo;
            Picasso.with(mContext).load(goodsInfo.getIcon()).into(mIvIcon);
            mTvName.setText(goodsInfo.getName());
            mTvZucheng.setText(goodsInfo.getForm());
            mTvYueshaoshounum.setText("月售" + goodsInfo.getMonthSaleNum() + "份");
            mTvNewprice.setText("￥" + goodsInfo.getNewPrice());
            if (goodsInfo.getOldPrice() > 0) {
                mTvOldprice.setVisibility(View.VISIBLE);
            } else {
                mTvOldprice.setVisibility(View.GONE);
            }
            mTvOldprice.setText(PriceFormater.format(goodsInfo.getOldPrice()));
            mTvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            if (goodsInfo.getCount() > 0) {
                mTvCount.setVisibility(View.VISIBLE);
                mIbMinus.setVisibility(View.VISIBLE);

            } else {
                mTvCount.setVisibility(View.GONE);
                mIbMinus.setVisibility(View.INVISIBLE);
            }
            mTvCount.setText(goodsInfo.getCount() + "");
        }

        /**
         * 显示动画
         *
         * @return
         */
        private AnimationSet getShowAnimation() {
            AnimationSet animationSet = new AnimationSet(false);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(alphaAnimation);
            RotateAnimation rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(rotateAnimation);
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 2, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            translateAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(translateAnimation);
            return animationSet;
        }


        private AnimationSet getHideAnimation() {
            AnimationSet animationSet = new AnimationSet(false);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            alphaAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(alphaAnimation);
            RotateAnimation rotateAnimation = new RotateAnimation(720, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(rotateAnimation);
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 2, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            translateAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(translateAnimation);
            return animationSet;
        }
    }

}
