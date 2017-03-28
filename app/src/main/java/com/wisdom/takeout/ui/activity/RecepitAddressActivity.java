package com.wisdom.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.RecepitAddress;
import com.wisdom.takeout.module.dao.AddressDao;
import com.wisdom.takeout.ui.adapter.AddressRvAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/3/23.
 */

public class RecepitAddressActivity extends AppCompatActivity {

    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_receipt_address)
    RecyclerView mRvReceiptAddress;
    @BindView(R.id.tv_add_address)
    TextView mTvAddAddress;
    private AddressDao mAddressDao;
    private AddressRvAdapter mAddressRvAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_address);
        ButterKnife.bind(this);
        mAddressDao = new AddressDao(this);
        mAddressRvAdapter = new AddressRvAdapter(this);
        mRvReceiptAddress.setLayoutManager(new LinearLayoutManager(this));
        mRvReceiptAddress.setAdapter(mAddressRvAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<RecepitAddress> recepitAddresses = mAddressDao.queryAllAddress();
        if (recepitAddresses != null) {
            mAddressRvAdapter.setAddressList(recepitAddresses);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.ib_back, R.id.tv_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(this, AddOrEditAddressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
