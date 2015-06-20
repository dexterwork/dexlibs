package com.awant.lion.tools;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 旺比優05 on 2015/6/9.
 */
public class HttpData extends AsyncTask<String, String, String> {

    /*
    call this...
    new HttpData().execute();
     */

    @Override
    protected String doInBackground(String... params) {

        String resultToDisplay = "";
        InputStream in = null;

        // HTTP Get
        try {
            URL url = new URL(Constant.CURRENT_TIME_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = urlConnection.getInputStream();
            if (in == null) return "";
            Writer writer = new StringWriter();
            char[] buffer = new char[2048];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(in, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                in.close();
            }
            resultToDisplay = writer.toString();
            MLog.i(this, "dex return date:" + resultToDisplay);
        } catch (Exception e) {
            return e.getMessage();
        }
        return resultToDisplay;
    }

    protected void onPostExecute(String result) {
        MLog.i(this, "dex return date:(onPostExecute) " + result);

    }

}
