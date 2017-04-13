package com.skyking.televant.sliding_layout.sample;

import android.view.View;
import android.widget.TextView;

import com.skyking.televant.R;
import com.skyking.televant.sliding_layout.SlidingTabFragment;

/**
 * Created by SkykingAndroid on 2017/4/13.
 */

public class F1 extends SlidingTabFragment {

    public static F1 getInstance(){
        F1 f=new F1();
        f.setTitle("F1");
        return f;
    }

    @Override
    public void initLayout(View view) {
        ((TextView)view.findViewById(R.id.tvName)).setText("F1");
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.test_fragment;
    }
}
