package com.skyking.skylib.tools;

import android.util.Log;

public class MLog {

    public static final boolean SHOW_LOG = true;
    private static final boolean SHOW_FRONT_UNIT = true;
    private static final String STRING_FRONT_UNIT = "dexter ";

    private static String getClassName(Object obj) {
        return obj.getClass().getSimpleName();
    }

    private static String getFrontString() {
        return SHOW_FRONT_UNIT ? STRING_FRONT_UNIT : "";
    }

    public static void v(Object obj, String msg) {
        if (SHOW_LOG) Log.v(getClassName(obj), getFrontString() + msg);
    }

    public static void d(Object obj, String msg) {
        if (SHOW_LOG) Log.d(getClassName(obj), getFrontString() + msg);
    }

    public static void i(Object obj, String msg) {
        if (SHOW_LOG) Log.i(getClassName(obj), getFrontString() + msg);
    }

    public static void w(Object obj, String msg) {
        if (SHOW_LOG) Log.w(getClassName(obj), getFrontString() + msg);
    }

    public static void e(Object obj, String msg) {
        if (SHOW_LOG) Log.e(getClassName(obj), getFrontString() + msg);
    }

    public static void v(String tag, String msg) {
        if (SHOW_LOG) Log.v(tag, getFrontString() + msg);
    }

    public static void d(String tag, String msg) {
        if (SHOW_LOG) Log.d(tag, getFrontString() + msg);
    }

    public static void i(String tag, String msg) {
        if (SHOW_LOG) Log.i(tag, getFrontString() + msg);
    }

    public static void w(String tag, String msg) {
        if (SHOW_LOG) Log.w(tag, getFrontString() + msg);
    }

    public static void e(String tag, String msg) {
        if (SHOW_LOG) Log.e(tag, getFrontString() + msg);
    }

    public static void v(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.v(cls.getSimpleName(), getFrontString() + msg);
    }

    public static void d(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.d(cls.getSimpleName(), getFrontString() + msg);
    }

    public static void i(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.i(cls.getSimpleName(), getFrontString() + msg);
    }

    public static void w(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.w(cls.getSimpleName(), getFrontString() + msg);
    }

    public static void e(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.e(cls.getSimpleName(), getFrontString() + msg);
    }


}
