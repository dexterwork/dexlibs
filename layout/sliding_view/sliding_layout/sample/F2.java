package com.skyking.televant.test;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.skyking.televant.R;
import com.skyking.televant.sliding_layout.SlidingTabFragment;

/**
 * Created by SkykingAndroid on 2017/4/13.
 */

public class F2 extends SlidingTabFragment {

    public static F2 getInstance(){
        F2 f=new F2();
        f.setTitle("F2");
        return f;
    }

    @Override
    public void initLayout(View view) {
        ((TextView)view.findViewById(R.id.tvName)).setText("F2");
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.test_fragment;
    }
}
