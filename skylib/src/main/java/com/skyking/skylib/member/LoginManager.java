package com.skyking.skylib.member;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.skyking.skylib.bundles.LoginBundle;
import com.skyking.skylib.entitys.LoginEntity;

/**
 * Created by SkykingAndroid on 2017/2/15.
 */

public class LoginManager {


    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    private final static String KEY_ACCOUNT = "account";

    private static String account;



    public static void init(Context context) {
        LoginManager.sp = context.getSharedPreferences(LoginManager.class.getSimpleName(), Context.MODE_PRIVATE);
        LoginManager.editor = LoginManager.sp.edit();
        if (sp.contains(KEY_ACCOUNT)) account = sp.getString(KEY_ACCOUNT, "");
    }

    public static void save(LoginBundle loginBundle, LoginEntity loginEntity) {
        Gson gson = new Gson();
        editor.putString(LoginBundle.class.getSimpleName(), gson.toJson(loginBundle));
        editor.putString(LoginEntity.class.getSimpleName(), gson.toJson(loginEntity));
        if (loginBundle != null) {
            String account = loginBundle.getAccount();
            if (!TextUtils.isEmpty(account)) editor.putString(KEY_ACCOUNT, account);
        }
        editor.commit();
    }

    public static LoginBundle getLoginBundle() {
        String str = sp.getString(LoginBundle.class.getSimpleName(), "");
        if (TextUtils.isEmpty(str)) return null;
        Gson gson = new Gson();
        return gson.fromJson(str, LoginBundle.class);
    }

    public static LoginEntity getLoginEntity() {
        String str = sp.getString(LoginEntity.class.getSimpleName(), "");
        if (TextUtils.isEmpty(str)) return null;
        Gson gson = new Gson();
        return gson.fromJson(str, LoginEntity.class);
    }

    public static void clear() {
        editor.clear().commit();
        if (!TextUtils.isEmpty(account)) editor.putString(KEY_ACCOUNT, account).commit();
    }


    public static String getKey() {
        LoginEntity entity = getLoginEntity();
        if (entity != null && entity.hasData()) {
            String key = entity.data.get(0).key;
            if (!TextUtils.isEmpty(key)) return key;
            return "";
        }
        return "";
    }

    public static boolean isLogin(){
        return !TextUtils.isEmpty(getKey());
    }
}
