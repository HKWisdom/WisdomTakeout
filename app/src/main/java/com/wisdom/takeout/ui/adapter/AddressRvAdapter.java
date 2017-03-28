package com.wisdom.takeout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.RecepitAddress;
import com.wisdom.takeout.ui.activity.AddOrEditAddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/3/23.
 */

public class AddressRvAdapter extends RecyclerView.Adapter {



    private Context mContext;
    private List<RecepitAddress> mAddressList = new ArrayList<>();

    public AddressRvAdapter(Context context) {
        mContext = context;
    }

    public void setAddressList(List<RecepitAddress> addressList) {
        mAddressList = addressList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_receipt_address, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mAddressList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mAddressList != null) {
            return mAddressList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.iv_edit)
        ImageView mIvEdit;
        private RecepitAddress mAddress;

        @OnClick(R.id.iv_edit)
        public void onClick() {
            //点击修改地址
            Intent intent = new Intent(mContext, AddOrEditAddressActivity.class);
            intent.putExtra("address", mAddress);
            mContext.startActivity(intent);
        }

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(RecepitAddress address) {
            this.mAddress = address;
            mTvName.setText(address.getName());
            mTvSex.setText(address.getSex());
            mTvPhone.setText(address.getPhone() + "," + address.getPhoneOther());
            mTvAddress.setText(address.getAddress() + "," + address.getDetailAddress());
            mTvLabel.setText(address.getLabel());
            mTvLabel.setVisibility(View.VISIBLE);
            mTvLabel.setTextColor(Color.BLACK);
            mTvLabel.setBackgroundColor(Color.GRAY);
            mIvEdit.setVisibility(View.VISIBLE);
        }
    }
}
