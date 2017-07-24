package com.skyking.skylib.objects;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.skyking.skylib.R;
import com.skyking.skylib.basic.BasicActivity;
import com.skyking.skylib.bundles.AdsBundle;
import com.skyking.skylib.bundles.ChannelBundle;
import com.skyking.skylib.connect.GetHttpImage;
import com.skyking.skylib.connect.Http;
import com.skyking.skylib.bundles.NoticeBundle;
import com.skyking.skylib.entitys.AdsEntity;
import com.skyking.skylib.entitys.BasicEntity;
import com.skyking.skylib.entitys.Channel;
import com.skyking.skylib.entitys.ChannelEntity;
import com.skyking.skylib.entitys.Group;
import com.skyking.skylib.entitys.NoticeEntity;
import com.skyking.skylib.tools.MLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SkykingAndroid on 2017/2/13.
 */

public class SkyData {

    private final String TAG = SkyData.class.getSimpleName();

    public static List<Group> groupList;
    public static NoticeEntity noticeEntity;
    public static AdsEntity adsEntity;

    public static String endDate;


    private BasicActivity activity;

    public static List<Integer> adultList;//成人台的頻道ID (從我的常用頻道點擊也較容易判斷是否為成人頻道

    public SkyData(BasicActivity activity) {
        this.activity = activity;
        initHandler();
    }

    public interface SkyDataListener {
        void done();
    }

    public static void reset() {
        groupList = null;
        adultList = null;
    }

    SkyDataListener skyDataListener;

    public static boolean hasData() {
        if (groupList == null) return false;
        if (noticeEntity == null) return false;
        if (!noticeEntity.hasData()) return false;
        if (adsEntity == null) return false;
        if (!adsEntity.hasData()) return false;
        if (adIndexBitmapMap == null) return false;
        return true;
    }

    public void notData(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(TextUtils.isEmpty(message) ? activity.getString(R.string.not_get_channel_data) : message);
        builder.setCancelable(false);
        builder.setPositiveButton(activity.getString(R.string.know), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        builder.show();
    }

    public void getData(SkyDataListener skyDataListener, String key) {
        this.skyDataListener = skyDataListener;
        if (groupList == null) {
            ChannelBundle cb = new ChannelBundle();
            if (!TextUtils.isEmpty(key)) cb.key = key;
            adultList = new ArrayList<>();
            new Http<ChannelEntity>(activity, cb, ChannelEntity.class) {
                @Override
                public void onError() {
                    MLog.w(TAG,"the fucking channel ERROR");
                }

                @Override
                public void get(ChannelEntity channelEntity) {
                    if (!check(channelEntity)) return;
                    MLog.v(TAG, "get=" + new Gson().toJson(channelEntity));

                    SkyData.groupList = channelEntity.data.get(0).Group;
                    for (Group g : SkyData.groupList) {
                        if (g.islock == 1) {
                            for (Channel c : g.channels) adultList.add(c.id);
                        }
//                        if(g.name.contains("廣播"))MLog.i(TAG,"廣播="+new Gson().toJson(g));
                    }
                    getNotice();
                }
            }.exec();
        } else {
            getNotice();
        }
    }

    private boolean check(BasicEntity entity) {
        if (entity == null) {
            activity.showProgress(false);
            notData("");
            return false;
        }
        if (!TextUtils.isEmpty(entity.errorMessage)) {
            activity.showProgress(false);
            notData(entity.errorMessage);
            return false;
        }
//        if (!entity.hasData()) {
//            activity.showProgress(false);
//            notData("");
//            return false;
//        }
        return true;
    }

    private void getNotice() {
        if (noticeEntity == null) noticeEntity = new NoticeEntity();
        if (!noticeEntity.hasData()) {
            new Http<NoticeEntity>(activity, new NoticeBundle(), NoticeEntity.class) {
                @Override
                public void onError() {
                    MLog.w(TAG,"the fucking notice ERROR");
                }

                @Override
                public void get(NoticeEntity noticeEntity) {
                    if (!check(noticeEntity)) return;

                    SkyData.noticeEntity = noticeEntity;
                    getAds();

                }
            }.exec();
        } else {
            getAds();
        }


    }

    private void getAds() {
        if (adsEntity == null) adsEntity = new AdsEntity();
        if (!adsEntity.hasData() || adIndexBitmapMap == null) {
            new Http<AdsEntity>(activity, new AdsBundle(), AdsEntity.class) {
                @Override
                public void onError() {
                    MLog.w(TAG,"the fucking ads ERROR");
                }

                @Override
                public void get(AdsEntity adsEntity) {
                    if (!check(adsEntity)) return;
                    SkyData.adsEntity = adsEntity;
                    getIndexBitmap();
                }
            }.exec();
        } else {
            getIndexBitmap();
        }
    }

    /**
     * 內部輪播廣告
     */
    public static HashMap<String, AdsEntity.ImgBundle> adIndexBitmapMap;

    private void getIndexBitmap() {

        adIndexBitmapMap = new HashMap<>();
        toGetAdImage(0);
    }

    Handler handler;


    class AdHttp extends GetHttpImage {
        AdsEntity.ImgBundle imgBundle;
        int index;
        boolean flag;
        Handler adHandler;

        public AdHttp(Context context, AdsEntity.ImgBundle imgBundle, int index) {
            super(context, imgBundle.img);
            this.imgBundle = imgBundle;
            this.index = index;
            adHandler = new Handler();
            MLog.v(TAG, "start get image");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!flag) cancel(true);
                }
            }, 3000);
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            MLog.w(TAG, "on cancelled---------------------");
            toGetAdImage(index);
        }


        @Override
        public void download(String url, Bitmap bitmap) {
            MLog.v(TAG, "get image done");
            flag = true;
            imgBundle.bitmap = bitmap;
            adIndexBitmapMap.put(imgBundle.img, imgBundle);
            if (adIndexBitmapMap.size() == adsEntity.data.get(0).index.size()) {
                skyDataListener.done();
            } else {
                toGetAdImage(index + 1);
            }

        }
    }

    final int ACTION_GET_ADS = 200;

    private void toGetAdImage(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(Cons.KEY_ACTION, ACTION_GET_ADS);
        bundle.putInt(Cons.KEY_VALUE1, index);
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                int action = bundle.getInt(Cons.KEY_ACTION);
                switch (action) {
                    case ACTION_GET_ADS:
                        int index = bundle.getInt(Cons.KEY_VALUE1);
                        if(adsEntity.data.get(0).index.size() <= index ){
                            skyDataListener.done();
                            return;
                        }
                        AdsEntity.ImgBundle imgBundle = adsEntity.data.get(0).index.get(index);
                        new AdHttp(activity, imgBundle, index).execute();
                        break;
                }
            }
        };
    }

}
