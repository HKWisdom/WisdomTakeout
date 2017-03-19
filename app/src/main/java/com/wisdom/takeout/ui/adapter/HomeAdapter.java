package com.wisdom.takeout.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisdom.takeout.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/3/19.
 */
public class HomeAdapter extends RecyclerView.Adapter {

    public List<String> mData;
    public Context mContext;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_home_common, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bindView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindView(String s) {
            mTv.setText(s);
        }
    }
}
