package studio.dexter.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import studio.dexter.exception_party.ExceptionLog;

/**
 * Created by Dexter on 2015/7/27.
 */
public class FileTools {
    public FileTools() {
        SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";//擷取手機的/sdcard路徑並給定檔案名稱
    }


    //    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    private final String SDCARD_PATH;

    public String getCurrentTimeString() {
        return CalendarTools.getStrTime();
    }

    public String getSDcardPath() {
        return SDCARD_PATH;
    }

    //TODO add append write methods (NOT over write).



    /**
     * write (OVER WRITE) string to SDcard, DEFAULT file name:"log_times.txt"
     *
     * @param message
     * @param fileName
     */
    public void writeStringToFileInSDcard(String message, String fileName) {
        if (TextUtils.isEmpty(fileName))
            fileName = "log_" + getCurrentTimeString() + ".txt";
        try {
            FileOutputStream output = new FileOutputStream(SDCARD_PATH + fileName);
            output.write(message.getBytes());  //write()寫入字串，並將字串以byte形式儲存。
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * write (OVER WRITE) strings Map<String, String> to SDcard, default file name:"log_times.txt"
     *
     * @param map
     * @param fileName
     */
    public void writeStringToFileInSDcard(Map<String, String> map, String fileName) {
        String str = "";
        Iterator it = map.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = (String) it.next();
            str += key + "=" + map.get(key);
            if (it.hasNext()) str += "\n";
        }
        writeStringToFileInSDcard(str, fileName);
    }

    /**
     * write (OVER WRITE) strings ArrayList<String> to SDcard, default file name:"log_times.txt"
     *
     * @param stringArray
     * @param filename
     */
    public void writeStringToFileInSDcard(ArrayList<String> stringArray, String filename) {
        String str = "";
        Iterator it = stringArray.iterator();
        while (it.hasNext()) {
            str += it.next();
            if (it.hasNext()) str += "\n";
        }
        writeStringToFileInSDcard(str, filename);
    }

    /**
     * read string from project private file folder.
     * PATH=/data/data/project_path/files/filename
     *
     * @param activity
     * @param filename
     * @return
     */
    public String readStringFromFileInProject(Activity activity, String filename) {
        InputStream inputStream = null;
        try {
            inputStream = activity.openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String receiveString = "";
        StringBuilder stringBuilder = new StringBuilder();

        try {
            while ((receiveString = bufferedReader.readLine()) != null) {
                if (!TextUtils.isEmpty(receiveString)) receiveString = "\n" + receiveString;
                stringBuilder.append(receiveString);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * write (OVER WRITE) string to project private file folder.
     * PATH=/data/data/project_path/files/filename
     *
     * @param activity
     * @param filename
     * @param writeString
     */
    public void writeStringToFileInProject(Activity activity, String filename, String writeString) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(activity.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(writeString);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO
    public String readStringFromFileInSDcard(Activity activity, String filename) {
        return null;
    }



}
