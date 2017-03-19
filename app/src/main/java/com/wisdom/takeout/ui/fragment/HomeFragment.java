package com.wisdom.takeout.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HKWisdom on 2017/3/19.
 */
public class HomeFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.BLUE);
        tv.setText(this.getClass().getSimpleName());
        return tv;
    }
}
