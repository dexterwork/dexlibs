package com.skyking.skylib.bundles;

import com.skyking.skylib.basic.BasicBundle;

/**
 * Created by SkykingAndroid on 2017/2/15.
 */

public class ProgramBundle extends BasicBundle {
    public String action="program";
    public String chid;

    public ProgramBundle(String chid) {
        this.chid = chid;
    }
}
