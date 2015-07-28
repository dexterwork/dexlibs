package studio.dexter.test;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by Dexter on 2015/7/27.
 */
public class FileTools {
    //    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    public FileTools() {
    }


    public String getCurrentTimeString() {
        return CalendarTools.getStrTime();
    }

    public String getSDcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/";
    }


    /**
     * write string to SDcard, DEFAULT file name:"log_times.txt"
     *
     * @param message
     * @param fileName
     */
    public void writeStringToFileInSDcard(String fileName, String message, boolean append) {
        if (TextUtils.isEmpty(fileName))
            fileName = "log_" + getCurrentTimeString() + ".txt";
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

    /**
     * write strings Map<String, String> to SDcard, default file name:"log_times.txt"
     *
     * @param map
     * @param fileName
     */
    public void writeStringToFileInSDcard(String fileName, Map<String, String> map, boolean append) {
        String str = "";
        Iterator it = map.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = (String) it.next();
            str += key + "=" + map.get(key);
            if (it.hasNext()) str += "\n";
        }
        writeStringToFileInSDcard(fileName, str, append);
    }

    /**
     * write strings ArrayList<String> to SDcard, default file name:"log_times.txt"
     *
     * @param stringArray
     * @param filename
     */
    public void writeStringToFileInSDcard(String filename, ArrayList<String> stringArray, boolean append) {
        String str = "";
        Iterator it = stringArray.iterator();
        while (it.hasNext()) {
            str += it.next();
            if (it.hasNext()) str += "\n";
        }
        writeStringToFileInSDcard(filename, str, append);
    }

    /**
     * read string from project private file folder.
     * PATH=/data/data/project_path/files/filename
     *
     * @param context
     * @param filename
     * @return
     */
    public String readStringFromFileInProject(Context context, String filename) {
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput(filename);
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
     * write string to project private file folder.
     * PATH=/data/data/project_path/files/filename
     *
     * @param context
     * @param filename
     * @param writeString
     */
    public void writeStringToFileInProject(Context context, String filename, String writeString, boolean append) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, append ? Context.MODE_APPEND : Context.MODE_PRIVATE));
            outputStreamWriter.write(writeString);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read file from SD card.
     * @param filename
     * @return
     */
    public String readStringFromFileInSDcard(String filename) {
        if (TextUtils.isEmpty(filename)) return null;
        String read = "";
        BufferedReader buffer = null;
        try {
            File file = new File(getSDcardPath() + filename);
            if (!file.exists()) return null;
            FileInputStream input = new FileInputStream(file);
            buffer = new BufferedReader(new InputStreamReader(input));
            String row = null;
            while ((row = buffer.readLine()) != null) {
                read += row + "\n";
                row = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return read;
    }


}
