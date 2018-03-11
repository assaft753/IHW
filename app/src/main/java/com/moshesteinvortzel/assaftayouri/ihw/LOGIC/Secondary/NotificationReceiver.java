package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Activities.LoginActivity;
import com.moshesteinvortzel.assaftayouri.ihw.R;

public class NotificationReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent loginIntent = new Intent(context, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, loginIntent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, intent.getExtras().getString("channel"))
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(intent.getExtras().getString("title"))
                .setContentText(intent.getExtras().getString("content"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(intent.getExtras().getInt("id"), mBuilder.build());
    }
}
