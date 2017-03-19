package com.wisdom.takeout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wisdom.takeout.ui.activity.BaseActivity;
import com.wisdom.takeout.ui.fragment.HomeFragment;
import com.wisdom.takeout.ui.fragment.MoreFragment;
import com.wisdom.takeout.ui.fragment.OrderFragment;
import com.wisdom.takeout.ui.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    @BindView(R.id.main_bottome_switcher_container)
    LinearLayout mMainBottomSwitcherContainer;
    private FragmentManager mSupportFragmentManager;
    private List<Fragment> mFragmentList;

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_main, null);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();

        mSupportFragmentManager = getSupportFragmentManager();
        mFragmentList = new ArrayList<>();
        initBottom();
        initFragment();
        changeState(0);
    }

    private void initBottom() {
        int childCount = mMainBottomSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childAt = mMainBottomSwitcherContainer.getChildAt(i);
            childAt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int indexOfChild = mMainBottomSwitcherContainer.indexOfChild(childAt);

                    changeState(indexOfChild);
                }
            });
        }
    }

    /**
     * 改变底部状态栏
     *
     * @param index
     */
    private void changeState(int index) {
        int childCount = mMainBottomSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {

            View childAt = mMainBottomSwitcherContainer.getChildAt(i);

            if (index == i) {
                setEnable(childAt, false);
            } else {
                setEnable(childAt, true);
            }

            //替换fragment
            mSupportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, mFragmentList.get(index)).commit();
        }
    }

    /**
     * 设置底部按钮是否可以点击
     *
     * @param childAt
     * @param state
     */
    private void setEnable(View childAt, boolean state) {
        childAt.setEnabled(state);

        if (childAt instanceof ViewGroup) {
            int childCount = ((ViewGroup) childAt).getChildCount();

            for (int i = 0; i < childCount; i++) {
                View view = ((ViewGroup) childAt).getChildAt(i);
                view.setEnabled(state);
            }
        }
    }

    public void initFragment() {
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new OrderFragment());
        mFragmentList.add(new UserFragment());
        mFragmentList.add(new MoreFragment());
    }
}
