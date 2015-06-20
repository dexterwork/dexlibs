package com.awant.lion.cloud_list;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.awant.lion.R;
import com.awant.lion.RecipientInformation;
import com.awant.lion.tools.Constant;
import com.awant.lion.tools.MLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 旺比優05 on 2015/6/12.
 */
public class CloudAreaListHttp extends AsyncTask<Void, Void, ArrayList<CloudTakeAreaBundle>> {
    RecipientInformation activity;
    int defaultSelect;

    public CloudAreaListHttp(RecipientInformation activity,int defaultSelect) {
        this.activity = activity;
        this.defaultSelect=defaultSelect;
    }

    @Override
    protected ArrayList<CloudTakeAreaBundle> doInBackground(Void... params) {
        MLog.i(this, "dex doInBackground");

        String result = "";
        InputStream in = null;

        // HTTP Get
        try {
            URL url = new URL(Constant.CLOUD_TAKE_AREA_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = urlConnection.getInputStream();
            if (in == null) return null;
            Writer writer = new StringWriter();
            char[] buffer = new char[2048];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                in.close();
            }
            result = writer.toString();

//                MLog.i(this, "dex http return data:" + resultToDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //轉換文字為JSONArray
        ArrayList<CloudTakeAreaBundle> list = new ArrayList<CloudTakeAreaBundle>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                CloudTakeAreaBundle bundle = new CloudTakeAreaBundle();
                bundle.setUid(data.getString("uid"));
                bundle.setNo(data.getString("no"));
                bundle.setName(data.getString("name"));
                list.add(bundle);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    protected void onPostExecute(final ArrayList<CloudTakeAreaBundle> result) {
        activity.setCloudAreaAdapter(result,defaultSelect);
    }


}
