package com.skyking.skylib.bundles;

import com.skyking.skylib.basic.BasicBundle;

/**
 * Created by SkykingAndroid on 2017/2/15.
 */

public class VerifyBundle extends BasicBundle {
    public String action="TVverify";
    public String account;
    public String device;

    public VerifyBundle(String account, String device) {
        this.account = account;
        this.device = device;
    }
}
