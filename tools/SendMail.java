package com.awant.lion.tools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by 旺比優05 on 2015/7/27.
 */
public class SendMail {
	
	 public static void sendCustomerEmail(Activity activity) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:service@skyking.com.tw"));
        data.putExtra(Intent.EXTRA_SUBJECT, "台灣好客服(Android)");
//        data.putExtra(Intent.EXTRA_TEXT, "這是內容");
        activity.startActivity(data);
    }
	

    public static void sent(Activity activity, String message) {
        String[] TO = {"dexterwork2010@gmail.com"};
//        String[] CC = {""};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "awant sent log -- " + CalendarTools.getStrTime());
        emailIntent.putExtra(Intent.EXTRA_TEXT, "catch logs=================\n\n" + message);

        try {
            activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
