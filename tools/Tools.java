﻿package com.awant.lion.tools;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dexter on 2015/6/17.
 */
public class Tools {


   public static String getDeviceId(Context context) {
      return android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }

    public static String strLen(String str, int len) {
        while (str.length() < len) str = "0" + str;
        return str;
    }

    public static String strLen(int n, int len) {
        return strLen(String.valueOf(n), len);
    }

    public static String floatToInt(String strFloat) {
        float tempFloat = Float.valueOf(strFloat);
        int tempInt = (int) tempFloat;
        return String.valueOf(tempInt);
    }

    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String DoubleToInteger(String str) {
        double d = Double.valueOf(str);
        return String.valueOf( (int) d);
    }

 public void clearCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = context.getFilesDir();
        if (file != null && file.isDirectory()) {
            for (String name : file.list()) {
                File temp=new File(context.getFilesDir(),name);
                context.deleteFile(temp.getName());
            }
        }
    }


    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
	
	 public static void hideKeyboard(Context context,View view){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

	public Bitmap getBitmap(String path) {
        File imgFile = new File(path);
        if (!imgFile.exists()) return null;

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imgFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] data = new byte[0];
        try {
            data = readStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data != null) {
            try {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            } catch (OutOfMemoryError oom) {
                oom.printStackTrace();
            }
        }
        return bitmap;
    }

    private byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
	
	
    /**
     * 打開瀏覽器
     * @param activity
     * @param strUrl
     */
    public void openUrlPage(Activity activity,String strUrl){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
        activity.startActivity(intent);
    }

	
	//  try {
    //       mVersionCode = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;
    //        mVersionName = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
    //    } catch (PackageManager.NameNotFoundException e) {
    //        e.printStackTrace();
    //    }
	
	
	  /**
     * get pixels from dp dize
     * @param context
     * @param dbSize
     * @return
     */
    public int getPixelsFromDp(Context context, int dbSize) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dbSize * scale + 0.5f);
    }
	
	
	public int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	 public void toGooglePlay(Context context) {
        String packageName = context.getPackageName();
        Intent intent;
        try {
            // Open app with Google Play app
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        } catch (android.content.ActivityNotFoundException anfe) {
            // Open Google Play website
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName));
        }
        context.startActivity(intent);
    }
	
	/**
	*解鎖讓螢幕可以翻轉
	*/
	public void unlockOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

	/**
	*讓螢幕不可以翻轉
	*/
    public void lockOrientationPortrait() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
	
	  /**
     * 回傳螢幕目前是否為水平橫式
     * @return
     */
    public boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
	
	  /**
     * 將一串文字前幾個字轉為 * 號
     *
     * @param originString 原始字串
     * @param count        前面幾個字
     * @return
     */
    public String replacePasswordString(String originString, int count) {
        String sub0 = originString.substring(0, originString.length() - (originString.length() - count));
        String sub = originString.substring(count);

        sub0 = sub0.replaceAll("\\d", "*");
        return sub0 + sub;
    }
	
	 /**
     * string to md5
     * @param string
     * @return
     */
	  public static String md5(String string) {
        MessageDigest digest;
        String stringEncoded = "";
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(string.getBytes());
            byte[] bytes = digest.digest();
            int length = bytes.length;
            StringBuilder sb = new StringBuilder(length << 1);
            for (int i = 0; i < length; i++) {
                sb.append(Character.forDigit((bytes[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(bytes[i] & 0x0f, 16));
            }
            stringEncoded = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringEncoded;
    }
	
	 /**
     * 檢查版本 (format: 1.0.0)
     *
     * @param deviceVersion 裝置上的版本
     * @param newVersion    線上版本
     * @return 若與線上版本相同則回傳 true
     */
    public boolean checkVersion(String deviceVersion, String newVersion) {
        int[] dVers = convertStringArrayToIntegerArray(deviceVersion.split("\\."));
        int[] sVers = convertStringArrayToIntegerArray(newVersion.split("\\."));

        if (dVers == null) return false;
        if (sVers == null) return false;
        if (dVers.length == 0 || sVers.length == 0) return false;
        if (dVers.length != sVers.length) return false;

        for (int i = 0; i < dVers.length; i++) {
            MLog.d(this, "dexter sVer=" + sVers[i] + "/dVer=" + dVers[i]);
        }
        for (int i = 0; i < sVers.length; i++) {
            if (sVers[i] > dVers[i]) return false;
            if (i == sVers.length - 1) break;
            if (sVers[i] < dVers[i]) return true;
        }
        return true;
    }

    public boolean checkVersion(Context context, String newVersion) {
        return checkVersion(getVersionName(context), newVersion);
    }
	
	 public String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int[] convertStringArrayToIntegerArray(String[] strArray) {
        if (strArray == null) return null;
        int[] nArray = new int[strArray.length];
        try {
            for (int i = 0; i < strArray.length; i++) {
                nArray[i] = Integer.parseInt(strArray[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nArray;
    }

    public boolean checkFocused(View view) {
        if (view.isFocused())
            return true;

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (checkFocused(viewGroup.getChildAt(i)))
                    return true;
            }
        }
        return false;
    }
}
