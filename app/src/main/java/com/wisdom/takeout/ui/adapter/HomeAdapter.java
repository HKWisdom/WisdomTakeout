package com.wisdom.takeout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.wisdom.takeout.R;
import com.wisdom.takeout.app.TakeoutApp;
import com.wisdom.takeout.module.bean.Seller;
import com.wisdom.takeout.ui.activity.BusinessActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/3/19.
 */
public class HomeAdapter extends RecyclerView.Adapter {

    public List<Seller> mNearByList;
    public List<Seller> mOtherList;
    public Context mContext;
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_DIVISION = 1;
    public static final int TYPE_SELLER = 2;
    private int GROUP_SIZE = 10;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Seller> nearByList, List<Seller> otherList) {
        mNearByList = nearByList;
        mOtherList = otherList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        // 头布局  （附近0 - 附近9）  分割线 （其他0-其他9） 分割线 （其他10-其他19） 分割线 （其他20-其他24）
        if (position == 0) {//头布局
            return TYPE_TITLE;
        }
        //        else if (position == (mNearByList.size() + 1)) {
        //            return TYPE_DIVISION;
        //        }

        else if (position > mNearByList.size() && (position - 1 - mNearByList.size()) % (GROUP_SIZE + 1) == 0) {
            return TYPE_DIVISION;
        } else {
            return TYPE_SELLER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        //        View itemView = View.inflate(mContext, R.layout.item_home_common, null);
        switch (viewType) {
            case TYPE_TITLE:
                itemView = View.inflate(mContext, R.layout.item_title, null);
                ItemTitleViewHolder viewHolder = new ItemTitleViewHolder(itemView);
                return viewHolder;

            case TYPE_DIVISION:
                itemView = View.inflate(mContext, R.layout.item_division, null);
                DivisionViewHolder itemTitleViewHolder = new DivisionViewHolder(itemView);
                return itemTitleViewHolder;

            case TYPE_SELLER:
                itemView = View.inflate(mContext, R.layout.item_seller, null);
                SellerViewHolder itemTitleViewHolder1 = new SellerViewHolder(itemView);
                return itemTitleViewHolder1;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_TITLE:
                ItemTitleViewHolder viewHolder = (ItemTitleViewHolder) holder;
                viewHolder.bindView("title" + position);
                break;

            case TYPE_DIVISION:
                DivisionViewHolder divisionViewHolder = (DivisionViewHolder) holder;
                divisionViewHolder.bindView("----------分割线-----------");
                break;

            case TYPE_SELLER:
                SellerViewHolder sellerViewHolder = (SellerViewHolder) holder;
                //根据position区分商家
                int index;
                if (position < mNearByList.size() + 1) {
                    //都数据附近商家
                    index = position - 1;
                    sellerViewHolder.bindView(mNearByList.get(index));
                } else {
                    index = position - 1 - mNearByList.size() - 1;//去除头部  附近商家  第一个分割线
                    index -= index / (GROUP_SIZE + 1);
                    sellerViewHolder.bindView(mOtherList.get(index));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        // 头布局  （附近0 - 附近9）  分割线 （其他0-其他9） 分割线 （其他10-其他19） 分割线 （其他20-其他24）

        int count = 0;
        if (mNearByList == null && mOtherList == null) {
            return 0;
        }

        if (mNearByList.size() > 0) {
            count += 1;
            count += mNearByList.size();
            count += 1;
        } else {
            //只有头布局
            count += 1;
        }

        if (mOtherList.size() > 0) {
            count += mOtherList.size();
            //计算分割线
            count += mOtherList.size() / GROUP_SIZE;
            if (mOtherList.size() % GROUP_SIZE == 0) {
                count -= 1;
            }
        }
        return count;
    }

    class ItemTitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slider)
        com.daimajia.slider.library.SliderLayout mSlider;

        public ItemTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindView(String s) {
            testData(mContext);
        }

        private void testData(Context context) {
            HashMap<String, String> url_maps = new HashMap<String, String>();
            url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
            url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
            url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
            url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

            for (String desc : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(itemView.getContext());
                textSliderView.description(desc).image(url_maps.get(desc));
                mSlider.addSlider(textSliderView);
            }
        }
    }

    class DivisionViewHolder extends RecyclerView.ViewHolder {

        public DivisionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindView(String s) {

        }
    }

    class SellerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.seller_logo)
        ImageView mSellerLogo;
        @BindView(R.id.tvCount)
        TextView mTvCount;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.ratingBar)
        RatingBar mRatingBar;
        @BindView(R.id.tv_home_sale)
        TextView mTvHomeSale;
        @BindView(R.id.tv_home_send_price)
        TextView mTvHomeSendPrice;
        @BindView(R.id.tv_home_distance)
        TextView mTvHomeDistance;
        public Seller seller;

        public SellerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,BusinessActivity.class);
                    intent.putExtra("seller",seller);


                    //查找在整个店点了多少商品
                    int sellerId = TakeoutApp.sInstance.queryCacheSelectedInfoBySellerId((int) seller.getId());
                    boolean hasSelectInfo = false;//判断是否有选中的品
                    if (sellerId > 0)  {
                        hasSelectInfo = true;
                    }else {
                        //不用读缓存
                    }
                    intent.putExtra("hasSelectInfo" ,hasSelectInfo);
                    mContext.startActivity(intent);
                }
            });
        }


        public void bindView(Seller sellerBean) {

            this.seller = sellerBean;

            String iconUrl = sellerBean.getIcon();
            Log.d("url", "bindView: " + iconUrl);
            Picasso.with(mContext).load(iconUrl).into(mSellerLogo);
            mTvTitle.setText(sellerBean.getName());
            mRatingBar.setRating(Float.parseFloat(sellerBean.getScore()));
            mTvHomeDistance.setText(sellerBean.getDistance());
            mTvHomeSale.setText(sellerBean.getSale());
            mTvHomeSendPrice.setText(sellerBean.getSendPrice());
            mTvCount.setText(sellerBean.getScore());
        }
    }
}
