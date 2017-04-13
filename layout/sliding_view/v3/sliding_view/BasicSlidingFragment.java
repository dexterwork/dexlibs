package com.skyking.televant.sliding_layout;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.skyking.televant.R;
import com.skyking.televant.basic.BasicFragment;

import java.util.List;

/**
 * Created by SkykingAndroid on 2017/4/13.
 */

public abstract class BasicSlidingFragment extends BasicFragment {

    protected List<SlidingTabFragment> slidingFragmentList;

    @Override
    protected void initView(View view) {
        slidingFragmentList = initSlidingFragmentList();

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(activity.getSupportFragmentManager(), slidingFragmentList);
        viewPager.setAdapter(adapter);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_layout);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return slidingFragmentList.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return slidingFragmentList.get(position).getDividerColor();
            }
        });
        slidingTabLayout.setBackgroundResource(R.color.sliding_background);
        slidingTabLayout.setMonoSpace(true);
        slidingTabLayout.setTitleColor(Color.BLACK);
        slidingTabLayout.setTitleTextSize(18);
        slidingTabLayout.setIndicatorHeightDip(6);
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.sliding_layout;
    }


    protected abstract List<SlidingTabFragment> initSlidingFragmentList();

}
