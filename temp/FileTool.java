package tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SkykingAndroid on 2016/8/12.
 */
public class FileTool {

    Context context;

    public static final String PATH_IMAGE_ADV_INNER = "/images/adv/inner";
    public static final String PATH_GCM_MESSAGE = "/message/gcm";
    final String BASE_FOLDER;

    String path;

    public FileTool(Context context, String path) {
        this.context = context;
        BASE_FOLDER = Environment.getExternalStorageDirectory() + "/skyking/";
        this.path = path;
    }

    public void storeGcmMessage(String message) {
        File folder = new File(BASE_FOLDER + PATH_GCM_MESSAGE);
        if (!folder.exists()) folder.mkdirs();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_hh_mm_ss");
        String time = format.format(calendar.getTime());
        File file = new File(folder, "receive_gcm_" + time + ".txt");


    }

    public void storeImage(Bitmap bitmapList, String fileName) {
        fileName = convertFileName(fileName);
        MLog.w(this, "dexter save image file to storage --> " + fileName);
        File pictureFile = getOutputMediaFile(fileName);
        if (pictureFile == null) {
            MLog.d(this,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmapList.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public void storeImage(List<Bitmap> bitmapList, List<String> fileNameList) {
        for (int i = 0; i < bitmapList.size(); i++) {
            storeImage(bitmapList.get(i), fileNameList.get(i));
        }
    }

    public List<String> getFileList() {
        File file = new File(BASE_FOLDER + path);
        if (!file.exists()) return null;
        return Arrays.asList(file.list());
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
        fileName = BASE_FOLDER + path + "/" + fileName;
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
        return fileName.replace(",", "").replace("/", "").replace("?", "");
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(String fileName) {
        File folder = new File(BASE_FOLDER + path);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                MLog.e(this, "dexter can't mkdirs " + folder);
                return null;
            }
        }
        fileName = convertFileName(fileName);
        String f = folder.getPath() + File.separator + fileName;
        File mediaFile = new File(f);
        return mediaFile;
    }
}
