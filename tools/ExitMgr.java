package com.skyking.skykingradio;

import java.util.Calendar;

/**
 * Created by SkykingAndroid on 2016/7/21.
 */
public class ExitMgr {

    private static long firstTime;
    private static final int DELAY = 2000;

    public static boolean toExit() {
        Calendar calendar = Calendar.getInstance();
        long clickTime = calendar.getTimeInMillis();
        if (clickTime < firstTime + DELAY) return true;
        firstTime = clickTime;
        return false;
    }
}
