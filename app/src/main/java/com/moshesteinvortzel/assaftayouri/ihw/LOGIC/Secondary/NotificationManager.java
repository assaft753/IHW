package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.NotificationReceiver;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public class NotificationManager
{
    private int pushId;
    private final String CHANNELIS = "IHW";

    public NotificationManager(int pushId)
    {
        this.pushId = pushId;
    }

    public NotificationManager()
    {
        this.pushId = 1;
    }

    public void AddNotification(int pushId, String title, String content, Context context, Calendar calendar, int notify)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(calendar.getTimeInMillis());
        calendar1.set(Calendar.DAY_OF_MONTH, calendar1.get(Calendar.DAY_OF_MONTH) - notify);
        Intent myIntent = new Intent(context, NotificationReceiver.class);
        myIntent.putExtra("title", title);
        myIntent.putExtra("content", content);
        myIntent.putExtra("channel", CHANNELIS);
        myIntent.putExtra("id", pushId);
        System.out.println("Add Push " + pushId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pushId, myIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);
    }

    public void setPushId(int pushId)
    {
        this.pushId = pushId;
    }

    public void CancelNotification(int pushId, Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        System.out.println("Delete Push " + pushId);
        Intent myIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pushId, myIntent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public int UpdateNotification(int pushId, String title, String content, Calendar calendar, Context context, int notify)
    {
        CancelNotification(pushId, context);
        int newPushId = getPushId();
        AddNotification(newPushId, title, content, context, calendar, notify);
        return newPushId;
    }

    public int getPushId()
    {
        return pushId;
    }

    public int GeneratePushId()
    {
        this.pushId++;
        return pushId - 1;
    }
}
