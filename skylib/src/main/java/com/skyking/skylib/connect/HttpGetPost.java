package com.skyking.skylib.connect;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skyking.skylib.basic.BasicActivity;
import com.skyking.skylib.tools.MLog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Dexter on 2015/7/16.
 */
public class HttpGetPost extends AsyncTask<Void, Void, Object> {
    final String TAG = HttpGetPost.class.getSimpleName();
    String url;
    HttpType httpType;
//    HttpGet httpGet = null;
//    HttpPost httpPost = null;

    Class<?> entity;


    BasicActivity activity;


    public enum HttpType {GET, POST}

//    public HttpGetPost(BasicActivity activity, String url, Class<?> entity, HttpType httpType) {
//        this.activity = activity;
//        this.url = url;
//        this.httpType = httpType;
//        this.entity = entity;
//    }

    public HttpGetPost(BasicActivity activity, String url, Class<?> entity) {
        this.activity = activity;
        this.url = url;
        this.entity = entity;
//        this(activity, url, entity, HttpType.POST);
    }


    @Override
    protected Object doInBackground(Void... params) {
        InputStream is = null;
        Object object = null;
//        try {
//            HttpEntity httpEntity = getResponse().getEntity();
//            if (httpEntity == null) return null;
//            String result = EntityUtils.toString(httpEntity);
//            is = new ByteArrayInputStream(result.getBytes("UTF-8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }

        try {
            is = connect(url);
            if (is == null) return null;
            Reader reader = new InputStreamReader(is, "UTF-8");
            Gson gson = new GsonBuilder().create();
            object = gson.fromJson(reader, entity);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

//    public String readFully(InputStream entityResponse) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int length = 0;
//        while ((length = entityResponse.read(buffer)) != -1) {
//            baos.write(buffer, 0, length);
//        }
//        return baos.toString();
//    }

    private InputStream connect(String strUrl) {
        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        HttpURLConnection uc = null;
        try {
            uc = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        uc.setConnectTimeout(5000);
        uc.setReadTimeout(5000); // 設定timeout時間
        try {
            MLog.w(TAG, "connect......");
            uc.connect(); // 開始連線
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        int status = 0;
        try {

            status = uc.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        uc.disconnect();
        if (status == HttpURLConnection.HTTP_OK) {
            try {

                return url.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private HttpResponse getResponse() {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            switch (httpType) {
                case GET:
                    return httpClient.execute(new HttpGet(url));
                case POST:
                    return httpClient.execute(new HttpPost(url));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
