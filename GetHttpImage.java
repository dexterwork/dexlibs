package dexter.studio.rsl.objects;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import dexter.studio.rsl.R;
import dexter.studio.rsl.tools.MLog;

/**
 * Created by Dexter on 2016/3/21.
 */
public class GetHttpImage extends AsyncTask<Void, Void, List<Bitmap>> {
    ProgressDialog dialog;

    Context context;
    List<String> urls;
    IGetHttpImage iGetHttpImage;

    final String TAG = "GetHttpImage";

    public GetHttpImage(Context context, List<String> urls, IGetHttpImage iGetHttpImage) {
        this.context = context;
        this.urls = urls;
        this.iGetHttpImage = iGetHttpImage;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage(context.getString(R.string.please_wait));
        dialog.show();
    }

    @Override
    protected List<Bitmap> doInBackground(Void... params) {
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        for (String url : urls) {
            bitmaps.add(getUrlBitmap(url));
            MLog.i(TAG, "dexter get image url=" + url);
        }
        return bitmaps;
    }

    @Override
    protected void onPostExecute(List<Bitmap> bitmaps) {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        iGetHttpImage.onGetHttpImageResponse(bitmaps);
    }


    public Bitmap getUrlBitmap(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpConn.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface IGetHttpImage {
        void onGetHttpImageResponse(List<Bitmap> bitmaps);
    }


}
