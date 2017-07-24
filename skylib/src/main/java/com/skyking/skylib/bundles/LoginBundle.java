package com.skyking.skylib.bundles;

import com.skyking.skylib.basic.BasicBundle;
import com.skyking.skylib.tools.Tools;

/**
 * Created by SkykingAndroid on 2017/2/15.
 */

public class LoginBundle extends BasicBundle {

    public String action="TVlogin";
    public String account;
    public String password;
    public String device;

    public LoginBundle(String account, String password,String device) {
        this.account = account;
        this.password = new Tools().toMD5(password);
        this.device=device;
    }

    public String getAccount() {
        return account;
    }
}
