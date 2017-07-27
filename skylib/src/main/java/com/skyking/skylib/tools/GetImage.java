package com.skyking.skylib.tools;

import android.graphics.Bitmap;
import android.os.Handler;

import com.skyking.skylib.basic.BasicActivity;
import com.skyking.skylib.connect.GetHttpImage;

import java.util.List;

/**
 * Created by SkykingAndroid on 2017/4/27.
 */

public class GetImage {
    final String TAG = "GetImage";
    BasicActivity activity;
    FileTool fileTool;



    public GetImage(BasicActivity activity, String path) {
        this.activity = activity;
        fileTool = new FileTool(activity, path);
    }

    public void getImage(String url) {
        List<String> list = fileTool.getFileList();
        String fileName = fileTool.convertFileName(url);
        if (list.contains(fileName)) {
            Bitmap bitmap = fileTool.getBitmapFromStorage(url);
            if (bitmap != null) {
                MLog.v(TAG, "get image from storage=" + url);
                if (getImageListener != null) getImageListener.done(url, bitmap);
            } else {
                api(url);
            }
        } else {
            api(url);
        }
    }

    private void api(final String url) {
        new GetHttpImage(activity, url) {
            @Override
            public void download(String url, Bitmap bitmap) {
                if (bitmap != null) {
                    fileTool.storeImage(bitmap, url);
                }
                MLog.v(TAG, "get image from api=" + url);
                if (getImageListener != null) getImageListener.done(url, bitmap);
            }
        }.execute();

    }

    private GetImageListener getImageListener;

    public void setGetImageListener(GetImageListener getImageListener) {
        this.getImageListener = getImageListener;
    }

    public boolean hasListener() {
        return getImageListener != null;
    }

    public interface GetImageListener {
        void done(String url, Bitmap bitmap);
    }

}
