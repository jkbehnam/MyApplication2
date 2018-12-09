package com.example.behnam.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class set_alarm_notify {
    public static void set_alarm_repeat(Context context, int id,  Calendar calendar) {

        Intent intent = new Intent(context, bcr_notification.class);
        intent.putExtra("when", id);
        //intent.setAction(Intent.ACTION_MAIN);
        // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Log.d("SetAlarm",String.valueOf(id)+" | "+calendar.getTime().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }
    public static void set_alarm_repeat(Context context, int id,  Long time) {

        Intent intent = new Intent(context, bcr_notification.class);
        intent.putExtra("when", id);
        //intent.setAction(Intent.ACTION_MAIN);
        // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Log.d("SetAlarm",String.valueOf(id)+" | "+String.valueOf(time));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        } else
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);

    }
    public static void cancel_alarm(Context context,int time_num){

        Log.d("cancel",String.valueOf(time_num));
        Intent intent = new Intent(context, bcr_notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, time_num, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        // Toast.makeText(context, "یادآور برای این ساعت لغو شد", Toast.LENGTH_SHORT).show();
    }
}
