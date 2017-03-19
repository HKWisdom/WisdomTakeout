package com.wisdom.takeout.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/3/19.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initView());
        ButterKnife.bind(this);

        initData();
    }

    /**
     * 初始化数据
     */
    protected  void initData(){

    }

    /**
     * 加载子类视图
     * @return
     */
    protected abstract View initView();
}
