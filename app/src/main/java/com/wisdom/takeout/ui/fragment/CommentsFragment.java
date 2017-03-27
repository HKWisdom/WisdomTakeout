package com.wisdom.takeout.ui.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by HKWisdom on 2017/3/25.
 */
public class CommentsFragment extends BaseFragment {
    @Override
    protected View initView() {
        TextView tv = new TextView(getActivity());
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.BLUE);
                tv.setText(this.getClass().getSimpleName());
        return tv;
    }
}
