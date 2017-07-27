package com.skyking.skylib.objects;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.skyking.skylib.basic.BasicActivity;
import com.skyking.skylib.basic.BasicThread;
import com.skyking.skylib.entitys.AdsEntity;
import com.skyking.skylib.tools.MLog;
import com.skyking.skylib.tools.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 內部輪播廣告
 * Created by SkykingAndroid on 2017/2/14.
 */

public class InnerAds {
    final String TAG = InnerAds.class.getSimpleName();
    BasicActivity activity;
    ImageView imageView;

    HashMap<String, AdsEntity.ImgBundle> map;
    List<String> keys;

    int index;

    final int ACTION_NEXT = 300;

    final int DELAY = 4000;

    Handler handler;

    public InnerAds(final BasicActivity activity, final ImageView imageView) {
        this.activity = activity;
        this.imageView = imageView;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                int action = bundle.getInt(Cons.KEY_ACTION);
                switch (action) {
                    case ACTION_NEXT:

                        index++;
                        if (index >= map.size()) index = 0;
                        String key = keys.get(index);
                        Bitmap bitmap = map.get(key).bitmap;
                        imageView.setImageBitmap(bitmap);


                        break;
                }
            }
        };

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == -1 || index >= map.size()) return;
                try {
                    String url = map.get(index).url;
                    if (TextUtils.isEmpty(url)) return;
                    new Tools().openUrlPage(activity, url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void set(HashMap<String, AdsEntity.ImgBundle> map) {
        if (map == null) return;
        if (map.size() == 0) return;
        this.map = map;

        for (AdsEntity.ImgBundle b : map.values()) {
            MLog.v(TAG, "ads=" + b.url);
        }

        index = -1;
        keys = new ArrayList<>(map.keySet());
        if (mThread != null && mThread.isAlive()) {
            mThread.close();
        }
        mThread = new MThread();
        mThread.start();
    }

    MThread mThread;

    class MThread extends BasicThread {
        @Override
        public void run() {
            while (flag) {
                next();
                toSleep(DELAY);
            }
        }
    }

    private void next() {
        Bundle bundle = new Bundle();
        bundle.putInt(Cons.KEY_ACTION, ACTION_NEXT);
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public boolean isHide() {
        return imageView.getVisibility() != View.VISIBLE;
    }

    public void show() {
        imageView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        imageView.setVisibility(View.GONE);
    }

    public void dismiss() {
        if (mThread != null && mThread.isAlive()) {
            mThread.close();
        }
    }


}
