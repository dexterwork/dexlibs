package com.awant.lion.tools;

import android.util.Log;

public class MLog {

    public static final boolean SHOW_LOG = true;

    private static String getClassName(Object obj) {
        return obj.getClass().getSimpleName();
    }

    public static void v(Object obj, String msg) {
        if (SHOW_LOG) Log.v(getClassName(obj), msg);
    }

    public static void d(Object obj, String msg) {
        if (SHOW_LOG) Log.d(getClassName(obj), msg);
    }

    public static void i(Object obj, String msg) {
        if (SHOW_LOG) Log.i(getClassName(obj), msg);
    }

    public static void w(Object obj, String msg) {
        if (SHOW_LOG) Log.w(getClassName(obj), msg);
    }

    public static void e(Object obj, String msg) {
        if (SHOW_LOG) Log.e(getClassName(obj), msg);
    }


}
