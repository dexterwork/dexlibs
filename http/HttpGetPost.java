package basic.connect;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.ExceptionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Dexter on 2015/7/16.
 */
public class HttpGetPost extends AsyncTask<Void, Void, Object> {
    String url;
    HttpType httpType;
    //    Class<?> bundleClass;
    HttpGet httpGet = null;
    HttpPost httpPost = null;
    ProgressDialog progressDialog;

    Class<?> entity;

    final int KEY_SHOW = 100;
    final int KEY_CLOSE = 99;

    public enum HttpType {GET, POST}

    public HttpGetPost(String url, Class<?> entity, HttpType httpType) {
        this.url = url;
        this.httpType = httpType;
        this.entity = entity;
        initHandler();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case KEY_SHOW:
                        try {
                            if (progressDialog != null) progressDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case KEY_CLOSE:
                        try {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        handler.sendEmptyMessage(KEY_SHOW);
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
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            Reader reader = new InputStreamReader(is, "UTF-8");
            Gson gson = new GsonBuilder().create();
            object = gson.fromJson(reader, entity);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        afterGson(object);
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

    public void afterGson(Object object) {
    }

    public void afterPostExecute(Object o) {
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        handler.sendEmptyMessage(KEY_CLOSE);

        afterPostExecute(o);
    }

    Handler handler;
}
