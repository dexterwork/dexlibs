package com.gtsmarthome.tw.smarthome.core.connect;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class GsonRequest<T> extends Request<T> {
    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private String PROTOCOL_CONTENT_TYPE = "application/json";

    private final Gson mGson;
    private final Type mClazz;
    private final Response.Listener<T> mListener;
    private String mRequestBody;
    private Map<String, String> headerValues = new HashMap<String, String>();

    public GsonRequest(int method, String url, Type clazz,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mClazz = clazz;
        this.mListener = listener;
        initHeaders();
        mGson = new Gson();
    }

    public GsonRequest(int method, String url, String jsonString, Type clazz,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, url, clazz, listener, errorListener);
        this.mRequestBody = jsonString;
        Log.v("horton_see",jsonString);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headerValues;
    }

    public void addHeader(String key, String value) {
        headerValues.put(key, value);
    }

    private void initHeaders() {
        /*addHeader("X-API-APICode", "CI02");
        addHeader("X-API-APIKEY", "460AD6F3-8216-469F-9B1C-52CFFA5D812C");
        addHeader("Accept", "application/json");
        addHeader("X-API-CompanyID", "ammy");*/
        addHeader("User-Agent" ,"ApacerCloud User-agent");
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = convertResponseToString(response);
            Log.v("horton", "json :  " + json);
            return Response.success((T) mGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.w("1","1");
            return Response.error(new ParseError(new GsonRequestException(e)));
        } catch (JsonSyntaxException e) {
            Log.w("2","2");
            return Response.error(new ParseError(new GsonRequestException(e)));
        } catch (IOException e) {
            Log.w("3","3");
            return Response.error(new ParseError(new GsonRequestException(e)));
        }
    }

    private String convertResponseToString(NetworkResponse response)
            throws IOException {
        String encoding = response.headers.get("Content-Encoding");
        if (encoding == null) {
            return new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
        }
        BufferedReader reader = null;
        try {
            reader = generateBufferedReader(new ByteArrayInputStream(
                    response.data), encoding);
            return readBufferedString(reader);
        } finally {
            reader.close();
        }
    }

    private String readBufferedString(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String stringHolder;
        while ((stringHolder = reader.readLine()) != null) {
            stringBuilder.append(stringHolder);
        }
        return stringBuilder.toString();
    }

    private BufferedReader generateBufferedReader(InputStream inputStream,
                                                  String encodingType) throws IOException {
        if (encodingType.equals("gzip")) {
            return new BufferedReader(new InputStreamReader(
                    new GZIPInputStream(inputStream)));
        }
        if (encodingType.equals("deflate")) {
            return new BufferedReader(new InputStreamReader(
                    new InflaterInputStream(inputStream)));
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * @deprecated Use {@link #getBodyContentType()}.
     */
    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    /**
     * @deprecated Use {@link #getBody()}.
     */
    @Override
    public byte[] getPostBody() {
        return getBody();
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    public void setBodyContentType(String bodyContentType) {
        PROTOCOL_CONTENT_TYPE = bodyContentType;
    }

    @Override
    public byte[] getBody() {
        try {
            Log.v("mRequestBody",mRequestBody);
            return mRequestBody == null ? null : mRequestBody
                    .getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog
                    .wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

}