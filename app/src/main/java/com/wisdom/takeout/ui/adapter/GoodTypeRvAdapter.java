package com.wisdom.takeout.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.GoodsTypeInfo;
import com.wisdom.takeout.ui.fragment.GoodsInfoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/3/25.
 */

public class GoodTypeRvAdapter extends RecyclerView.Adapter {

    private  GoodsInfoFragment goodsInfoFragment;
    private Context mContext;
    private List<GoodsTypeInfo> mInfoList = new ArrayList<>();

    public void setInfoList(List<GoodsTypeInfo> infoList) {
        mInfoList = infoList;
        notifyDataSetChanged();
    }

    public List<GoodsTypeInfo> getInfoList() {
        return mInfoList;
    }

    public GoodTypeRvAdapter(Context context, GoodsInfoFragment goodsInfoFragment) {
        this.mContext = context;
        this.goodsInfoFragment = goodsInfoFragment;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type, parent, false);
        GoodInfoViewHolder viewHolder = new GoodInfoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        GoodInfoViewHolder goodInfoViewHolder = (GoodInfoViewHolder) holder;
        goodInfoViewHolder.bindView(mInfoList.get(position),position);

    }

    @Override
    public int getItemCount() {
        if (mInfoList != null) {
            return mInfoList.size();
        }
        return 0;
    }
    public int selectPosition;
    class GoodInfoViewHolder extends RecyclerView.ViewHolder {
        private  View mItemView;
        @BindView(R.id.tvCount)
        TextView mTvCount;
        @BindView(R.id.type)
        TextView mType;
        private int mPosition;
        private GoodsTypeInfo goodsTypeInfo;


        public GoodInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mItemView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //定义一个临时变量(刷新作则UI)
                    selectPosition = mPosition;
                    notifyDataSetChanged();

                    int typeId = goodsTypeInfo.getId();
                    int index = goodsInfoFragment.mPresenter.setIndexByTypeId(typeId);
                    goodsInfoFragment.mSlhlv.setSelection(index);
                }
            });
        }

        public void bindView(GoodsTypeInfo goodsTypeInfo, int position) {
            this.goodsTypeInfo = goodsTypeInfo;
            if (selectPosition == position) {//当前选中
                //选中的条目，白底黑字
                mItemView.setBackgroundColor(Color.WHITE);
                mType.setTextColor(Color.BLACK);
                mType.setTypeface(Typeface.DEFAULT_BOLD); //加粗效果
            }else {
                //普通条目，灰底灰字
                mItemView.setBackgroundColor(Color.parseColor("#b9dedcdc"));
                mType.setTextColor(Color.GRAY);
                mType.setTypeface(Typeface.DEFAULT);
            }
            this.mPosition = position;
            mType.setText(goodsTypeInfo.getName());

            if(goodsTypeInfo.getRedDotCount()>0){
                mTvCount.setVisibility(View.VISIBLE);
            }else{
                mTvCount.setVisibility(View.GONE);
            }
            mTvCount.setText(goodsTypeInfo.getRedDotCount() + "");
        }
    }
}
