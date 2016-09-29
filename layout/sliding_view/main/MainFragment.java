package com.twgood.android.main;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.twgood.android.R;
import com.twgood.android.basic.BasicFragment;
import com.twgood.android.main.ChannelFragment;
import com.twgood.android.objects.sliding_layout.SlidingTabFragment;
import com.twgood.android.objects.sliding_layout.SlidingTabLayout;
import com.twgood.android.objects.sliding_layout.TabFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SkykingAndroid on 2016/9/14.
 */
public class MainFragment extends BasicFragment {


    @Override
    protected void initView(View view) {

        final List<SlidingTabFragment> list = getTabFragments();

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(activity.getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_layout);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return list.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return list.get(position).getDividerColor();
            }
        });
        slidingTabLayout.setBackgroundResource(R.color.app_blue);
        slidingTabLayout.setMonoSpace(true);
        slidingTabLayout.setTitleColor(Color.WHITE);
        slidingTabLayout.setTitleTextSize(18);
        slidingTabLayout.setIndicatorHeightDip(6);
        slidingTabLayout.setViewPager(viewPager);

    }

    private List<SlidingTabFragment> getTabFragments() {
        List<SlidingTabFragment> list = new ArrayList<>();
        list.add(ChannelFragment.getInstance(getString(R.string.channel), Color.WHITE));
        list.add(ProgramListFragment.getInstance(getString(R.string.program_list), Color.WHITE));
        return list;
    }

    @Override
    protected int getFragmentLayoutResource() {
        return R.layout.fragment_main;
    }
}
