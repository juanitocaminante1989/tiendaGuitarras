package info.androidhive.slidingmenu.services;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import info.androidhive.slidingmenu.MainActivity;
import info.androidhive.slidingmenu.R;

/**
 * Created by Juan on 23/02/2017.
 */

public class NotificationBuilder {

    public static void Build(String contentText, String contentTitle, Context context, Class classs){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentText(contentText);
        final Intent emptyIntent = new Intent(context, classs);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentTitle(contentTitle);
        mBuilder.setSmallIcon(R.drawable.bajo);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(6, mBuilder.build());
    }

}
