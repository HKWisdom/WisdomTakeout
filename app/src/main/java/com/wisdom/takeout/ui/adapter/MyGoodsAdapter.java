package com.wisdom.takeout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by HKWisdom on 2017/3/25.
 */

public class MyGoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context mContext;
    private List<GoodsInfo> mGoodsInfoList = new ArrayList<>();

    public MyGoodsAdapter(Context context) {
        mContext = context;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_header, parent, false);
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);
        String typeName = goodsInfo.getTypeName();
        ((TextView) view).setText(typeName);
        return view;
    }

    @Override
    public long getHeaderId(int position) {
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);
        int id = goodsInfo.getId();
        return id;
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
        private AnimationSet mShowAnimation;
        private GoodsInfo mGoodsInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }





        public void bindView(GoodsInfo goodsInfo) {
            this.mGoodsInfo = goodsInfo;

        }




    }
}
