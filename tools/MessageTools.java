package com.awant.lion.objects;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dexter on 2015/10/7.
 */
public class MessageTools {

    public static void makeRedToast(Context context, String message, int showTime) {
        Toast toast = Toast.makeText(context, message, showTime);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setBackgroundColor(Color.RED);
        textView.setPadding(10, 10, 10, 10);
        toast.getView().setBackgroundColor(Color.RED);
        toast.show();
    }
}
