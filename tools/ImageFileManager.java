package object;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import tools.Tools;

/**
 * Created by SkykingAndroid on 2016/11/3.
 */

public class ImageFileManager {

    Activity activity;
    Tools tools;

    List<String> fileNameList;

    final String FOLDER = "GoodTW";
    File folder;

    public ImageFileManager(Activity activity) {
        this.activity = activity;
        this.tools = new Tools();
        getList();
    }

    private void getList() {
        folder = new File(tools.getSDcardPath() + FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
            return;
        }
        fileNameList = Arrays.asList(folder.list());
    }


    public boolean hasFile(String url) {
        if (fileNameList == null) return false;
        return fileNameList.contains(convert(url));
    }


    public List<String> getFileNameList() {
        return fileNameList;
    }


    private String convert(String url) {
        return tools.toMD5(url.replace("/", "").replace(".", "")) + ".png";
    }

    public void saveImageFile(String url, Bitmap bitmap) {
        if (folder == null) folder = new File(tools.getSDcardPath() + FOLDER);
        if (!folder.exists()) {
            if (!folder.mkdir()) return;
        }
        File file = new File(folder.getPath() + File.separator + convert(url));
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public Bitmap getBitmap(String url) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(folder.getPath() + File.separator + convert(url), options);
    }
}
