package com.twgood.android.main;

import android.graphics.Color;
import android.view.View;

import com.twgood.android.R;
import com.twgood.android.objects.sliding_layout.SlidingTabFragment;

/**
 * Created by SkykingAndroid on 2016/9/19.
 */
public class ChannelFragment extends SlidingTabFragment {

    public static ChannelFragment getInstance(String title, int indicatorColor) {
        ChannelFragment fragment = new ChannelFragment();
        fragment.setDividerColor(Color.TRANSPARENT);
        fragment.setIndicatorColor(indicatorColor);
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getFragmentLayoutResource() {
        return R.layout.test0;
    }
}
