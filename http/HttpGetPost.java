package studio.dexter.http_module;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import java.nio.charset.StandardCharsets;

/**
 * Created by Dexter on 2015/7/16.
 */
public class HttpGetPost extends AsyncTask<Void, Void, Object> {
    String url;
    HttpType httpType;
    Class<?> bundleClass;
    HttpGet httpGet = null;
    HttpPost httpPost = null;

    public enum HttpType {GET, POST}

    public HttpGetPost(String url, Class<?> bundleClass, HttpType httpType) {
        this.url = url;
        this.httpType = httpType;
        this.bundleClass = bundleClass;
    }

    @SuppressLint("NewApi")
    @Override
    protected Object doInBackground(Void... params) {
        InputStream is = null;
        Object object = null;
        setHttp();
        try {
            HttpEntity httpEntity = getResponse().getEntity();
            String result = EntityUtils.toString(httpEntity);
            is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Reader reader = new InputStreamReader(is, "UTF-8");
            Gson gson = new GsonBuilder().create();
            object = gson.fromJson(reader, bundleClass);
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
        throw new RuntimeException("HttpResponse is null.");
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

}
