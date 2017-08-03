package com.skyking.skylib.connect;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by SkykingAndroid on 2017-08-03.
 */

public class OkHttp extends AsyncTask<Void, Void, Object> {

    final String TAG = getClass().getSimpleName();
    String url;

    Class<?> entity;

    public OkHttp(String url, Class<?> entity) {
        this.url = url;
        this.entity = entity;
    }

    @Override
    protected Object doInBackground(Void... params) {

        Request request = new Request.Builder().url(url).build();
        final OkHttpClient client = new OkHttpClient();
        try {
            final Response response = client.newCall(request).execute();
            try {
                InputStream is = response.body().byteStream();
                if (is == null) return null;
                Reader reader = new InputStreamReader(is, "UTF-8");
                Gson gson = new GsonBuilder().create();
                Object object = gson.fromJson(reader, entity);
                is.close();
                return object;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
