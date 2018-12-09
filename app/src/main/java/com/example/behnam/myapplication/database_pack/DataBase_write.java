package com.example.behnam.myapplication.database_pack;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.connect_to_server.update_server_data;
import com.example.behnam.myapplication.objects.DailyData;
import com.example.behnam.myapplication.objects.Exercise;
import com.example.behnam.myapplication.objects.Sign;
import com.example.behnam.myapplication.objects.User;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import nodomain.freeyourgadget.gadgetbridge.entities.MiBandActivitySample;


public class DataBase_write {
    public void save_today_data(List<MiBandActivitySample> samples, Context context) {
        for (MiBandActivitySample s : samples
                ) {

            ContentValues cv;
            cv = new ContentValues();
            cv.put("TIMESTAMP", s.getTimestamp());
            cv.put("DEVICE_ID", s.getDeviceId());
            cv.put("USER_ID", s.getUserId());
            cv.put("RAW_INTENSITY", s.getRawIntensity());
            cv.put("STEPS", s.getSteps());
            cv.put("RAW_KIND", s.getRawKind());
            cv.put("HEART_RATE", s.getHeartRate());

            DataBase.getInstance(context).getDb().insert("MI_BAND_ACTIVITY_SAMPLE", null, cv);


        }

    }

    public static void write_daily_indicators(DailyData dailyData) {
        ContentValues cv;
        cv = new ContentValues();
        cv.put("bp_up", dailyData.getUp_pb());
        cv.put("bp_down", dailyData.getDown_pb());
        cv.put("glucose", dailyData.getGluose());
        cv.put("weight", dailyData.getEt_weight());
        cv.put("cigarette", dailyData.getCigarette());
        cv.put("sleep", dailyData.getSleep());
        cv.put("step", dailyData.getSteps());
        cv.put("heart_rate", dailyData.getLast_heart_rate());
        cv.put("is_send", 0);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
        cv.put("answer_time", String.valueOf(cal.getTimeInMillis() / 1000));

        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cv.put("time", String.valueOf(cal.getTimeInMillis() /1000));



        DataBase.getInstance(MainActivity.mainContext).getDb().replace("DailyData", null, cv);

    }

    public static void write_drugs_details(String item) {
        ContentValues cv;
        cv = new ContentValues();
        cv.put("details", item);
        DataBase.getInstance(MainActivity.mainContext).getDb().insert("DrugDetails", null, cv);


    }

    public static void write_chat_sender(String s) {

        ContentValues cv = new ContentValues();
        cv.put("content", s);
        cv.put("is_send", 0);
        DataBase.getInstance(MainActivity.mainContext).getDb().insert("ChatSend", null, cv);
        ContentValues cv2 = new ContentValues();
        cv2.put("date", System.currentTimeMillis());
        cv2.put("text", s);
        cv2.put("role", "patient");
        DataBase.getInstance(MainActivity.mainContext).getDb().insert("ChatAll", null, cv2);

    }

    public static void max_heart_rate(String s1, String s2) {
        DataBase.getInstance(MainActivity.mainContext).getDb().execSQL("delete from " + "MaxHeartRate");
        ContentValues cv = new ContentValues();
        cv.put("heartRateBase", s1);
        cv.put("heartRateEnd", s2);

        DataBase.getInstance(MainActivity.mainContext).getDb().insert("MaxHeartRate", null, cv);

    }

    public static void write_chat_recive(String s) {
        DataBase.getInstance(MainActivity.mainContext).getDb().execSQL("delete from " + "ChatAll");
        JSONArray us = null;
        int m_doctor_count = 0;
        try {
            us = new JSONArray(s);
            for (int i = 0; i < us.length(); i++) {
                JSONObject e = us.getJSONObject(i);


                ContentValues cv = new ContentValues();
                cv.put("userId", e.getString("userId"));
                cv.put("patientId", e.getString("patientId"));
                cv.put("text", e.getString("text"));
                cv.put("date", e.getString("date"));
                cv.put("firstName", e.getString("firstName"));
                cv.put("lastName", e.getString("lastName"));
                cv.put("role", e.getString("role"));
                cv.put("read", e.getString("read"));


                DataBase.getInstance(MainActivity.mainContext).getDb().insert("ChatAll", null, cv);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void write_signs(ArrayList<Sign> signs, Long timestamp) {
        signs = set_suration_sign(signs);

        ContentValues cv;
        cv = new ContentValues();
        cv.put("date", timestamp.toString());
        cv.put("fastBreathing", String.valueOf(signs.get(0).getIn_exercise()));
        cv.put("abnormalDizziness", String.valueOf(signs.get(1).getIn_exercise()));
        cv.put("chestPain", String.valueOf(signs.get(2).getIn_exercise()));
        cv.put("shortnessBreath", String.valueOf(signs.get(3).getIn_exercise()));
        cv.put("muscleCramp", String.valueOf(signs.get(4).getIn_exercise()));
        cv.put("tiredness", String.valueOf(signs.get(5).getIn_exercise()));
        cv.put("heartBeat", String.valueOf(signs.get(6).getIn_exercise()));
        cv.put("cough", String.valueOf(signs.get(7).getIn_exercise()));
        cv.put("nausea", String.valueOf(signs.get(8).getIn_exercise()));
        cv.put("coldSweat", String.valueOf(signs.get(10).getIn_exercise()));
        cv.put("lameFeet", String.valueOf(signs.get(11).getIn_exercise()));
        cv.put("fastBreathingTime", String.valueOf(signs.get(0).getDuration()));
        cv.put("abnormalDizzinessTime", String.valueOf(signs.get(1).getDuration()));
        cv.put("chestPainTime", String.valueOf(signs.get(2).getDuration()));
        cv.put("shortnessBreathTime", String.valueOf(signs.get(3).getDuration()));
        cv.put("muscleCrampTime", String.valueOf(signs.get(4).getDuration()));
        cv.put("tirednessTime", String.valueOf(signs.get(5).getDuration()));
        cv.put("heartBeatTime", String.valueOf(signs.get(6).getDuration()));
        cv.put("coughTime", String.valueOf(signs.get(7).getDuration()));
        cv.put("nauseaTime", String.valueOf(signs.get(8).getDuration()));
        cv.put("coldSweatTime", String.valueOf(signs.get(10).getDuration()));
        cv.put("lameFeetTime", String.valueOf(signs.get(11).getDuration()));
        cv.put("fastBreathingAction", String.valueOf(signs.get(0).getReaction()));
        cv.put("abnormalDizzinessAction", String.valueOf(signs.get(1).getReaction()));
        cv.put("chestPainAction", String.valueOf(signs.get(2).getReaction()));
        cv.put("shortnessBreathAction", String.valueOf(signs.get(3).getReaction()));
        cv.put("muscleCrampAction", String.valueOf(signs.get(4).getReaction()));
        cv.put("tirednessAction", String.valueOf(signs.get(5).getReaction()));
        cv.put("heartBeatAction", String.valueOf(signs.get(6).getReaction()));
        cv.put("coughAction", String.valueOf(signs.get(7).getReaction()));
        cv.put("nauseaAction", String.valueOf(signs.get(8).getReaction()));
        cv.put("coldSweatAction", String.valueOf(signs.get(10).getReaction()));
        cv.put("lameFeetAction", String.valueOf(signs.get(11).getReaction()));

        DataBase.getInstance(MainActivity.mainContext).getDb().insert("Signs", null, cv);


    }

    public void write_profile(User user) {
        DataBase.getInstance(MainActivity.mainContext).getDb().execSQL("delete from " + "UserInfo");
        ContentValues cv;
        cv = new ContentValues();
        cv.put("id", user.getId());
        cv.put("username", user.getUsername());
        cv.put("firstName", user.getFirstName());
        cv.put("lastName", user.getLastName());
        cv.put("parentName", user.getParentName());
        cv.put("education", user.getEducation());
        cv.put("nationalCode", user.getNationalCode());
        cv.put("birthDate", user.getBirthDate());
        cv.put("gender", user.getGender());
        cv.put("job", user.getJob());
        cv.put("phone", user.getPhone());
        cv.put("address", user.getAddress());
        cv.put("weight", user.getWeight());
        cv.put("height", user.getHeight());
        DataBase.getInstance(MainActivity.mainContext).getDb().insert("UserInfo", null, cv);
    }

    public static void write_mental_test(String title, int result, Long timestamp) {

        ContentValues cv;
        cv = new ContentValues();
        cv.put("mtitle", title);
        cv.put("result", String.valueOf(result));
        cv.put("time", timestamp.toString());
        DataBase.getInstance(MainActivity.mainContext).getDb().insert("MentalTest", null, cv);
    }

    public static void InsertExercise(ArrayList<Exercise> warmup, ArrayList<Exercise> colddown, int seekwarm, int seekmain, int seekcold) {
        ContentValues cv;
        cv = new ContentValues();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));

        cv.put("date", cal.getTimeInMillis() / 1000);
        cv.put("warmSlowDown", warmup.get(0).getIs_checked());
        cv.put("warmLiftAgree", warmup.get(1).getIs_checked());
        cv.put("warmLiftOpposite", warmup.get(2).getIs_checked());
        cv.put("warmAnkle", warmup.get(3).getIs_checked());
        cv.put("warmToe", warmup.get(4).getIs_checked());
        cv.put("warmSquat", warmup.get(5).getIs_checked());
        cv.put("warmSwingBack", warmup.get(6).getIs_checked());
        cv.put("warmSideHand", warmup.get(7).getIs_checked());
        cv.put("warmBending", warmup.get(8).getIs_checked());
        cv.put("warmRotate", warmup.get(9).getIs_checked());
        cv.put("warmShoulder", warmup.get(10).getIs_checked());
        cv.put("warmNeckSide", warmup.get(11).getIs_checked());
        cv.put("warmNeckForward", warmup.get(12).getIs_checked());
        cv.put("warmWalking", warmup.get(13).getIs_checked());
        cv.put("warmWalkingHand", warmup.get(14).getIs_checked());
        cv.put("coolSlowDown", colddown.get(0).getIs_checked());
        cv.put("coolNeckSide", colddown.get(1).getIs_checked());
        cv.put("coolNeckForward", colddown.get(2).getIs_checked());
        cv.put("coolHandForward", colddown.get(3).getIs_checked());
        cv.put("coolElbow", colddown.get(4).getIs_checked());
        cv.put("coolShoulder", colddown.get(5).getIs_checked());
        cv.put("coolSideHand", colddown.get(6).getIs_checked());
        cv.put("coolSide", colddown.get(7).getIs_checked());
        cv.put("coolKnee", colddown.get(8).getIs_checked());
        cv.put("coolBehindMuscles", colddown.get(9).getIs_checked());
        cv.put("coolToe", colddown.get(10).getIs_checked());
        cv.put("coolFootBack", colddown.get(11).getIs_checked());
        cv.put("coolHardness", seekcold);
        cv.put("warmHardness", seekwarm);
        cv.put("hardness", seekmain);
        cv.put("is_send", 0);
        DataBase.getInstance(MainActivity.mainContext).getDb().replace("InsertExercise", null, cv);


    }

    public static void save_action_log(String action, Long time, long spend_time) {
        ContentValues cv;
        cv = new ContentValues();
        cv.put("date",  String.valueOf(time));
        cv.put("fileName", action);
        cv.put("time", String.valueOf(spend_time / 1000));
        cv.put("is_send", 0);
        new DataBase(MainActivity.mainContext).getDb().insert("Logs", null, cv);
    }
    public static void save_action_exer_duration( Long time, int spend_time,String sport) {
        ContentValues cv;

        cv = new ContentValues();
        cv.put("date", String.valueOf(time));
        cv.put("duration", String.valueOf(spend_time));
        cv.put("sport", sport);
        cv.put("is_send", 0);
        new DataBase(MainActivity.mainContext).getDb().insert("exercise_duration", null, cv);
    }
    public static ArrayList<Sign> set_suration_sign(ArrayList<Sign> signs) {
        for (Sign sign : signs
                ) {
            if(sign.getTitle_chbox())
            {
            switch (sign.getDuration().intValue()) {
                case 0:
                    sign.setDuration((double) 0.08);
                    break;
                case 1:
                    sign.setDuration((double) 0.16);
                    break;
                case 2:
                    sign.setDuration((double) 0.25);
                    break;
                case 3:
                    sign.setDuration((double) 0.33);
                    break;
                case 4:
                    sign.setDuration((double) 0.41);
                    break;
                case 5:
                    sign.setDuration((double) 0.5);
                    break;
                case 6:
                    sign.setDuration((double) 1);
                    break;
                case 7:
                    sign.setDuration((double) 5);
                    break;
                case 8:
                    sign.setDuration((double) 10);
                    break;
                case 9:
                    sign.setDuration((double) 15);
                    break;
                case 10:
                    sign.setDuration((double) 20);
                    break;
                case 11:
                    sign.setDuration((double) 25);
                    break;
                case 12:
                    sign.setDuration((double) 30);
                    break;
                case 13:
                    sign.setDuration((double) 60);
                    break;
                case 14:
                    sign.setDuration((double) 120);
                    break;
                case 15:
                    sign.setDuration((double) 180);
                    break;
                case 16:
                    sign.setDuration((double) 240);
                    break;
                case 17:
                    sign.setDuration((double) 300);
                    break;
                case 18:
                    sign.setDuration((double) 360);
                    break;
                case 19:
                    sign.setDuration((double) 420);
                    break;
                case 20:
                    sign.setDuration((double) 480);
                    break;
                case 21:
                    sign.setDuration((double) 540);
                    break;
                case 22:
                    sign.setDuration((double) 600);
                    break;
                case 23:
                    sign.setDuration((double) 660);
                    break;
                case 24:
                    sign.setDuration((double) 720);
                    break;
                case 25:
                    sign.setDuration((double) 720);
                    break;

            }

        }else sign.setDuration((double) 0);}
        return signs;
    }


}
