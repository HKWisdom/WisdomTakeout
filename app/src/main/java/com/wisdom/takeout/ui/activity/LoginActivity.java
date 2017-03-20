package com.wisdom.takeout.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdom.takeout.R;
import com.wisdom.takeout.utils.SMSUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by HKWisdom on 2017/3/20.
 */

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

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
    private String mPhone;

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_login, null);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        SMSSDK.initSDK(this, "1c428266f8ad6", "67765ccf155965d54def622ce222e682");
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.d(TAG, "afterEvent: " + "提交验证码成功");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Log.d(TAG, "afterEvent: " + "获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @OnClick({R.id.tv_user_code, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_code:
                mPhone = mEtUserPhone.getText().toString().trim();
                if (TextUtils.isEmpty(mPhone)) {
                    Toast.makeText(this, "内容为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //TODO:获取验证码
                    SMSSDK.getVerificationCode("86", mPhone);
                }

                break;

            case R.id.login:
               String userCode = mEtUserPhone.getText().toString().trim();
                if (TextUtils.isEmpty(mPhone)) {
                    Toast.makeText(this, "号码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = mEtUserCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "号码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //提交用户手机号
                if(SMSUtil.judgePhoneNums(this, mPhone)) {
                    SMSSDK.submitVerificationCode("86", mPhone, code);
                }


                break;
        }
    }
}
