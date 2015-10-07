package com.awant.lion.objects;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dexter on 2015/10/7.
 */
public class MessageTools {


    public static void makeRedToast(final Activity activity, final String message, final int showTime) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(activity, message, showTime);
                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                textView.setBackgroundColor(Color.RED);
                textView.setPadding(10, 10, 10, 10);
                toast.getView().setBackgroundColor(Color.RED);
                toast.show();
            }
        });
    }


}
