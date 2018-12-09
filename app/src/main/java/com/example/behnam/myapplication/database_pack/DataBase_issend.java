package com.example.behnam.myapplication.database_pack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.behnam.myapplication.objects.Animation;
import com.example.behnam.myapplication.objects.DailyData;
import com.example.behnam.myapplication.objects.Daily_system_massage;
import com.example.behnam.myapplication.objects.Duration;
import com.example.behnam.myapplication.objects.Log;
import com.example.behnam.myapplication.objects.MiBandData;
import com.example.behnam.myapplication.objects.Sign_list;
import com.example.behnam.myapplication.objects.exercise_item;
import com.example.behnam.myapplication.objects.myChatMessage;

import java.util.ArrayList;

import nodomain.freeyourgadget.gadgetbridge.entities.MiBandActivitySample;
import nodomain.freeyourgadget.gadgetbridge.model.WeatherSpec;

public class DataBase_issend {
    public static void system_messages_isread(Context context, ArrayList<Daily_system_massage> dv_list) {
        for (Daily_system_massage sdm:dv_list
             ) {
            ContentValues cv;
            cv = new ContentValues();
            cv.put("is_read", "1");
            DataBase.getInstance(context).getDb().update("system_message", cv, "id='" + sdm.getDay() + "'", null);

        }

    }

    public static void system_dailydata_isread(Context context, DailyData dv_list) {

            ContentValues cv;
            cv = new ContentValues();
            cv.put("is_send", 1);
            DataBase.getInstance(context).getDb().update("Dailydata", cv, "answer_time='" + dv_list.getAnswer_time() + "'", null);



    }
    public static void system_exercise_isread(Context context, exercise_item dv_list) {

        ContentValues cv;
        cv = new ContentValues();
        cv.put("is_send", "1");
        DataBase.getInstance(context).getDb().update("InsertExercise", cv, "date='" + dv_list.getDate() + "'", null);

    }
    public static void system_log_isread(Context context, Log dv_list) {
                DataBase.getInstance(context).getDb().delete("Logs", "id='" + dv_list.getId() + "'", null);

    }

    public static void system_excer_duration_isread(Context context, Duration dv_list) {
        DataBase.getInstance(context).getDb().delete("exercise_duration", "id='" + dv_list.getId() + "'", null);

    }
    public static void system_sign_isread(Context context, Sign_list dv_list) {

        ContentValues cv;
        cv = new ContentValues();
        cv.put("is_send", "1");
        DataBase.getInstance(context).getDb().update("Signs", cv, "date='" + dv_list.getDate() + "'", null);



    }
    public static void system_chat_isread(Context context, myChatMessage dv_list) {

        ContentValues cv;
        cv = new ContentValues();
        cv.put("is_send", "1");
        DataBase.getInstance(context).getDb().update("ChatSend", cv, "id='" + dv_list.getId() + "'", null);



    }
    public static void system_banddata_isread(Context context, ArrayList<MiBandData> d_list) {
        for (MiBandData d : d_list) {

            ContentValues cv;
            cv = new ContentValues();
            cv.put("is_send", "1");
            DataBase.getInstance(context).getDb().update("MI_BAND_ACTIVITY_SAMPLE", cv, "TIMESTAMP='" + d.getTimestamp() + "'", null);

        }

    }
    public static void doctor_message_is_send(Context context) {

        ContentValues cv;
        cv = new ContentValues();
        cv.put("read", "1");
        DataBase.getInstance(context).getDb().update("ChatAll", cv, "", null);



    }
}
