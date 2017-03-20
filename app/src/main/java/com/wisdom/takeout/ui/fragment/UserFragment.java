package com.wisdom.takeout.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdom.takeout.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/19.
 */
public class UserFragment extends BaseFragment {

    @BindView(R.id.iv_user_back)
    ImageView mIvUserBack;
    @BindView(R.id.iv_user_password_login)
    TextView mIvUserPasswordLogin;
    @BindView(R.id.et_user_phone)
    EditText mEtUserPhone;
    @BindView(R.id.tv_user_code)
    TextView mTvUserCode;
    @BindView(R.id.et_user_code)
    EditText mEtUserCode;
    @BindView(R.id.login)
    TextView mLogin;

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.activity_login, null);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
    }


    @OnClick({R.id.tv_user_code, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_code:
                String userCode = mEtUserCode.getText().toString().trim();
                if (TextUtils.isEmpty(userCode)) {
                    Toast.makeText(mContext, "内容为空", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    //TODO:获取验证码
                }

                break;

            case R.id.login:
                String loginCode = mEtUserCode.getText().toString().trim();
                if (TextUtils.isEmpty(loginCode)) {
                    Toast.makeText(mContext, "内容为空", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    //TODO:登录
                }
                break;
        }
    }

}
