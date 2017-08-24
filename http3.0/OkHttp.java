package dexter.studio.dexlib.connect2;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import dexter.studio.dexlib.bundles.BasicBundle;
import dexter.studio.dexlib.tools.MLog;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by SkykingAndroid on 2017-08-03.
 */

public class OkHttp<T> extends AsyncTask<Void, Void, T> {

    final String TAG = getClass().getSimpleName();
    String url;

    Class<T> entity;
    BasicBundle bundle;

    public OkHttp(String url, BasicBundle bundle, Class<T> entity) {
        this.url = url;
        this.bundle = bundle;
        this.entity = entity;
    }

    //https://www.studytutorial.in/android-okhttp-post-and-get-request-tutorial
    @Override
    protected T doInBackground(Void... params) {
        MediaType mt = MediaType.parse("application/json");
        if (bundle == null) bundle = new BasicBundle();

        String json = new Gson().toJson(bundle);
        MLog.w(TAG, "request json=" + json);

        RequestBody body = RequestBody.create(mt, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final OkHttpClient client = new OkHttpClient();
        try {
            final Response response = client.newCall(request).execute();
            try {
                InputStream is = response.body().byteStream();
                if (is == null) return null;
                Reader reader = new InputStreamReader(is, "UTF-8");
                Gson gson = new GsonBuilder().create();
                T object = gson.fromJson(reader, entity);
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
