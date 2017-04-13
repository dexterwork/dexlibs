package com.skyking.televant.sliding_layout.sample;

import com.skyking.televant.sliding_layout.BasicSlidingFragment;
import com.skyking.televant.sliding_layout.SlidingTabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SkykingAndroid on 2017/4/13.
 */

public class SampleSlidingFragment extends BasicSlidingFragment {


    /**
     * 把要切換的 fragment 加到 list 中即可, 然後把這個 SampleSlidingFragment replace to container.
     * @return
     */

    @Override
    protected List<SlidingTabFragment> initSlidingFragmentList() {
        List<SlidingTabFragment> list=new ArrayList<>();
        list.add(F1.getInstance());
        list.add(F2.getInstance());
        list.add(F3.getInstance());
        return list;
    }
}
