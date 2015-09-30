package com.awant.lion.tools;

import android.util.Log;

import com.awant.lion.settings.Settings;

public class MLog {

    public static final boolean SHOW_LOG = !Settings.PUBLISH;

    private static String getClassName(Object obj) {
        return obj.getClass().getSimpleName();
    }

    public static void v(Object obj, String msg) {
        if (Settings.debug) Log.v(getClassName(obj), msg);
    }

    public static void v(Object obj, String msg, boolean force) {
        if (force) {
            Log.v(getClassName(obj), msg);
            return;
        }
        if (Settings.debug) Log.v(getClassName(obj), msg);
    }


    public static void d(Object obj, String msg) {
        if (Settings.debug) Log.d(getClassName(obj), msg);
    }

    public static void d(Object obj, String msg, boolean force) {
        if (force) {
            Log.d(getClassName(obj), msg);
            return;
        }
        if (Settings.debug) Log.d(getClassName(obj), msg);
    }

    public static void i(Object obj, String msg) {
        if (Settings.debug) Log.i(getClassName(obj), msg);
    }

    public static void i(Object obj, String msg, boolean force) {
        if (force) {
            Log.i(getClassName(obj), msg);
            return;
        }
        if (Settings.debug) Log.i(getClassName(obj), msg);
    }

    public static void w(Object obj, String msg) {
        Log.w(getClassName(obj), msg);
    }

    public static void e(Object obj, String msg) {
        Log.e(getClassName(obj), msg);
    }

    public static void v(String tag, String msg) {
        if (Settings.debug) Log.v(tag, msg);
    }

    public static void v(String tag, String msg, boolean force) {
        if (force) {
            Log.v(tag, msg);
            return;
        }
        if (Settings.debug) Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (Settings.debug) Log.d(tag, msg);
    }

    public static void d(String tag, String msg, boolean force) {
        if (force) {
            Log.d(tag, msg);
            return;
        }
        if (Settings.debug) Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (Settings.debug) Log.i(tag, msg);
    }

    public static void i(String tag, String msg, boolean force) {
        if (force) {
            Log.i(tag, msg);
            return;
        }
        if (Settings.debug) Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void v(Class<?> cls, String msg) {
        if (Settings.debug) Log.v(cls.getSimpleName(), msg);
    }

    public static void v(Class<?> cls, String msg, boolean force) {
        if (force) {
            Log.v(cls.getSimpleName(), msg);
            return;
        }
        if (Settings.debug) Log.v(cls.getSimpleName(), msg);
    }

    public static void d(Class<?> cls, String msg) {
        if (Settings.debug) Log.d(cls.getSimpleName(), msg);
    }

    public static void d(Class<?> cls, String msg, boolean force) {
        if (force) {
            Log.d(cls.getSimpleName(), msg);
            return;
        }
        if (Settings.debug) Log.d(cls.getSimpleName(), msg);
    }

    public static void i(Class<?> cls, String msg) {
        if (Settings.debug) Log.i(cls.getSimpleName(), msg);
    }

    public static void i(Class<?> cls, String msg, boolean force) {
        if (force) {
            Log.i(cls.getSimpleName(), msg);
            return;
        }
        if (Settings.debug) Log.i(cls.getSimpleName(), msg);
    }

    public static void w(Class<?> cls, String msg) {
        Log.w(cls.getSimpleName(), msg);
    }

    public static void e(Class<?> cls, String msg) {
        Log.e(cls.getSimpleName(), msg);
    }


}
