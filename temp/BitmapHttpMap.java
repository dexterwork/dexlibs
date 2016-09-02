package basic.connect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.kingspirit.android.tools.MLog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import basic.BasicActivity;
import basic.BasicThread;


/**
 * Created by SkykingAndroid on 2016/7/29.
 */
public abstract class BitmapHttpMap {
    final String TAG = "BitmapHttp";
    BasicActivity activity;
    String url;
    HashMap<Integer, String> urlMap;

    HashMap<Integer, Bitmap> bitmapMap;

    List<Integer> stackList;


    public BitmapHttpMap(BasicActivity activity, HashMap<Integer, String> urlMap) {
        this.activity = activity;
        this.urlMap = urlMap;
        getList();
    }


    private void getList() {
        if (urlMap == null) {
            done(null);
            return;
        }
        if (urlMap.size() == 0) {
            done(null);
            return;
        }

        bitmapMap = new HashMap<>();
        setHandler();

        stackList = new ArrayList<>(urlMap.keySet());

        mThread = new MThread();
        mThread.start();
    }

    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                MLog.d(TAG, "dexter getList bitmap done");
                done(bitmapMap);
            }
        };
    }


    MThread mThread;

    class MThread extends BasicThread {


        @Override
        public void run() {
            while (stackList.size() > 0) {
                mAsync = new MAsync();
                mAsync.execute();
                toWait();
            }
            handler.sendEmptyMessage(0);
        }
    }

    Handler handler;

    MAsync mAsync;

    class MAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            int key = stackList.get(0);

            bitmapMap.put(key, getBitmapFromURL(urlMap.get(key)));
            stackList.remove(0);
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


    public abstract void done(HashMap<Integer, Bitmap> bitmapMap);

    public abstract void error();


}
