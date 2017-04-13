package com.skyking.televant.test;

import com.skyking.televant.sliding_layout.BasicSlidingFragment;
import com.skyking.televant.sliding_layout.SlidingTabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SkykingAndroid on 2017/4/13.
 */

public class SampleSlidingFragment extends BasicSlidingFragment {



    @Override
    protected List<SlidingTabFragment> initSlidingFragmentList() {
        List<SlidingTabFragment> list=new ArrayList<>();
        list.add(F1.getInstance());
        list.add(F2.getInstance());
        list.add(F3.getInstance());
        return list;
    }
}
