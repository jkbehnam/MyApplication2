package com.example.behnam.myapplication.broadcast_reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.behnam.myapplication.activities.Login_acirivty;
import com.example.behnam.myapplication.set_alarm_notify;

/**
 * Created by User on 2/16/2018.
 */

public class bcr_auto_setup_after_boot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //set_alarm.setalarm_CheckBox(context);
        SharedPreferences prefs2 = PreferenceManager.getDefaultSharedPreferences(context);
      if(prefs2.getLong("10001cal", 0)!=0) {
          set_alarm_notify.set_alarm_repeat(context, 10001, prefs2.getLong("10001cal", 0));
      }
        if(prefs2.getLong("10002cal", 0)!=0) {
            set_alarm_notify.set_alarm_repeat(context, 10002, prefs2.getLong("10002cal", 0));
        }
        if(prefs2.getLong("10003cal", 0)!=0) {
            set_alarm_notify.set_alarm_repeat(context, 10003, prefs2.getLong("10003cal", 0));
        }
        if(prefs2.getLong("10004cal", 0)!=0) {
            set_alarm_notify.set_alarm_repeat(context, 10004, prefs2.getLong("10004cal", 0));
        }
        if(prefs2.getLong("10005cal", 0)!=0) {
            set_alarm_notify.set_alarm_repeat(context, 10005, prefs2.getLong("10005cal", 0));
        }

    }
}
