package com.skyking.skylib.basic;

/**
 * Created by SkykingAndroid on 2017/4/18.
 */

public abstract class ViewFragment extends BasicFragment {

    public boolean onBackPressed() {
        return false;
    }


    public ViewFragmentListener viewFragmentListener;

    public interface ViewFragmentListener {
        void replace( ViewFragment fragment);
    }

}
