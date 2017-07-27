package com.skyking.skylib.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.skyking.skylib.R;


/**
 * Created by SkykingAndroid on 2016/7/20.
 */
public class GetInfo {

    final static boolean BY_DEVICE = true;//會繞過已定的 device id

    final String TOKEN_KEY = "Televant@";


    public static String getDeviceId(Context context) {
        String id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

//
//        MLog.w("GetInfo","dexter device id="+id+ "/tm="+tm.getDeviceId());
        return id;
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return tm.getDeviceId();
    }

    public static boolean byDeviceId(Context context) {
        if (!BY_DEVICE) return false;
        String[] deviceIds = context.getResources().getStringArray(R.array.IDS);
        String devId = GetInfo.getDeviceId(context);
        for (String id : deviceIds) {
            if (id.equals(devId)) return true;
        }
        return false;
    }


    public String getAppMD5(String strDate) {
        return Tools.toMD5(TOKEN_KEY + strDate);
    }


//    public MediaTypeEnum getMediaTypeEnum(String playURL) {
//        if (playURL.contains("mp4")) return MediaTypeEnum.VIDEO;
//        return MediaTypeEnum.MUSIC;
//    }

    public String getVersionName(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getVersionCode(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
