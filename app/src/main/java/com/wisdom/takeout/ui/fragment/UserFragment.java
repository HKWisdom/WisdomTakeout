package com.wisdom.takeout.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdom.takeout.R;
import com.wisdom.takeout.app.TakeoutApp;
import com.wisdom.takeout.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wisdom.takeout.R.id.ll_userinfo;

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
    @BindView(ll_userinfo)
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

    @Override
    public void onResume() {
        super.onResume();
        int id = TakeoutApp.sUser.getId();
        if (id == -1) {
            //用户未登录
            mLogin.setVisibility(View.VISIBLE);
            mLlUserinfo.setVisibility(View.INVISIBLE);
        }else {
            mLogin.setVisibility(View.GONE);
            mLlUserinfo.setVisibility(View.VISIBLE);
            mUsername.setText("欢迎您," + TakeoutApp.sUser.getName());
            mPhone.setText(TakeoutApp.sUser.getPhone());
        }

    }

    @OnClick(R.id.login)
    public void onClick() {
        Intent intent = new Intent(mContext,LoginActivity.class);
        startActivity(intent);
    }
}
