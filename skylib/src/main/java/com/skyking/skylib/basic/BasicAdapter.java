package com.skyking.skylib.basic;

import android.widget.BaseAdapter;

/**
 * Created by SkykingAndroid on 2017/2/9.
 */

public abstract class BasicAdapter extends BaseAdapter {
    @Override
    public long getItemId(int position) {
        return 0;
    }
}
