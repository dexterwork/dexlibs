package dexterliu.studio.imoney.basic.basic_layout;

import android.view.ViewGroup;

/**
 * Created by Dexter on 2017/8/5.
 */

public interface BasicLayoutInterface<T> {
    T setView(ViewGroup parent);
    void setMatchParent();
}
