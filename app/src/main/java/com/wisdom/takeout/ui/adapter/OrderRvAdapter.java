package com.wisdom.takeout.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.Order;
import com.wisdom.takeout.utils.OrderObservable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/3/24.
 */

public class OrderRvAdapter extends RecyclerView.Adapter implements Observer {

    private List<Order> mOrderList = new ArrayList<>();
    private Context mContext;
    private int mPosition = -1;

    public OrderRvAdapter(Context context) {
        mContext = context;
        OrderObservable.getInstance().addObserver(this);
    }

    @Override
    public void update(Observable o, Object data) {
        HashMap<String,String> map = (HashMap<String, String>) data;
        String orderId = map.get("orderId");
        String type = map.get("type");
        for (int i = 0; i < mOrderList.size(); i++) {
            Order order = mOrderList.get(i);
            if (order.id.equals(orderId)) {
                order.type = type;
                mPosition = i;
                break;
            }
        }

        if (mPosition != -1) {
            notifyItemChanged(mPosition);
        }
    }

    public void setOrderList(List<Order> orderList) {
        mOrderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = View.inflate(mContext, R.layout.item_order_item, null);
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_order_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bindView(mOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mOrderList != null) {
            return mOrderList.size();
        }
        return 0;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_order_item_seller_logo)
        ImageView mIvOrderItemSellerLogo;
        @BindView(R.id.tv_order_item_seller_name)
        TextView mTvOrderItemSellerName;
        @BindView(R.id.tv_order_item_type)
        TextView mTvOrderItemType;
        @BindView(R.id.tv_order_item_time)
        TextView mTvOrderItemTime;
        @BindView(R.id.tv_order_item_foods)
        TextView mTvOrderItemFoods;
        @BindView(R.id.tv_order_item_money)
        TextView mTvOrderItemMoney;
        @BindView(R.id.tv_order_item_multi_function)
        TextView mTvOrderItemMultiFunction;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Order order) {
            mTvOrderItemSellerName.setText(order.seller.getName());
            mTvOrderItemType.setText(getOrderTypeInfo(order.type));

        }

        private String getOrderTypeInfo(String type) {
            /**
             * 订单状态
             * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单
             */
            //            public static final String ORDERTYPE_UNPAYMENT = "10";
            //            public static final String ORDERTYPE_SUBMIT = "20";
            //            public static final String ORDERTYPE_RECEIVEORDER = "30";
            //            public static final String ORDERTYPE_DISTRIBUTION = "40";
            //            public static final String ORDERTYPE_SERVED = "50";
            //            public static final String ORDERTYPE_CANCELLEDORDER = "60";

            String typeInfo = "";
            switch (type) {
                case OrderObservable.ORDERTYPE_UNPAYMENT:
                    typeInfo = "未支付";
                    break;
                case OrderObservable.ORDERTYPE_SUBMIT:
                    typeInfo = "已提交订单";
                    break;
                case OrderObservable.ORDERTYPE_RECEIVEORDER:
                    typeInfo = "商家接单";
                    break;
                case OrderObservable.ORDERTYPE_DISTRIBUTION:
                    typeInfo = "配送中";
                    break;
                case OrderObservable.ORDERTYPE_SERVED:
                    typeInfo = "已送达";
                    break;
                case OrderObservable.ORDERTYPE_CANCELLEDORDER:
                    typeInfo = "取消的订单";
                    break;
            }
            return typeInfo;
        }
    }

}
