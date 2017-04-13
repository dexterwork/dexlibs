package com.skyking.televant.sliding_layout.sample;

import android.view.View;
import android.widget.TextView;

import com.skyking.televant.R;
import com.skyking.televant.sliding_layout.SlidingTabFragment;

/**
 * Created by SkykingAndroid on 2017/4/13.
 */

public class F3 extends SlidingTabFragment {

    public static F3 getInstance(){
        F3 f=new F3();
        f.setTitle("F3");
        return f;
    }

    @Override
    public void initLayout(View view) {
        ((TextView)view.findViewById(R.id.tvName)).setText("F3");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.test_fragment;
    }
}
