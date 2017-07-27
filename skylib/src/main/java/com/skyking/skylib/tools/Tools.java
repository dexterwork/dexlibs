package com.skyking.skylib.tools;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.MessageDigest;

/**
 * Created by SkykingAndroid on 2016/7/27.
 */
public class Tools {

    public String getSDcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/";
    }


//    /**
//     * write string to SDcard, DEFAULT file name:"log_times.txt"
//     *
//     * @param message
//     * @param fileName
//     */
//    public void writeStringToFileInSDcard(String fileName, String message, boolean append) {
//        if (TextUtils.isEmpty(fileName))
//            fileName = "log_" + Calendar.getInstance().getTimeInMillis() + ".txt";
//        try {
//            File file = new File(getSDcardPath() + fileName);
//            file.createNewFile();
//            FileOutputStream output = new FileOutputStream(file, append);
//            output.write(message.getBytes());  //write()寫入字串，並將字串以byte形式儲存。
//            output.flush();
//            output.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static void hideKeyboard(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    /**
     * 打開瀏覽器
     *
     * @param activity
     * @param strUrl
     */
    public void openUrlPage(Activity activity, String strUrl) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toGooglePlay(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + activity.getPackageName()));
            activity.startActivity(intent);
        } catch (Exception e) {
            String url = "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
            new Tools().openUrlPage(activity, url);
        }
    }

    /**
     * string convert to MD5
     *
     * @param string
     * @return
     */
    public static String toMD5(String string) {
        String md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] barr = md.digest(string.getBytes());  //將 byte 陣列加密
            StringBuffer sb = new StringBuffer();  //將 byte 陣列轉成 16 進制
            for (int i = 0; i < barr.length; i++) {
                sb.append(byte2Hex(barr[i]));
            }
            String hex = sb.toString();
            md5 = hex; //一律轉成大寫
//            md5 = hex.toUpperCase(); //一律轉成大寫
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static String byte2Hex(byte b) {
        String[] h = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        int i = b;
        if (i < 0) {
            i += 256;
        }
        return h[i / 16] + h[i % 16];
    }

    public boolean checkInternet(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        //如果未連線的話，mNetworkInfo會等於null
        if (mNetworkInfo == null) return false;
        //網路是否已連線(true or false)
        return mNetworkInfo.isConnected() && mNetworkInfo.isAvailable();

//        //網路連線方式名稱(WIFI or mobile)
//        mNetworkInfo.getTypeName();
//        //網路連線狀態
//        mNetworkInfo.getState();
//        //網路是否可使用
//        mNetworkInfo.isAvailable();
//        //網路是否已連接or連線中
//        mNetworkInfo.isConnectedOrConnecting();
//        //網路是否故障有問題
//        mNetworkInfo.isFailover();
//        //網路是否在漫遊模式
//        mNetworkInfo.isRoaming();

    }

    public static boolean matchesEmail(String email) {
        return email.matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$") ? true : false;
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public float convertDpToPixel(float dp, Activity activity) {
        float px = dp * activity.getResources().getDisplayMetrics().density;
        return px;
    }


    public static long getCurrentUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public void writeStringToFileInSDcard(String fileName, String message, boolean append) {

        try {
            File file = new File(getSDcardPath() + fileName);
            file.createNewFile();
            FileOutputStream output = new FileOutputStream(file, append);
            output.write(message.getBytes());  //write()寫入字串，並將字串以byte形式儲存。
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float convertPixelToDp(int px, Context context) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static boolean isFirstApp(Context context) {
        SharedPreferences sp = context.getSharedPreferences("set", Context.MODE_PRIVATE);
        if (sp.contains("first")) return false;
        sp.edit().putString("first", "true").commit();
        return true;
    }
}
