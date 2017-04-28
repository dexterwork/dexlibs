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

/**
 * Created by SkykingAndroid on 2017/2/13.
 */

public abstract class GetHttpImage extends AsyncTask<Void, Void, Bitmap> {

    Context context;
    String url;

    public GetHttpImage(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return getUrlBitmap(url);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        download(url,bitmap);
    }

    public abstract void download(String url,Bitmap bitmap);

    private Bitmap getUrlBitmap(String imageUrl) {
        try {

            URL url = new URL(imageUrl);
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
//            httpConn.setRequestProperty("Charsert", "utf-8");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpConn.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
