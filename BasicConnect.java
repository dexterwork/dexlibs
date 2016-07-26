package dexter.studio.rsl.basic;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import dexter.studio.rsl.tools.MLog;
import dexter.studio.rsl.tools.connect.GsonRequest;
import dexter.studio.rsl.tools.connect.SuperVolleyConnect;

/**
 * Created by Dexter on 2016/3/4.
 */
public abstract class BasicConnect<D> extends SuperVolleyConnect implements Response.Listener<D>, Response.ErrorListener {

    String host;
    String url;
    Object bundle;

    public BasicConnect(String host, String url, Object bundle) {
        this.host = host;
        this.url = url;
        this.bundle = bundle;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        MLog.e(this, "dexter onErrorResponse=" + error.getMessage());
    }


    @Override
    public void request(Object user) {
        String urls = Uri.parse(host + url).buildUpon().build().toString();

        String json = new Gson().toJson(bundle);
        MLog.i(this, "dexter json=" + json);
        Type messageType = new TypeToken<D>() {
        }.getType();
        GsonRequest<D> request = new GsonRequest<D>(
                Request.Method.POST, urls, json, messageType, this, this);
        request.setTag(user);
        performRequest(request);
    }


}
