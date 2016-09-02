package basic.connect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.kingspirit.android.tools.MLog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import basic.BasicActivity;
import basic.BasicThread;


/**
 * Created by SkykingAndroid on 2016/7/29.
 */
public abstract class BitmapHttp {
    final String TAG = "BitmapHttp";
    BasicActivity activity;
    List<String> urlList;
    String url;

    List<Bitmap> bitmaps;

    public BitmapHttp(BasicActivity activity, String url) {
        this.activity = activity;
        this.url = url;
        get();
    }

    public BitmapHttp(BasicActivity activity, List<String> urlList) {
        this.activity = activity;
        this.urlList = urlList;
        getList();
    }

    private void get() {
        if (TextUtils.isEmpty(url)) {
            done(null);
            return;
        }
        urlList = new ArrayList<>();
        urlList.add(url);
        getList();

    }



    private void getList() {
        if (urlList == null) {
            done(null);
            return;
        }
        if (urlList.size() == 0) {
            done(null);
            return;
        }

        bitmaps = new ArrayList<Bitmap>();
        setHandler();


        mThread = new MThread();
        mThread.start();
    }

    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                MLog.d(TAG, "dexter getList bitmap done");
                done(bitmaps);
            }
        };
    }


    MThread mThread;

    class MThread extends BasicThread {


        @Override
        public void run() {
            index = 0;
            while (index < urlList.size()) {
//                MLog.d(TAG, "dexter run index=" + index);
                mAsync = new MAsync();
                mAsync.execute();
                toWait();
                index++;
            }
            handler.sendEmptyMessage(0);
        }
    }

    Handler handler;

    int index;
    MAsync mAsync;

    class MAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            bitmaps.add(getBitmapFromURL(urlList.get(index)));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mThread.toNotify();
            super.onPostExecute(aVoid);
//            MLog.d(TAG, "dexter onPostExecute");

        }
    }

    private Bitmap getBitmapFromURL(final String src) {
        MLog.i(TAG, "dexter getBitmapFromURL " + src);
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            error();
            return null;
        }
    }


    public abstract void done(List<Bitmap> bitmaps);
    public abstract void error();


}
