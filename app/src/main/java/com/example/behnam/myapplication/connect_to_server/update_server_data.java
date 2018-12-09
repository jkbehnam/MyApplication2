package com.example.behnam.myapplication.connect_to_server;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_home;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.activities.messages.Fragment_doctor_message;
import com.example.behnam.myapplication.database_pack.DataBase;
import com.example.behnam.myapplication.database_pack.DataBase_issend;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.example.behnam.myapplication.objects.DailyData;
import com.example.behnam.myapplication.objects.Drug_details;
import com.example.behnam.myapplication.objects.Duration;
import com.example.behnam.myapplication.objects.MiBandData;
import com.example.behnam.myapplication.objects.Sign_list;
import com.example.behnam.myapplication.objects.exercise_item;
import com.example.behnam.myapplication.objects.myChatMessage;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import nodomain.freeyourgadget.gadgetbridge.activities.ControlCenterv2;

import static com.example.behnam.myapplication.connect_to_server.URLs.URL_AIRPOLLUTION;

/**
 * Created by User on 2/13/2018.
 */

public class update_server_data {


    public static void syncData(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
        String userToken = prefs.getString("token", "");
        if (!userToken.equals("")) {
            send_last_daily_data(context);
            send_last_exercise_data(context);
            send_last_band_data(context);
            send_last_sign_data(context);
            GetChatMessages(context);
            send_chat_array(context);
            GetMaxHeart(context);
            GetDrugData(context);
            GetRiskFactors(context);
            send_last_log_data(context);
            GetHeartBeatBrief(context);
            send_exerciseDuration(context);
        }

    }

    public static void send_last_daily_data(Context context) {
        ArrayList<DailyData> d_list = DataBase_read.get_unsend_daily_data(context);
        for (DailyData d : d_list
                ) {
            send_daily_data(context, d);

        }
    }

    public static void send_daily_data(final Context context, DailyData d) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_DAILY_DATA,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            DataBase_issend.system_dailydata_isread(context, d);
                            // Toast.makeText(context, "داده با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_dailydata", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date", String.valueOf(d.getAnswer_time()));
                params.put("bloodPressureMin", String.valueOf(d.getDown_pb()));
                params.put("bloodPressureMax", String.valueOf(d.getUp_pb()));
                params.put("bloodGlucose", String.valueOf(d.getGluose()));
                params.put("weight", String.valueOf(d.getEt_weight()));
                params.put("cigarette", String.valueOf(d.getCigarette()));
                params.put("sleep", String.valueOf(d.getSleep()));
                params.put("stepCount", String.valueOf(d.getSteps()));
                params.put("depression", DataBase_read.getlastmental("afsordegi", context));
                params.put("anxiety", DataBase_read.getlastmental("ezterab", context));
                params.put("heartBeatMin", String.valueOf(d.getHeart_rate_min()));
                params.put("heartBeatMax", String.valueOf(d.getHeart_rate_max()));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        //return q_list;
    }

    public static void send_last_exercise_data(Context context) {
        ArrayList<exercise_item> d_list = DataBase_read.get_unsend_exercise_data(context);
        for (exercise_item d : d_list
                ) {
            send_exercise_data(context, d);

        }

    }

    public static void send_exercise_data(final Context context, exercise_item d) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_EXERCISE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            DataBase_issend.system_exercise_isread(context, d);
                            // Toast.makeText(context, "داده ورزش با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_exercisedata", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("date", d.getDate());
                params.put("warmSlowDown", String.valueOf(d.getWarmSlowDown()));
                params.put("warmLiftAgree", String.valueOf(d.getWarmLiftAgree()));
                params.put("warmLiftOpposite", String.valueOf(d.getWarmLiftOpposite()));
                params.put("warmAnkle", String.valueOf(d.getWarmAnkle()));
                params.put("warmToe", String.valueOf(d.getWarmToe()));
                params.put("warmSquat", String.valueOf(d.getWarmSquat()));
                params.put("warmSwingBack", String.valueOf(d.getWarmSwingBack()));
                params.put("warmSideHand", String.valueOf(d.getWarmSideHand()));
                params.put("warmBending", String.valueOf(d.getWarmBending()));
                params.put("warmRotate", String.valueOf(d.getWarmRotate()));
                params.put("warmShoulder", String.valueOf(d.getWarmShoulder()));
                params.put("warmNeckSide", String.valueOf(d.getWarmNeckSide()));
                params.put("warmNeckForward", String.valueOf(d.getWarmNeckForward()));
                params.put("warmWalking", String.valueOf(d.getWarmWalking()));
                params.put("warmWalkingHand", String.valueOf(d.getWarmWalkingHand()));
                params.put("coolSlowDown", String.valueOf(d.getCoolSlowDown()));
                params.put("coolNeckSide", String.valueOf(d.getCoolNeckSide()));
                params.put("coolNeckForward", String.valueOf(d.getCoolNeckForward()));
                params.put("coolHandForward", String.valueOf(d.getCoolHandForward()));
                params.put("coolElbow", String.valueOf(d.getCoolElbow()));
                params.put("coolShoulder", String.valueOf(d.getCoolShoulder()));

                params.put("coolSideHand", String.valueOf(d.getCoolSideHand()));
                params.put("coolSide", String.valueOf(d.getCoolSide()));
                params.put("coolKnee", String.valueOf(d.getCoolKnee()));
                params.put("coolBehindMuscles", String.valueOf(d.getCoolBehindMuscles()));
                params.put("coolToe", String.valueOf(d.getCoolToe()));
                params.put("coolFootBack", String.valueOf(d.getCoolFootBack()));
                params.put("coolHardness", String.valueOf(d.getCoolHardness()));
                params.put("warmHardness", String.valueOf(d.getWarmHardness()));
                params.put("hardness", String.valueOf(d.getHardness()));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        //return q_list;
    }

    public static void send_last_band_data(Context context) {
        ArrayList<MiBandData> d_list = DataBase_read.get_unsend_band_data(context);
        ArrayList<MiBandData> d_list2 = new ArrayList<>();
        if (d_list.size() != 0) {
            JSONArray jArrayPatientQuestion = new JSONArray();
            int i = 0;


            for (MiBandData d : d_list) {
                i++;
                if (i == 50) {
                    i = 0;
                    send_band_data(context, d_list2, jArrayPatientQuestion);
                    d_list2 = new ArrayList<>();
                    jArrayPatientQuestion = new JSONArray();

                }
                try {
                    JSONObject jObjectPatientQuestion = new JSONObject();
                    jObjectPatientQuestion.put("date", d.getTimestamp());
                    jObjectPatientQuestion.put("stepCount", d.getSteps());
                    jObjectPatientQuestion.put("heartBeat", String.valueOf(d.getHeartRate()));
                    jObjectPatientQuestion.put("sport", d.getSport());

                    //jObjectPatientQuestion.put("RegDateTime", patientQuestion.getRegDateTime());
                    jArrayPatientQuestion.put(jObjectPatientQuestion);
                    d_list2.add(d);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (i < 50) {
                send_band_data(context, d_list2, jArrayPatientQuestion);
            }


        }
    }

    public static void send_band_data(final Context context, ArrayList<MiBandData> d, JSONArray jArrayPatientQuestion) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_BAND_DATA,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Log.d("bandsend", "success");
                            DataBase_issend.system_banddata_isread(context, d);
                            //  Toast.makeText(context, "داده دستبند با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);

                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jArrayPatientQuestion.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 500000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 500000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        //return q_list;
    }

    public static void send_last_sign_data(Context context) {
        ArrayList<Sign_list> d_list = DataBase_read.get_unsend_sign_data(context);
        for (Sign_list d : d_list
                ) {
            send_sign_data(context, d);

        }

    }

    public static void send_sign_data(final Context context, Sign_list d) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_SIGN_DATA,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            DataBase_issend.system_sign_isread(context, d);
                            // Toast.makeText(context, "داده علایم با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_signdata", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date", String.valueOf(d.getDate()));
                params.put("fastBreathing", String.valueOf(d.getFastBreathing()));
                params.put("abnormalDizziness", String.valueOf(d.getAbnormalDizziness()));
                params.put("chestPain", String.valueOf(d.getChestPain()));
                params.put("shortnessBreath", String.valueOf(d.getShortnessBreath()));
                params.put("muscleCramp", String.valueOf(d.getMuscleCramp()));
                params.put("tiredness", String.valueOf(d.getTiredness()));
                params.put("heartBeat", String.valueOf(d.getHeartBeat()));
                params.put("cough", String.valueOf(d.getCough()));
                params.put("nausea", String.valueOf(d.getNausea()));
                params.put("coldSweat", String.valueOf(d.getColdSweat()));
                params.put("fastBreathingTime", String.valueOf(d.getFastBreathingTime()));
                params.put("abnormalDizzinessTime", String.valueOf(d.getAbnormalDizzinessTime()));
                params.put("chestPainTime", String.valueOf(d.getChestPainTime()));
                params.put("shortnessBreathTime", String.valueOf(d.getShortnessBreathTime()));
                params.put("muscleCrampTime", String.valueOf(d.getMuscleCrampTime()));
                params.put("tirednessTime", String.valueOf(d.getTirednessTime()));
                params.put("heartBeatTime", String.valueOf(d.getHeartBeatTime()));
                params.put("coughTime", String.valueOf(d.getCoughTime()));
                params.put("nauseaTime", String.valueOf(d.getNauseaTime()));
                params.put("coldSweatTime", String.valueOf(d.getColdSweatTime()));
                params.put("fastBreathingAction", String.valueOf(d.getFastBreathingAction()));
                params.put("abnormalDizzinessAction", String.valueOf(d.getAbnormalDizzinessAction()));
                params.put("chestPainAction", String.valueOf(d.getChestPainAction()));
                params.put("shortnessBreathAction", String.valueOf(d.getShortnessBreathAction()));
                params.put("muscleCrampAction", String.valueOf(d.getMuscleCrampAction()));
                params.put("tirednessAction", String.valueOf(d.getTirednessAction()));
                params.put("heartBeatAction", String.valueOf(d.getHeartBeatAction()));
                params.put("coughAction", String.valueOf(d.getCoughAction()));
                params.put("nauseaAction", String.valueOf(d.getNauseaAction()));
                params.put("coldSweatAction", String.valueOf(d.getColdSweatAction()));
                params.put("lameFeet", String.valueOf(d.getLameFeet()));
                params.put("lameFeetTime", String.valueOf(d.getLameFeetTime()));
                params.put("lameFeetAction", String.valueOf(d.getLameFeetAction()));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        stringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        //return q_list;
    }

    public static void send_chat_array(Context context) {
        ArrayList<myChatMessage> list = DataBase_read.get_notsend_chats(context);
        for (myChatMessage message : list
                ) {
            send_chat_messages(context, message);
        }
    }

    public static void send_chat_messages(final Context context, myChatMessage message) {
        ArrayList<String> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_CHATMESSAGES,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        if (response.equals("success")) {
                            //Toast.makeText(context, "پیام ها با موفقیت ارسال شد", Toast.LENGTH_SHORT).show();
                            Log.d("chats_send", response);
                            DataBase_issend.system_chat_isread(context, message);
                            //  DataBase_write.write_chat_recive(response);

                            //converting response to json object
                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_message", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("text", message.getMessage());

                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public static void getairPollution(android.support.v4.app.Fragment a, Context context) {

        final int TIMEOUT_MS = 20000;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                URL_AIRPOLLUTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String[] part = response.split("(?<=\\D)(?=\\d)");
                        //  System.out.println(part[0]);
                        //  System.out.println(part[1]);
                        part = part[0].replaceAll("[0-9]", "").split("\\(");
                        ((Fragment_home) a).setAirPollution(part[0], "");
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
                        SharedPreferences.Editor prefsEditor = prefs.edit();
                        prefsEditor.putLong("lastweather", Calendar.getInstance().getTimeInMillis());
                        prefsEditor.putString("airPollution", part[0]);
                        prefsEditor.commit();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


        };
        jsonObjRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjRequest);
        //return q_list;
    }

    public static void GetDrugData(Context context) {
        ArrayList<Drug_details> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_DRUG_DATA,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        prefsEditor.putString("DrugList", response);
                        prefsEditor.commit();
                        //progressBar.setVisibility(View.GONE);
                        if (!response.equals("")) {
                            Log.d("drugs", response);

                            JSONArray us = null;
                            try {
                                us = new JSONArray(response);
                                for (int i = 0; i < us.length(); i++) {
                                    JSONObject e = us.getJSONObject(i);
                                    Drug_details dd = new Drug_details();
                                    dd.setId(e.getString("id"));
                                    dd.setDate(e.getString("date"));
                                    dd.setGroup(e.getString("group"));
                                    id.add(dd);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            DataBase.getInstance(MainActivity.mainContext).getDb().execSQL("delete from " + "DrugDetails");
                            update_server_data.GetDrugDetail(id, context);

                            //converting response to json object
                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_drugs", error.toString());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void GetDrugDetail(ArrayList<Drug_details> drugs, Context context) {

        for (Drug_details drug : drugs
                ) {

            GetDrugData2(drug, context);
        }

    }

    public static void GetDrugData2(Drug_details drug, Context context) {
        ArrayList<String> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_DRUG_DATA_DETAILS + drug.getId() + "/" + drug.getDate()
                + "/" + drug.getGroup(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        if (!response.equals("")) {
                            Log.d("drugs2", response);

                            DataBase_write.write_drugs_details(response);

                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_drugs2", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public static void GetHeartBeatBrief(Context context) {
        ArrayList<String> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GetHeartBeatBrief,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //progressBar.setVisibility(View.GONE);
                        if (!response.equals("")) {
                            Log.d("GetHeartBeatBrief", response);

                            try {

                                JSONObject obj = new JSONObject(response);
                                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                prefsEditor.putString("week1", obj.getString("week1"));
                                prefsEditor.putString("week2", obj.getString("week2"));
                                prefsEditor.putString("week3", obj.getString("week3"));
                                prefsEditor.putString("week4", obj.getString("week4"));
                                prefsEditor.putString("week5", obj.getString("week5"));
                                prefsEditor.putString("week6", obj.getString("week6"));
                                prefsEditor.commit();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //converting response to json object
                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_briefheartrate", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public static void GetMaxHeart(Context context) {
        ArrayList<String> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_MAX_HEART,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //progressBar.setVisibility(View.GONE);
                        if (!response.equals("")) {
                            Log.d("heart", response);

                            JSONArray us = null;
                            try {
                                us = new JSONArray(response);
                                for (int i = 0; i < us.length(); i++) {
                                    JSONObject e = us.getJSONObject(i);
                                    id.add(e.getString("id"));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            update_server_data.GetHeartDetail(id, context);

                            //converting response to json object
                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_maxheartrate", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void GetHeartDetail(ArrayList<String> HeartId, Context context) {

        for (String heart : HeartId
                ) {

            GetHeartData2(heart, context);
        }


    }

    public static void GetHeartData2(String heartId, Context context) {
        ArrayList<String> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_MAX_HEART_DETAIL + heartId,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        if (!response.equals("")) {

                            try {

                                JSONObject obj = new JSONObject(response);
                                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                prefsEditor.putString("heartRateBase", obj.getString("heartRateBase"));
                                prefsEditor.putString("heartRateEnd", obj.getString("heartRateEnd"));
                                prefsEditor.commit();

                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                                //  String heartRateEnd = prefs.getString("heartRateEnd", "120");
                                String heartRateBase = prefs.getString("heartRateBase", "120");
                                String birthDate = prefs.getString("birthDate", "");
                                Long L = Long.parseLong(birthDate) * 1000;
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

                                SharedPreferences.Editor prefsEditor2 = pref.edit();
                                try {
                                    int age = getAge(L);
                                    String s = String.valueOf(220 - age - Integer.parseInt(heartRateBase));
                                    prefsEditor2.putString("heartRateEnd", s);
                                    prefsEditor2.commit();
                                }catch (Exception e){}


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_maxheartrate2", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void GetRiskFactors(Context context) {
        ArrayList<String> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_RISK_FACTORS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        if (!response.equals("")) {
                            Log.d("factorDM", response);

                            try {
                                JSONObject obj = new JSONObject(response);
                                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                prefsEditor.putString("factorDM", obj.getString("factorDM"));
                                prefsEditor.putString("factorSmoker", obj.getString("factorSmoker"));

                                prefsEditor.commit();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // update_server_data.GetDrugDetail(id, context);

                            //converting response to json object
                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_riskfactors", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void GetChatMessages(android.support.v4.app.Fragment a, Context context) {
        ArrayList<String> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_CHATMESSAGES,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        if (!response.equals("")) {
                            Log.d("chats", response);
                            DataBase_write.write_chat_recive(response);
                            ((Fragment_doctor_message) a).show_messages();
                            //converting response to json object
                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_getmessage", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void GetChatMessages(Context context) {
        ArrayList<String> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_CHATMESSAGES,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        if (!response.equals("")) {
                            Log.d("chats", response);
                            DataBase_write.write_chat_recive(response);

                            //converting response to json object
                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_getmessage", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void setread_message(Context context) {
        ArrayList<String> id = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URLs.URL_GET_MESSAGEREAD,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        if (!response.equals("")) {

                            //converting response to json object
                            //if no error in response
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_READmessage", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void show_error_warning(VolleyError error, Context context) {

        String message = null;
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }


        //  Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public static void send_last_log_data(Context context) {
        ArrayList<com.example.behnam.myapplication.objects.Log> d_list = DataBase_read.get_non_send_log(context);
        for (com.example.behnam.myapplication.objects.Log d : d_list
                ) {
            send_log_data(context, d);

        }

    }

    public static void send_log_data(final Context context, com.example.behnam.myapplication.objects.Log d) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOG,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            DataBase_issend.system_log_isread(context, d);
                            //  Toast.makeText(context, "داده ورزش با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_logdata", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("date", d.getDate());
                params.put("fileName", d.getFileName());
                params.put("time", d.getTime());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        //return q_list;
    }

    public static void send_exerciseDuration(Context context) {
        ArrayList<Duration> d_list = DataBase_read.get_non_send_duration(context);
        for (Duration d : d_list
                ) {
            send_exerciseDuration_d(context, d);

        }

    }

    public static void send_exerciseDuration_d(final Context context, Duration d) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_exerciseDuration,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            DataBase_issend.system_excer_duration_isread(context, d);
                            //  Toast.makeText(context, "داده ورزش با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_error_warning(error, context);
                        Log.d("myerror_logdata", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("sport", d.getSport());
                params.put("createDate", d.getDate());
                params.put("duration", d.getDurtaion());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        //return q_list;
    }

    public static int getAge(Long L) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(L);
        Date date1 = cal.getTime();
        int age = 0;

        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(date1);
        if (dob.after(now)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        int year1 = now.get(Calendar.YEAR);
        int year2 = dob.get(Calendar.YEAR);
        age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dob.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dob.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }
        return age;
    }
}
