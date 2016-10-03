package com.twgood.android.objects.sliding_layout;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SkykingAndroid on 2016/9/19.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    List<SlidingTabFragment> list;

    public TabFragmentPagerAdapter(FragmentManager fm, List<SlidingTabFragment> list) {
        super(fm);
        this.list = list;
        if (this.list == null) this.list = new ArrayList<>();
    }

    @Override
    public SlidingTabFragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}