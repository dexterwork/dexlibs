package com.awant.lion.tools;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by dexter on 2015/6/16.
 */
public class WriteFile {
    public WriteFile() {
    }

    //    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" >

    static String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;//擷取手機的/sdcard路徑並給定檔案名稱

    public void writeTextToFile(String message, String fileName) {

        if (TextUtils.isEmpty(fileName))
            fileName = "awant_log_" + CalendarTools.getStrTime() + ".txt";
        try {
            //建立FileOutputStream物件，路徑為SD卡中的output.txt
            FileOutputStream output = new FileOutputStream(filepath + fileName);
            output.write(message.getBytes());  //write()寫入字串，並將字串以byte形式儲存。
            output.close();
            MLog.w(this, "dex write file to SDcard:" + filepath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeTextToFile(Map<String, ?> map, String fileName) {
        String str = "";
        for (String key : map.keySet()) {
            str += "key=" + key + " value=" + map.get(key) + "\n";
        }
        MLog.w(this, "dex commit string=" + str);
        writeTextToFile(str, fileName);
    }

    public static String newLineWrite(Object obj, String message) {
        String writeString="";
//        MLog.d(obj.getClass().getSimpleName(), "write file string:" + message);
        String filename = filepath + "awant_log.txt";
        String writeTime = CalendarTools.getStrTime() + "----------------------------\r\n";


        try {

            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String temp = br.readLine();
            String readData = "";
            while (!TextUtils.isEmpty(temp)) {
                readData += temp;
                temp = br.readLine();
            }
            readData += temp;
            br.close();
            fr.close();




            FileWriter fw = new FileWriter(filename, false);
            BufferedWriter bw = new BufferedWriter(fw); //將BufferedWeiter與FileWrite物件做連結
            if(!TextUtils.isEmpty(readData)){
                writeString+=readData+"\r\n-\r\n-\r\n" + writeTime + message;
            }else{
                writeString+=writeTime + message;
            }
            bw.write(writeString);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writeString;

    }


}
