package com.wisdom.takeout.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/19.
 */
public class UserFragment extends BaseFragment {


    @BindView(R.id.tv_user_setting)
    ImageView mTvUserSetting;
    @BindView(R.id.iv_user_notice)
    ImageView mIvUserNotice;
    @BindView(R.id.login)
    ImageView mLogin;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;
    @BindView(R.id.iv_address_manager)
    ImageView mIvAddressManager;

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_user, null);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();

    }
    @OnClick(R.id.login)
    public void onClick() {
        Intent intent = new Intent(mContext,LoginActivity.class);
        startActivity(intent);
    }
}
