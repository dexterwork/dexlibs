package com.twgood.android.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Calendar;

/**
 * Created by SkykingAndroid on 2016/9/19.
 */
public class UserManager {
    Context context;

    final String FILE = "user_info";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    final String KEY_ACCOUNT = "KEY_ACCOUNT";
    final String KEY_PASSWORD = "KEY_PASSWORD";
    final String KEY_LAST_ACCOUNT = "KEY_ORIGIN_ACCOUNT";
    final String KEY_LOGIN_UNIX_TIME = "KEY_LOGIN_UNIX_TIME";

    public UserManager(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void saveAccount(String account, String password) {
        editor.putString(KEY_ACCOUNT, account);
        editor.putString(KEY_LAST_ACCOUNT, account);
        editor.putString(KEY_PASSWORD, password);
        editor.putLong(KEY_LOGIN_UNIX_TIME, Calendar.getInstance().getTimeInMillis() / 1000);
        editor.commit();
    }

    public void clearInfo() {
        editor.remove(KEY_ACCOUNT);
        editor.remove(KEY_PASSWORD);
        editor.commit();
    }

    public String getLastAccount() {
        return sp.getString(KEY_LAST_ACCOUNT, "");
    }

    public String getAccount() {
        return sp.getString(KEY_ACCOUNT, "");
    }

    public String getPassword() {
        return sp.getString(KEY_PASSWORD, "");
    }

    public long getLastLoginUnixTime() {
        return sp.getLong(KEY_LOGIN_UNIX_TIME, 0);
    }


}
