package com.example.behnam.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;


import com.example.behnam.myapplication.activities.MainActivity;

import java.util.Calendar;
import java.util.TimeZone;

public class bcr_notification extends BroadcastReceiver {
    send_notification_strategy SNS;
    int p;
Context context;
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        context=arg0;
        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();
        Log.d("ALarm", "alarm recived");
        Bundle extras = arg1.getExtras();
        p = extras.getInt("when");
        //------------------------------------
        Intent intent = new Intent(arg0, bcr_notification.class);
        intent.putExtra("when", p);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
        cal.add(java.util.Calendar.DATE, 1);
        Log.d("SetAlarm1", String.valueOf(p) + " | ");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(arg0, p, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) arg0.getSystemService(Context.ALARM_SERVICE);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(arg0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putBoolean(String.valueOf(p), true);
        prefsEditor.commit();


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        } else
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        //------------------------------------
        sendNotification(arg0, p);
    }

    public void sendNotification(Context context, int p) {

        switch (p) {
            case 10001:
                SNS = new daily_data_notification();
                SNS.send_nofift(context);

                break;
            case 10002:
                SNS = new day_exercise_notification();
                SNS.send_nofift(context);

                break;
            case 10003:
                SNS = new weekly_sign_notification();
                SNS.send_nofift(context);

                break;
            case 10004:
                SNS = new afsordegi();
                SNS.send_nofift(context);

                break;
            case 10005:
                SNS = new ezterab();
                SNS.send_nofift(context);

                break;
        }



    }

}