package com.skyking.skylib.connect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Dexter on 2016/3/21.
 */
public abstract class GetHttpImageUrlMap extends AsyncTask<Void, Void, TreeMap<String, Bitmap>> {

    Context context;
    TreeMap<String, String> urlMap;//<url key, url>


    final String TAG = "GetHttpImage";

    public GetHttpImageUrlMap(Context context, TreeMap<String, String> urlMap) {
        this.context = context;
        this.urlMap = urlMap;
    }


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected TreeMap<String, Bitmap> doInBackground(Void... params) {
        TreeMap<String, Bitmap> bMap = new TreeMap<>();
        List<String> ids = new ArrayList<>(urlMap.keySet());

        try {
            for (String id : ids) {//img
                bMap.put(id, getUrlBitmap(id, urlMap.get(id)));
            }
        } catch (Exception e) {
            showLog(e.toString());
        }
        return bMap;
    }

    @Override
    protected void onPostExecute(TreeMap<String, Bitmap> map) {
        onGetHttpImageResponse(map);
    }


    public Bitmap getUrlBitmap(String id, String imageUrl) {
//        try {
//            imageUrl = new String(imageUrl.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        try {
//            Charset.forName("UTF-8").encode(imageUrl);
//            imageUrl = URLEncoder.encode(imageUrl, "UTF-8");

            URL url = new URL(imageUrl);
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
//            httpConn.setRequestProperty("Charsert", "utf-8");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpConn.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                onDownload(id, "download url:" + imageUrl);
                inputStream.close();
                return bitmap;
            }
        } catch (MalformedURLException e1) {
            showLog(e1.toString());
        } catch (IOException e) {
            showLog(e.toString());
        } catch (Exception e) {
            showLog(e.toString());
        }
        return null;
    }

    public abstract void onGetHttpImageResponse(TreeMap<String, Bitmap> bitmapMap);

    public void onDownload(String id, String msg) {
    }

    public void showLog(String log) {
    }

}
