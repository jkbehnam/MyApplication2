package com.example.behnam.myapplication.database_pack;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.objects.Chart_data;
import com.example.behnam.myapplication.objects.Animation;
import com.example.behnam.myapplication.objects.DailyData;
import com.example.behnam.myapplication.objects.Duration;
import com.example.behnam.myapplication.objects.Exercise;
import com.example.behnam.myapplication.objects.MiBandData;
import com.example.behnam.myapplication.objects.Sign_list;
import com.example.behnam.myapplication.objects.User;
import com.example.behnam.myapplication.objects.exercise_item;
import com.example.behnam.myapplication.objects.myChatMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import nodomain.freeyourgadget.gadgetbridge.entities.MiBandActivitySample;
import saman.zamani.persiandate.PersianDate;


public class DataBase_read {

    public static ArrayList<Animation> give_animation(Context context, String type) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from animation_table where anim_type='" + type + "'", null);
        cr.moveToFirst();
        ArrayList<Animation> anim_list = new ArrayList<>();
        if (cr.getCount() != 0 && cr.getString(cr.getColumnIndex("anim_type")).equals(type)) {
            do {
                Animation Q = new Animation();
                Q.setAnim_type(cr.getString(cr.getColumnIndex("anim_type")));
                Q.setAnim_name(cr.getString(cr.getColumnIndex("anim_name")));
                Q.setAnim_text(cr.getString(cr.getColumnIndex("anim_text")));
                Q.setAnim_video(cr.getString(cr.getColumnIndex("anim_video")));
                anim_list.add(Q);

            } while (cr.moveToNext());
        }
        return anim_list;
    }

    public static ArrayList<myChatMessage> get_chats(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from ChatAll", null);
        cr.moveToFirst();
        ArrayList<myChatMessage> chat_list = new ArrayList<>();
        if (cr.getCount() != 0) {
            do {
                myChatMessage Q = new myChatMessage();
                Q.setTimestamp(cr.getString(cr.getColumnIndex("date")));
                Q.setMessage(cr.getString(cr.getColumnIndex("text")));
                Q.setWho_send(cr.getString(cr.getColumnIndex("role")));
                chat_list.add(Q);

            } while (cr.moveToNext());
        }
        return chat_list;
    }

    public static ArrayList<myChatMessage> get_notsend_chats(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from ChatSend where is_send=0", null);
        cr.moveToFirst();
        ArrayList<myChatMessage> chat_list = new ArrayList<>();
        if (cr.getCount() != 0) {
            do {
                myChatMessage Q = new myChatMessage();
                Q.setId(cr.getInt(cr.getColumnIndex("id")));
                Q.setMessage(cr.getString(cr.getColumnIndex("content")));

                chat_list.add(Q);

            } while (cr.moveToNext());
        }
        return chat_list;
    }

    public static ArrayList<String> get_drugs(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from DrugDetails", null);
        cr.moveToFirst();
        ArrayList<String> _list = new ArrayList<>();
        if (cr.getCount() != 0) {
            do {
                _list.add(cr.getString(cr.getColumnIndex("details")));

            } while (cr.moveToNext());
        }
        return _list;
    }

    public static ArrayList<DailyData> get_unsend_daily_data(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from DailyData where is_send='0'", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
        cr.moveToFirst();
        ArrayList<DailyData> daily_list = new ArrayList<>();
        if (cr.getCount() != 0) {
            do {
                DailyData d = new DailyData();
                d.setTime(cr.getString(cr.getColumnIndex("time")));
                d.setAnswer_time(cr.getString(cr.getColumnIndex("answer_time")));
                d.setUp_pb(Double.parseDouble(cr.getString(cr.getColumnIndex("bp_up"))));
                d.setDown_pb(Double.parseDouble(cr.getString(cr.getColumnIndex("bp_down"))));
                d.setGluose(Double.parseDouble(cr.getString(cr.getColumnIndex("glucose"))));
                d.setEt_weight(Double.parseDouble(cr.getString(cr.getColumnIndex("weight"))));
                d.setCigarette(Double.parseDouble(cr.getString(cr.getColumnIndex("cigarette"))));
                d.setSleep(Double.parseDouble(cr.getString(cr.getColumnIndex("sleep"))));
                d.setSteps(Double.parseDouble(cr.getString(cr.getColumnIndex("step"))));
                d.setHeart_rate_min(Double.parseDouble(cr.getString(cr.getColumnIndex("heart_rate"))));
                d.setHeart_rate_max(Double.parseDouble(cr.getString(cr.getColumnIndex("heart_rate"))));
                d.setLast_heart_rate(Double.parseDouble(cr.getString(cr.getColumnIndex("heart_rate"))));


                daily_list.add(d);
            } while (cr.moveToNext());
        }

        return daily_list;
    }

    public static ArrayList<exercise_item> get_unsend_exercise_data(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from InsertExercise where is_send='0'", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
        cr.moveToFirst();
        ArrayList<exercise_item> exercise_list = new ArrayList<>();
        if (cr.getCount() != 0) {
            do {
                exercise_item d = new exercise_item();
                d.setDate(cr.getString(cr.getColumnIndex("date")));
                d.setWarmSlowDown(cr.getInt(cr.getColumnIndex("warmSlowDown")));
                d.setWarmLiftAgree(cr.getInt(cr.getColumnIndex("warmLiftAgree")));
                d.setWarmLiftOpposite(cr.getInt(cr.getColumnIndex("warmLiftOpposite")));
                d.setWarmAnkle(cr.getInt(cr.getColumnIndex("warmAnkle")));
                d.setWarmToe(cr.getInt(cr.getColumnIndex("warmToe")));
                d.setWarmSquat(cr.getInt(cr.getColumnIndex("warmSquat")));
                d.setWarmSwingBack(cr.getInt(cr.getColumnIndex("warmSwingBack")));
                d.setWarmSideHand(cr.getInt(cr.getColumnIndex("warmSideHand")));
                d.setWarmBending(cr.getInt(cr.getColumnIndex("warmBending")));
                d.setWarmRotate(cr.getInt(cr.getColumnIndex("warmRotate")));
                d.setWarmShoulder(cr.getInt(cr.getColumnIndex("warmShoulder")));
                d.setWarmNeckSide(cr.getInt(cr.getColumnIndex("warmNeckSide")));
                d.setWarmNeckForward(cr.getInt(cr.getColumnIndex("warmNeckForward")));
                d.setWarmWalking(cr.getInt(cr.getColumnIndex("warmWalking")));
                d.setWarmWalkingHand(cr.getInt(cr.getColumnIndex("warmWalkingHand")));
                d.setCoolSlowDown(cr.getInt(cr.getColumnIndex("coolSlowDown")));
                d.setCoolNeckSide(cr.getInt(cr.getColumnIndex("coolNeckSide")));
                d.setCoolHandForward(cr.getInt(cr.getColumnIndex("coolNeckForward")));
                d.setCoolHandForward(cr.getInt(cr.getColumnIndex("coolHandForward")));
                d.setCoolElbow(cr.getInt(cr.getColumnIndex("coolElbow")));
                d.setCoolShoulder(cr.getInt(cr.getColumnIndex("coolShoulder")));
                d.setCoolSwingBack(cr.getInt(cr.getColumnIndex("coolSwingBack")));
                d.setCoolSideHand(cr.getInt(cr.getColumnIndex("coolSideHand")));
                d.setCoolSide(cr.getInt(cr.getColumnIndex("coolSide")));
                d.setCoolKnee(cr.getInt(cr.getColumnIndex("coolKnee")));
                d.setCoolBehindMuscles(cr.getInt(cr.getColumnIndex("coolBehindMuscles")));
                d.setCoolToe(cr.getInt(cr.getColumnIndex("coolToe")));
                d.setCoolFootBack(cr.getInt(cr.getColumnIndex("coolFootBack")));
                d.setCoolHardness(cr.getInt(cr.getColumnIndex("coolHardness")));
                d.setWarmHardness(cr.getInt(cr.getColumnIndex("warmHardness")));
                d.setHardness(cr.getInt(cr.getColumnIndex("hardness")));


                exercise_list.add(d);

            } while (cr.moveToNext());
        }

        return exercise_list;
    }

    public static ArrayList<Sign_list> get_unsend_sign_data(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from Signs where is_send='0'", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
        cr.moveToFirst();
        ArrayList<Sign_list> sign_list = new ArrayList<>();
        if (cr.getCount() != 0) {
            do {
                Sign_list d = new Sign_list();
                d.setDate(cr.getString(cr.getColumnIndex("date")));
                d.setFastBreathing(cr.getString(cr.getColumnIndex("fastBreathing")));
                d.setAbnormalDizziness(cr.getString(cr.getColumnIndex("abnormalDizziness")));
                d.setChestPain(cr.getString(cr.getColumnIndex("chestPain")));
                d.setShortnessBreath(cr.getString(cr.getColumnIndex("shortnessBreath")));
                d.setMuscleCramp(cr.getString(cr.getColumnIndex("muscleCramp")));
                d.setTiredness(cr.getString(cr.getColumnIndex("tiredness")));
                d.setHeartBeat(cr.getString(cr.getColumnIndex("heartBeat")));
                d.setCough(cr.getString(cr.getColumnIndex("cough")));
                d.setNausea(cr.getString(cr.getColumnIndex("nausea")));
                d.setColdSweat(cr.getString(cr.getColumnIndex("coldSweat")));
                d.setFastBreathingTime(cr.getString(cr.getColumnIndex("fastBreathingTime")));
                d.setAbnormalDizzinessTime(cr.getString(cr.getColumnIndex("abnormalDizzinessTime")));
                d.setChestPainTime(cr.getString(cr.getColumnIndex("chestPainTime")));
                d.setShortnessBreathTime(cr.getString(cr.getColumnIndex("shortnessBreathTime")));
                d.setMuscleCrampTime(cr.getString(cr.getColumnIndex("muscleCrampTime")));
                d.setTirednessTime(cr.getString(cr.getColumnIndex("tirednessTime")));
                d.setHeartBeatTime(cr.getString(cr.getColumnIndex("heartBeatTime")));
                d.setCoughTime(cr.getString(cr.getColumnIndex("coughTime")));
                d.setNauseaTime(cr.getString(cr.getColumnIndex("nauseaTime")));
                d.setColdSweatTime(cr.getString(cr.getColumnIndex("coldSweatTime")));
                d.setFastBreathingAction(cr.getString(cr.getColumnIndex("fastBreathingAction")));
                d.setAbnormalDizzinessAction(cr.getString(cr.getColumnIndex("abnormalDizzinessAction")));
                d.setChestPainAction(cr.getString(cr.getColumnIndex("chestPainAction")));
                d.setShortnessBreathAction(cr.getString(cr.getColumnIndex("shortnessBreathAction")));
                d.setMuscleCrampAction(cr.getString(cr.getColumnIndex("muscleCrampAction")));
                d.setTirednessAction(cr.getString(cr.getColumnIndex("tirednessAction")));
                d.setHeartBeatAction(cr.getString(cr.getColumnIndex("heartBeatAction")));
                d.setCoughAction(cr.getString(cr.getColumnIndex("coughAction")));
                d.setNauseaAction(cr.getString(cr.getColumnIndex("nauseaAction")));
                d.setColdSweatAction(cr.getString(cr.getColumnIndex("coldSweatAction")));
                d.setLameFeet(cr.getString(cr.getColumnIndex("lameFeet")));
                d.setLameFeetTime(cr.getString(cr.getColumnIndex("lameFeetTime")));
                d.setLameFeetAction(cr.getString(cr.getColumnIndex("lameFeetAction")));

                sign_list.add(d);

            } while (cr.moveToNext());
        }

        return sign_list;
    }

    public static ArrayList<MiBandData> get_unsend_band_data(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from MI_BAND_ACTIVITY_SAMPLE where is_send=0", null);
        cr.moveToFirst();

        ArrayList<MiBandData> data_list = new ArrayList<>();

        if (cr.getCount() != 0) {


            do {

                    MiBandData data = new MiBandData();
                    data.setTimestamp(cr.getInt(cr.getColumnIndex("TIMESTAMP")));
                    data.setHeartRate(cr.getInt(cr.getColumnIndex("HEART_RATE")));
                    data.setSteps(cr.getInt(cr.getColumnIndex("STEPS")));
                    data.setSport(cr.getInt(cr.getColumnIndex("sport")));
                    data_list.add(data);


            } while (cr.moveToNext());


        }


        return data_list;
    }


    public static String give_messages_daily(Context context, String type) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily where m_type='" + type + "' ORDER BY RANDOM() LIMIT 1", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);

        cr.moveToFirst();
        String Q = "";
        if (cr.getCount() != 0) {
            do {
                Q = cr.getString(cr.getColumnIndex("m_text"));


            } while (cr.moveToNext());
        }
        Q = Q.replace("hh", "</div><div>");
        Q = "<div class=\"body2\">" +"<span>&#9888;</span>"+ Q + "</div>";
        return Q;
    }

    public static ArrayList<Chart_data> get_steps(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from MI_BAND_ACTIVITY_SAMPLE", null);
        cr.moveToLast();


        GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
Long ss=c.getTimeInMillis();
        ArrayList<Chart_data> data = new ArrayList<>();

        int x;

        if (cr.getCount() != 0) {
            int step = 0;
            boolean end;

            do {
                x = cr.getInt(cr.getColumnIndex("TIMESTAMP"));
                if (cr.getInt(cr.getColumnIndex("STEPS")) > 0) {
                    step = step + cr.getInt(cr.getColumnIndex("STEPS"));
                }

                if (!(x > c.getTimeInMillis() / 1000)) {
                    Chart_data cd = new Chart_data();
                    PersianDate pdate = new PersianDate(c.getTimeInMillis());
                    cd.setText(String.valueOf(pdate.getShYear()) + "/" + String.valueOf(pdate.getShMonth()) + "/" + String.valueOf(pdate.getShDay()));
                    cd.setValue(step);
                    step = 0;
                    data.add(cd);
                    c.add(Calendar.DATE, -1);
                }


            } while (cr.moveToPrevious());
            if (step!=0) {
                Chart_data cd = new Chart_data();
                PersianDate pdate = new PersianDate(c.getTimeInMillis());
                cd.setText(String.valueOf(pdate.getShYear()) + "/" + String.valueOf(pdate.getShMonth()) + "/" + String.valueOf(pdate.getShDay()));
                cd.setValue(step);
                data.add(cd);

            }

        }


        return data;
    }
    public static ArrayList<Chart_data> get_heartrate(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from MI_BAND_ACTIVITY_SAMPLE", null);
        cr.moveToLast();
        ArrayList<Animation> anim_list = new ArrayList<>();

        GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        ArrayList<Chart_data> data = new ArrayList<>();

        int x=0;
        int counter=0;
        int date=0;
        int lastday_HR=0;
        if (cr.getCount() != 0) {
            int heart_rate = 0;



            do {
                x = cr.getInt(cr.getColumnIndex("TIMESTAMP"));
                if (cr.getInt(cr.getColumnIndex("HEART_RATE"))!= 0 &&cr.getInt(cr.getColumnIndex("HEART_RATE"))!=255) {
                    heart_rate = heart_rate + cr.getInt(cr.getColumnIndex("HEART_RATE"));
                    if(date==0&&(lastday_HR==0||lastday_HR==255)){
                        lastday_HR=heart_rate;

                    }
                    ++counter;
                }

                if (!(x > c.getTimeInMillis() / 1000)) {

                    Chart_data cd = new Chart_data();
                    PersianDate pdate = new PersianDate(c.getTimeInMillis());
                    cd.setText(String.valueOf(pdate.getShYear()) + "/" + String.valueOf(pdate.getShMonth()) + "/" + String.valueOf(pdate.getShDay()));
                    if(date==0){
                        if(lastday_HR!=255)
                        cd.setValue(lastday_HR);
                        else cd.setValue(0);
                    }
                    else {
                    if(counter!=0){
                    cd.setValue(heart_rate/counter);}
                    else {
                        cd.setValue(heart_rate);
                    }}
                    heart_rate = 0;
                    counter=0;
                    date++;
                    Log.d("HR",String.valueOf(cd.getValue())+" | "+String.valueOf(x));
                    data.add(cd);
                    c.add(Calendar.DATE, -1);
                }


            } while (cr.moveToPrevious());
            if (counter!=0) {

                Chart_data cd = new Chart_data();
                PersianDate pdate = new PersianDate(c.getTimeInMillis());
                cd.setText(String.valueOf(pdate.getShYear()) + "/" + String.valueOf(pdate.getShMonth()) + "/" + String.valueOf(pdate.getShDay()));
                if(date==0){
                    if(lastday_HR!=255)
                        cd.setValue(lastday_HR);
                    else cd.setValue(0);
                }
                else {
                    if(counter!=0){
                        cd.setValue(heart_rate/counter);}
                    else {
                        cd.setValue(heart_rate);
                    }}


                Log.d("HR",String.valueOf(cd.getValue())+" | "+String.valueOf(x));
                data.add(cd);

            }

        }


        return data;
    }
    public static ArrayList<Chart_data> get_hardness_5dayago(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from InsertExercise", null);
        cr.moveToLast();
        ArrayList<Chart_data> data = new ArrayList<>();

        String x;
        int i = 0;
        if (cr.getCount() != 0) {


            do {
                x = cr.getString(cr.getColumnIndex("date"));
                Chart_data cd = new Chart_data();
                PersianDate pdate = new PersianDate(Long.parseLong(x));
                cd.setText(String.valueOf(pdate.getShYear()) + "/" + String.valueOf(pdate.getShMonth()) + "/" + String.valueOf(pdate.getShDay()));
                cd.setValue(cr.getInt(cr.getColumnIndex("hardness")));
                data.add(cd);
                i++;


            } while (cr.moveToPrevious() && i < 5);


        }


        return data;
    }

    public static String get_height(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from UserInfo", null);
        cr.moveToLast();

        if (cr.getCount() != 0) {


            do {
                return cr.getString(cr.getColumnIndex("height"));


            } while (cr.moveToPrevious());


        }


        return "0";
    }

    public static DailyData get_today_steps_heart_minmax(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from MI_BAND_ACTIVITY_SAMPLE", null);
        cr.moveToLast();
        ArrayList<Animation> anim_list = new ArrayList<>();

        GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        ArrayList<Chart_data> data = new ArrayList<>();
        int h_r_min = 0;
        int h_r_last = 0;
        int h_r_max = 1000;
        int x;
        DailyData d_data = new DailyData();
        if (cr.getCount() != 0) {
            int step = 0;
            boolean end;

            do {
                x = cr.getInt(cr.getColumnIndex("TIMESTAMP"));
                if (!(x > c.getTimeInMillis() / 1000)) {
                    d_data.setHeart_rate_max(h_r_max);
                    d_data.setHeart_rate_min(h_r_min);
                    d_data.setLast_heart_rate(h_r_last);
                    d_data.setSteps(step);
                    return d_data;
                }
                if (h_r_last == 0&&cr.getInt(cr.getColumnIndex("HEART_RATE"))!=0&&cr.getInt(cr.getColumnIndex("HEART_RATE"))!=255) {
                    h_r_last = cr.getInt(cr.getColumnIndex("HEART_RATE"));

                }
                if (cr.getInt(cr.getColumnIndex("STEPS")) > 0) {
                    step = step + cr.getInt(cr.getColumnIndex("STEPS"));
                }
                if (cr.getInt(cr.getColumnIndex("HEART_RATE")) > h_r_max) {
                    h_r_max = cr.getInt(cr.getColumnIndex("HEART_RATE"));
                }
                if (cr.getInt(cr.getColumnIndex("HEART_RATE")) < h_r_min) {
                    h_r_min = cr.getInt(cr.getColumnIndex("HEART_RATE"));
                }


            } while (cr.moveToPrevious());


        }


        return d_data;
    }

    public static ArrayList<Chart_data> get_glu_cig_weight_sleep(Context context, String item) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select " + item + ",time from DailyData", null);
        cr.moveToLast();
        GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        ArrayList<Chart_data> data = new ArrayList<>();

        long x;

        if (cr.getCount() != 0) {


            do {
                x = cr.getInt(1);


                Chart_data cd = new Chart_data();
                PersianDate pdate = new PersianDate(c.getTimeInMillis());
                cd.setText(String.valueOf(pdate.getShYear()) + "/" + String.valueOf(pdate.getShMonth()) + "/" + String.valueOf(pdate.getShDay()));
                cd.setValue(cr.getInt(0));
                data.add(cd);
                c.add(Calendar.DATE, -1);


            } while (cr.moveToPrevious());


        }


        return data;
    }

    public static ArrayList<Chart_data> get_bp(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select bp_up,bp_down,time from DailyData", null);
        cr.moveToLast();


        GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        ArrayList<Chart_data> data = new ArrayList<>();

        long x;

        if (cr.getCount() != 0) {
            do {
                x = cr.getInt(2);


                Chart_data cd = new Chart_data();
                PersianDate pdate = new PersianDate(c.getTimeInMillis());
                cd.setText(String.valueOf(pdate.getShYear()) + "/" + String.valueOf(pdate.getShMonth()) + "/" + String.valueOf(pdate.getShDay()));
                cd.setValue(cr.getInt(0));
                cd.setValue2(cr.getInt(1));
                data.add(cd);
                c.add(Calendar.DATE, -1);


            } while (cr.moveToPrevious());


        }


        return data;
    }

    public static ArrayList<String> get_traningS_items1(Context context, String table) {
        Cursor cr;

        cr = DataBase.getInstance(context).getDb().rawQuery("select distinct level1 from " + table + "", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
        ArrayList<String> s = new ArrayList<>();
        cr.moveToFirst();
        String Q = "";
        if (cr.getCount() != 0) {
            do {
                Q = cr.getString(cr.getColumnIndex("level1"));
                s.add(Q);

            } while (cr.moveToNext());
        }


        return s;
    }

    public static ArrayList<String> get_traningS_items2(Context context, String type, String table) {
        Cursor cr;

        cr = DataBase.getInstance(context).getDb().rawQuery("select distinct level2 from " + table + " where level1='" + type + "'", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
        ArrayList<String> s = new ArrayList<>();
        cr.moveToFirst();
        String Q = "";
        if (cr.getCount() != 0) {
            do {
                Q = cr.getString(cr.getColumnIndex("level2"));
                s.add(Q);

            } while (cr.moveToNext());
        }


        return s;
    }

    public static ArrayList<String> get_traningS_items3(Context context, String type, String table) {
        Cursor cr;

        cr = DataBase.getInstance(context).getDb().rawQuery("select distinct level3 from " + table + " where level2='" + type + "'", null);
        // cr = DataBase.getInstance(context).getDb().rawQuery("select * from main_training_tb where level2='" + type + "'", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
        ArrayList<String> s = new ArrayList<>();
        cr.moveToFirst();
        String Q = "";
        if (cr.getCount() != 0) {
            do {
                Q = cr.getString(cr.getColumnIndex("level3"));
                s.add(Q);

            } while (cr.moveToNext());
        }


        return s;
    }

    public static ArrayList<String> get_traningS_items4(Context context, String type, String table) {
        Cursor cr;

        cr = DataBase.getInstance(context).getDb().rawQuery("select distinct level4 from " + table + " where level3='" + type + "'", null);
        // cr = DataBase.getInstance(context).getDb().rawQuery("select * from main_training_tb where level2='" + type + "'", null);
        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
        ArrayList<String> s = new ArrayList<>();
        cr.moveToFirst();
        String Q = "";
        if (cr.getCount() != 0) {
            do {
                Q = cr.getString(cr.getColumnIndex("level4"));
                s.add(Q);

            } while (cr.moveToNext());
        }


        return s;
    }

    public static User get_user_profile(Context context) {
        User user = new User();
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from UserInfo", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
        cr.moveToFirst();
        if (cr.getCount() != 0) {
            do {

                user.setId(cr.getInt(cr.getColumnIndex("id")));
                user.setUsername(cr.getString(cr.getColumnIndex("username")));
                user.setFirstName(cr.getString(cr.getColumnIndex("firstName")));
                user.setLastName(cr.getString(cr.getColumnIndex("lastName")));
                user.setParentName(cr.getString(cr.getColumnIndex("parentName")));
                user.setEducation(cr.getString(cr.getColumnIndex("education")));
                user.setNationalCode(cr.getString(cr.getColumnIndex("nationalCode")));
                user.setBirthDate(cr.getString(cr.getColumnIndex("birthDate")));
                user.setGender(cr.getString(cr.getColumnIndex("gender")));
                user.setJob(cr.getString(cr.getColumnIndex("job")));
                user.setPhone(cr.getString(cr.getColumnIndex("phone")));
                user.setAddress(cr.getString(cr.getColumnIndex("address")));
                user.setWeight(cr.getString(cr.getColumnIndex("weight")));
                user.setHeight(cr.getString(cr.getColumnIndex("height")));

            } while (cr.moveToNext());
        }


        return user;
    }
    public static ArrayList<com.example.behnam.myapplication.objects.Log> get_non_send_log(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from Logs where is_send=0", null);
        cr.moveToFirst();
        ArrayList<com.example.behnam.myapplication.objects.Log> logs_list = new ArrayList<>();
        if (cr.getCount() != 0) {
            do {
                com.example.behnam.myapplication.objects.Log Q = new com.example.behnam.myapplication.objects.Log();
                Q.setDate(cr.getString(cr.getColumnIndex("date")));
                Q.setFileName(cr.getString(cr.getColumnIndex("fileName")));
                Q.setTime(cr.getString(cr.getColumnIndex("time")));
                Q.setId(cr.getInt(cr.getColumnIndex("id")));
                logs_list.add(Q);

            } while (cr.moveToNext());
        }
        return logs_list;
    }
    public static ArrayList<Duration> get_non_send_duration(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from exercise_duration where is_send=0", null);
        cr.moveToFirst();
        ArrayList<Duration> excer_duration_list = new ArrayList<>();
        if (cr.getCount() != 0) {
            do {
               Duration Q = new Duration();
                Q.setDate(cr.getString(cr.getColumnIndex("date")));
                Q.setDurtaion(cr.getString(cr.getColumnIndex("duration")));
                Q.setId(cr.getInt(cr.getColumnIndex("id")));
                Q.setSport(cr.getString(cr.getColumnIndex("sport")));
                excer_duration_list.add(Q);

            } while (cr.moveToNext());
        }
        return excer_duration_list;
    }
    public static String getlastmental(String title,Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from MentalTest where mtitle='" + title + "'", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
        String result = "";

        cr.moveToLast();
        if (cr.getCount() != 0) {
            result = cr.getString(cr.getColumnIndex("result"));
        }
        return result;


    }
    public static int getnewmessage_number(Context context) {
        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from ChatAll where read='0'", null);

        //  Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily  WHERE CONTAINS(m_type, '"+type+"');", null);
int count=cr.getCount();
        return cr.getCount();


    }
}
