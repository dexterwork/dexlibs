package com.skyking.skylib.connect;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skyking.skylib.basic.BasicActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Dexter on 2015/7/16.
 */
public   class HttpGetPost extends AsyncTask<Void, Void, Object> {
    String url;
    HttpType httpType;
    HttpGet httpGet = null;
    HttpPost httpPost = null;

    Class<?> entity;


    BasicActivity activity;



    public enum HttpType {GET, POST}

    public HttpGetPost(BasicActivity activity, String url, Class<?> entity, HttpType httpType) {
        this.activity = activity;
        this.url = url;
        this.httpType = httpType;
        this.entity = entity;
    }

    public HttpGetPost(BasicActivity activity, String url, Class<?> entity){
        this(activity,url,entity, HttpType.POST);
    }




    @Override
    protected Object doInBackground(Void... params) {
        InputStream is = null;
        Object object = null;
        setHttp();
        try {
            HttpEntity httpEntity = getResponse().getEntity();
            if (httpEntity == null) return null;
            String result = EntityUtils.toString(httpEntity);
            is = new ByteArrayInputStream(result.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Reader reader = new InputStreamReader(is, "UTF-8");
            Gson gson = new GsonBuilder().create();
            object = gson.fromJson(reader, entity);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    private HttpResponse getResponse() {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            switch (httpType) {
                case GET:
                    return httpClient.execute(httpGet);
                case POST:
                    return httpClient.execute(httpPost);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setHttp() {
        switch (httpType) {
            case GET:
                httpGet = new HttpGet(url);
                break;
            case POST:
                httpPost = new HttpPost(url);
                break;
        }
    }


//    public  void response(Object o){}
//
//
//    @Override
//    protected void onPostExecute(Object o) {
//        super.onPostExecute(o);
//        response(o);
//    }

}
