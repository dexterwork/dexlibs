package com.awant.lion.tools;

import android.app.Activity;
import android.view.LayoutInflater;

/**
 * Created by 旺比優05 on 2015/7/13.
 */
public class LayoutTools {

    public static LayoutInflater getInflater(Activity activity) {
        return (LayoutInflater) activity
                .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }
}
