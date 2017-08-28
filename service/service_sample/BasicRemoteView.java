package com.skyking.play_module;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by SkykingAndroid on 2017-08-14.
 */

public abstract class BasicRemoteView extends RemoteViews {


    protected Context context;

    public static int ID;


    public NotificationCompat.Builder mBuilder;
    int smallIconRes;


    public BasicRemoteView(Context context, int layoutId, int smallIconRes, int ID) {
        super(context.getPackageName(), layoutId);
        this.context = context;
        this.smallIconRes = smallIconRes;
        this.ID = ID;
    }


    public void startNotification(boolean onGoing) {
        mBuilder = new NotificationCompat.Builder(context).setSmallIcon(smallIconRes);
        mBuilder.setContent(this);
        setAction();
        mBuilder.setOngoing(onGoing);
        mBuilder.setAutoCancel(onGoing);
        getManager().notify(ID, mBuilder.build());
    }

    protected abstract void setAction();

    protected void setStartActivity(Class cls) {
        //        開啟app
        Intent resultIntent = new Intent(context, cls);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);
    }

    public void dismiss() {
        getManager().cancel(ID);
    }


    public NotificationManager getManager() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public void notifyChange(){
        if (mBuilder != null)  getManager().notify(ID, mBuilder.build());
    }
    public void setOnGoing(boolean onGoing) {
        if (mBuilder != null) mBuilder.setOngoing(onGoing);
    }

    protected void setAction(int btnId, String action) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        setOnClickPendingIntent(btnId, pendingIntent);
    }


}
