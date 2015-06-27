package com.awant.lion.tools;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * Created by dexter on 2015/6/16.
 */
public class WriteFile {
    public WriteFile() {
    }

    //    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" >

    String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;//擷取手機的/sdcard路徑並給定檔案名稱

    public void writeTextToFile(String str, String fileName) {

        if (TextUtils.isEmpty(fileName))
            fileName = "_output_" + CalendarTools.getStrTime() + ".txt";
        try {
            //建立FileOutputStream物件，路徑為SD卡中的output.txt
            FileOutputStream output = new FileOutputStream(filepath + fileName);
            output.write(str.getBytes());  //write()寫入字串，並將字串以byte形式儲存。
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
        writeTextToFile(str, fileName);
    }


}
