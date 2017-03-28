package com.wisdom.takeout.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdom.takeout.R;
import com.wisdom.takeout.module.bean.RecepitAddress;
import com.wisdom.takeout.module.dao.AddressDao;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/3/23.
 */
public class AddOrEditAddressActivity extends AppCompatActivity {

    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ib_delete)
    ImageButton mIbDelete;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_women)
    RadioButton mRbWomen;
    @BindView(R.id.rg_sex)
    RadioGroup mRgSex;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.ib_delete_phone)
    ImageButton mIbDeletePhone;
    @BindView(R.id.ib_add_phone_other)
    ImageButton mIbAddPhoneOther;
    @BindView(R.id.et_phone_other)
    EditText mEtPhoneOther;
    @BindView(R.id.ib_delete_phone_other)
    ImageButton mIbDeletePhoneOther;
    @BindView(R.id.rl_phone_other)
    RelativeLayout mRlPhoneOther;
    @BindView(R.id.et_receipt_address)
    EditText mEtReceiptAddress;
    @BindView(R.id.et_detail_address)
    EditText mEtDetailAddress;
    @BindView(R.id.tv_label)
    TextView mTvLabel;
    @BindView(R.id.ib_select_label)
    ImageView mIbSelectLabel;
    @BindView(R.id.bt_ok)
    Button mBtOk;
    private RecepitAddress mAddress;
    private AddressDao mAddressDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_receipt_address);
        ButterKnife.bind(this);
        processIntent();
        mAddressDao = new AddressDao(this);
        mEtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //有焦点的处理
                } else {

                }
            }
        });
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mIbDeletePhone.setVisibility(View.GONE);
                } else {
                    //显示删除按钮
                    mIbDeletePhone.setVisibility(View.VISIBLE);
                }
            }
        });

        mEtPhoneOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mIbDeletePhoneOther.setVisibility(View.GONE);
                } else {
                    //显示删除按钮
                    mIbDeletePhoneOther.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void processIntent() {
        if (getIntent() != null) {
            mAddress = (RecepitAddress) getIntent().getSerializableExtra("address");
            if (mAddress != null) {
                //地址不为空，修改地址
                mTvTitle.setText("修改地址");
                mIbDelete.setVisibility(View.VISIBLE);
                mIbDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAddress();
                    }
                });
                mEtName.setText(mAddress.getName());
                String sex = mAddress.getSex();
                if ("女士".equals(sex)) {
                    mRbWomen.setChecked(true);
                } else {
                    mRbMan.setChecked(true);
                }
                mEtPhone.setText(mAddress.getPhone());
                mEtPhoneOther.setText(mAddress.getPhoneOther());
                mEtReceiptAddress.setText(mAddress.getAddress());
                mEtDetailAddress.setText(mAddress.getDetailAddress());
                mTvLabel.setText(mAddress.getLabel());
                mTvLabel.setBackgroundColor(Color.GRAY);
                mTvLabel.setTextColor(Color.BLACK);
            } else {

            }
        }
    }

    private void deleteAddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除地址么？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isOk = mAddressDao.deleteRecepitAddress(mAddress);
                if (isOk) {
                    finish();
                } else {
                    Log.e("address", "删除地址异常");
                }
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @OnClick({R.id.ib_back, R.id.ib_delete_phone, R.id.ib_add_phone_other, R.id.bt_ok, R.id.ib_delete_phone_other, R.id.ib_select_label})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_delete_phone:
                //清空文本
                mEtPhone.setText("");
                break;
            case R.id.ib_delete_phone_other:
                //清空文本
                mEtPhoneOther.setText("");
                break;
            case R.id.ib_add_phone_other:
                //点击后显示备用电话
                mRlPhoneOther.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_ok:
                //开始校验
                boolean isOk = checkReceiptAddressInfo();
                if (isOk) {
                    if (mAddress == null) {
                        //校验通过，新增地址到本地sqlite
                        insertAddress();
                    } else {
                        updateAddress();
                    }
                }
                break;
            case R.id.ib_select_label:
                //选择地址的标签
                selectlabel();
                break;
        }
    }

    private void updateAddress() {
        String name = mEtName.getText().toString().trim();
        String sex = "女士";
        if (mRbMan.isChecked()) {
            sex = "先生";
        }
        String phone = mEtPhone.getText().toString().trim();
        String phoneOther = mEtPhoneOther.getText().toString().trim();
        String address = mEtReceiptAddress.getText().toString().trim();
        String detailAddress = mEtDetailAddress.getText().toString().trim();
        String label = mTvLabel.getText().toString().trim();
        mAddress.setName(name);
        mAddress.setSex(sex);
        mAddress.setPhone(phone);
        mAddress.setPhoneOther(phoneOther);
        mAddress.setAddress(address);
        mAddress.setDetailAddress(detailAddress);
        mAddress.setLabel(label);
        boolean isOk = mAddressDao.update(mAddress);
        if (isOk) {
            Toast.makeText(this, "更新地址成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "更新地址失败!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertAddress() {
        String name = mEtName.getText().toString().trim();
        String sex = "女士";
        if (mRbMan.isChecked()) {
            sex = "先生";
        }
        String phone = mEtPhone.getText().toString().trim();
        String phoneOther = mEtPhoneOther.getText().toString().trim();
        String address = mEtReceiptAddress.getText().toString().trim();
        String detailAddress = mEtDetailAddress.getText().toString().trim();
        String label = mTvLabel.getText().toString().trim();
        RecepitAddress recepitAddress = new RecepitAddress(999, name, sex, phone, phoneOther, address, detailAddress, label, "33");
        boolean isOk = mAddressDao.addRecepitAddress(recepitAddress);
        if (isOk) {
            Toast.makeText(this, "新增地址成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "新增地址失败!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private String[] mItems = new String[]{"无", "家", "学校", "公司"};
    private String[] mColors = new String[]{"#778899", "#ddeeff", "#aa2233", "#666666"};

    private void selectlabel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择地址的标签");
        builder.setItems(mItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //让textview显示点击后的标签
                mTvLabel.setTextColor(Color.BLACK);
                mTvLabel.setBackgroundColor(Color.parseColor(mColors[which]));
                mTvLabel.setText(mItems[which]);
                //三原色、
            }
        });
        builder.show();
    }

    public boolean checkReceiptAddressInfo() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        String receiptAddress = mEtReceiptAddress.getText().toString().trim();
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请填写收获地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        String address = mEtDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isMobileNO(String phone) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return phone.matches(telRegex);
    }
}
