package com.skyking.skylib.tools;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import com.skyking.skylib.basic.BasicActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SkykingAndroid on 2017/2/13.
 */

public class FileTool {

    final static String TAG = FileTool.class.getSimpleName();

    BasicActivity activity;
    public static final String PATH_IMAGE_ADV_INNER = "images/adv/inner/";
    public static final String PATH_IMAGE_CHANNEL_ICON = "images/channel/";
    public static final String PATH_IMAGE_RADIO_ICON = "images/radio/";
    public static final String PATH_IMAGE_ITEM_ICON = "images/item/";
    public static final String PATH_JSON_RECORD = "json/";
    //    public static final String PATH_GCM_MESSAGE = "/message/gcm";
    private final String BASE_FOLDER;
    String path;

    public FileTool(BasicActivity activity, String path) {
        this.activity = activity;
        this.path = path;
        BASE_FOLDER = activity.getFilesDir().getPath() + File.separator;
//        BASE_FOLDER = Environment.getExternalStorageDirectory() + "/" + activity.getPackageName() + "/";
    }

    /**
     * write string to SDcard, DEFAULT file name:"log_times.txt"
     *
     * @param message
     * @param fileName
     */
    public void writeStringToFileInSDcard(String fileName, String message, boolean append) {
        if (TextUtils.isEmpty(fileName))
            fileName = "json_" + System.currentTimeMillis() + ".txt";
        try {
            File file = new File(BASE_FOLDER + fileName);
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


    public void storeImage(Bitmap bitmap, String fileName) {
        if (bitmap == null || TextUtils.isEmpty(fileName)) return;
        MLog.w(this, "save image file to storage --> " + fileName);
        File pictureFile = getOutputMediaFile(fileName);
        if (pictureFile == null) {
            MLog.d(this,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        MLog.i(TAG, "save path=" + pictureFile.getPath());
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public void storeImage(List<Bitmap> bitmapList, List<String> fileNameList) {
        if (bitmapList == null || fileNameList == null) return;
        for (int i = 0; i < bitmapList.size(); i++) {
            storeImage(bitmapList.get(i), fileNameList.get(i));
        }
    }

    public List<String> getFileList() {
        File file = new File(BASE_FOLDER + path);
        List<String> list = new ArrayList<String>();
        if (!file.exists()) return list;
        for (String s : file.list()) list.add(s);
        return list;
    }

    public void clearFile() {
        File folder = new File(BASE_FOLDER + path);
        if (!folder.exists()) return;
        if (folder.isDirectory()) {
            for (File child : folder.listFiles()) child.delete();
        }
        folder.delete();
    }

    public Bitmap getBitmapFromStorage(String fileName) {
        fileName = BASE_FOLDER + path + convertFileName(fileName);
        MLog.i(this, "dexter get bitmap from storage --> " + fileName);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(fileName, options);
    }

    public List<Bitmap> getBitmapListFromStorage(List<String> fileNameList) {
        List<Bitmap> list = new ArrayList<>();
        for (String name : fileNameList) list.add(getBitmapFromStorage(name));
        return list;
    }


    public String convertFileName(String fileName) {
        return Tools.toMD5(fileName.replace(",", "").replace("/", "").replace("?", "").replace(":", "").replace(".", "").replace("-", ""));
//        return Tools.toMD5(fileName.replace(",", "").replace("/", "").replace("?", "").replace(":", "")) + (fileName.contains(".png") ? ".png" : ".jpg");
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(String fileName) {
        File folder = new File(BASE_FOLDER + path);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                MLog.e(this, "can't mkdirs " + folder);
                return null;
            }
        }
        fileName = convertFileName(fileName);
        String f = folder.getPath() + File.separator + fileName;
        File mediaFile = new File(f);
        return mediaFile;
    }

    /**
     * 檢查帶入的圖片檔是否已不需要, 並進行刪除
     *
     * @param urlList
     */
    public void checkAndRemoveNoNeed(List<String> urlList) {
        if (urlList == null) return;
        List<String> hasList = getFileList();
        if (hasList == null) return;

        MLog.e(TAG, "path=" + BASE_FOLDER + path);

        List<String> tempList = new ArrayList<>();
        for (String url : urlList) tempList.add(convertFileName(url));

        for (String has : hasList) {
            if (tempList.contains(has)) continue;
            File file = new File(BASE_FOLDER + path + has);
            if (file.exists()) file.delete();
        }

        try {
            int ver = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;
            if (ver > 5) return;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String DD0 = Environment.getExternalStorageDirectory() + File.separator + activity.getPackageName();
        File DDF0 = new File(DD0);
        if (!DDF0.exists()) return;

        deleteFolder(DDF0);
    }

    private void deleteFolder(File file) {
        MLog.v(TAG, "delete " + file.getPath() );
        if (!file.exists()) return;
        if (!file.isDirectory()) {
            file.delete();
            return;
        }

        for (File f : file.listFiles()) {
            if (f.isDirectory()) deleteFolder(f);
            f.delete();
        }
        file.delete();
    }

}
