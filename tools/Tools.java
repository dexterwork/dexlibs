package com.awant.lion.tools;

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
	
	//  try {
    //       mVersionCode = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;
    //        mVersionName = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
    //    } catch (PackageManager.NameNotFoundException e) {
    //        e.printStackTrace();
    //    }
}
