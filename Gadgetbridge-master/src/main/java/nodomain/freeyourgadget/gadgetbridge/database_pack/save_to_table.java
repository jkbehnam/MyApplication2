package nodomain.freeyourgadget.gadgetbridge.database_pack;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import nodomain.freeyourgadget.gadgetbridge.entities.MiBandActivitySample;


public class save_to_table {
    String heartRateEnd = "";
    String heartRateBase = "";
    int perc;

    public static int read_retrival() {
        return 60;
    }

    public void save_today_data(List<MiBandActivitySample> samples, Context context) {


        get_user_profile(context);
        int max = Integer.parseInt(heartRateBase) + 20;
        int max_exer = (Integer.parseInt(heartRateEnd) * perc) / 100 + Integer.parseInt(heartRateBase);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        cal.add(Calendar.MINUTE, -3);
        Long now = System.currentTimeMillis();
        Long min60 = prefs.getLong("60min", now);


        for (MiBandActivitySample s : samples
                ) {

            ContentValues cv;
            cv = new ContentValues();

            cv.put("HEART_RATE", s.getHeartRate());
            Log.d("HEART_RATE", String.valueOf(s.getHeartRate()));
            cv.put("TIMESTAMP", s.getTimestamp());
            cv.put("DEVICE_ID", s.getDeviceId());
            cv.put("USER_ID", s.getUserId());
            cv.put("RAW_INTENSITY", s.getRawIntensity());
            cv.put("STEPS", s.getSteps());
            cv.put("RAW_KIND", s.getRawKind());

            cv.put("is_send", 0);

            try {
                if (cal.getTimeInMillis() / 1000 < s.getTimestamp()) {
                    if (min60 <= now) {


                        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                        SharedPreferences.Editor prefsEditor = prefs1.edit();
                        if (s.getHeartRate() != 255 && s.getHeartRate() != 0 && s.getHeartRate() > max) {
                            Log.d("behnam_fetch6", String.valueOf(s.getHeartRate()));

                            try {
                                Intent myIntent = new Intent(context, Class.forName("com.example.behnam.myapplication.activities.alarm_alarm"));
                                myIntent.putExtra("HR", s.getHeartRate());
                                myIntent.putExtra("HR_max", s.getHeartRate());
                                myIntent.putExtra("HR_time", s.getTimestamp());
                                context.startActivity(myIntent);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }


                        }
                    } else {
                        if (s.getHeartRate() != 255 && s.getHeartRate() != 0 && s.getHeartRate() >= max_exer) {
                            Intent myIntent = new Intent(context, Class.forName("com.example.behnam.myapplication.activities.alarm_alarm_2_photo"));
                            myIntent.putExtra("HR", s.getHeartRate());
                            myIntent.putExtra("HR_max", s.getHeartRate());
                            myIntent.putExtra("HR_time", s.getTimestamp());
                            context.startActivity(myIntent);


                        }


                        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                        SharedPreferences.Editor prefsEditor = prefs1.edit();
                        prefsEditor.putInt("inSport", 0);
                        prefsEditor.commit();

                    }

                } else {

                }
            } catch (Exception e) {
            }
            SharedPreferences prefs2 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            int sport = prefs2.getInt("inSport", 0);
            cv.put("sport", sport);
            DataBase.getInstance(context).getDb().insert("MI_BAND_ACTIVITY_SAMPLE", null, cv);
        }


    }

    public void get_user_profile(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        heartRateBase = prefs.getString("heartRateBase", "120");
        heartRateEnd = prefs.getString("heartRateEnd", "120");
        if (heartRateEnd.equals("")) {
            heartRateEnd = "120";
        }
        try {
            switch (Max_hr_week(context.getApplicationContext())) {
                case 1:
                    perc = Integer.parseInt(prefs.getString("week1", "35"));

                    break;
                case 2:
                    perc = Integer.parseInt(prefs.getString("week2", "40"));
                    break;
                case 3:
                    perc = Integer.parseInt(prefs.getString("week3", "50"));
                    break;
                case 4:
                    perc = Integer.parseInt(prefs.getString("week4", "60"));
                    break;
                case 5:
                    perc = Integer.parseInt(prefs.getString("week5", "70"));
                    break;
                case 6:
                    perc = Integer.parseInt(prefs.getString("week6", "80"));
                    break;
            }

        } catch (Exception e) {
            perc = 35;


        }


        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);


    }

    public static void save_action_log(Context c, String action, Long time, long spend_time) {
        ContentValues cv;
        cv = new ContentValues();
        cv.put("date", time);
        cv.put("fileName", action);
        cv.put("time", String.valueOf(spend_time / 1000));
        cv.put("is_send", 0);
        new DataBase(c).getDb().insert("Logs", null, cv);
    }

    public int Max_hr_week(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Long start_date = prefs.getLong("start_date", 0);
        long daysDiff = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - start_date);
        return (int) (daysDiff / 7) + 1;
    }
}
