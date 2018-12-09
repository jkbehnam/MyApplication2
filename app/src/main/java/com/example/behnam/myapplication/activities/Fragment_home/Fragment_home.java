
package com.example.behnam.myapplication.activities.Fragment_home;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;

import com.example.behnam.myapplication.activities.Fragment_mental.Fragment_mental;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_home_recycle;
import com.example.behnam.myapplication.connect_to_server.update_server_data;

import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.objects.HomeRecycleView;
import com.example.behnam.myapplication.send_notification_strategy;
import com.example.behnam.myapplication.set_alarm_notify;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import nodomain.freeyourgadget.gadgetbridge.GBApplication;
import saman.zamani.persiandate.PersianDate;

import static nodomain.freeyourgadget.gadgetbridge.devices.miband.MiBandConst.PREF_MI2_DISPLAY_ITEMS;


public class Fragment_home extends Fragment {
    ArrayList<HomeRecycleView> OH_list;
    JSONObject data = null;
    TextView txt_day_of_month, txt_date, txt_tmp_num, txt_tmp_photo, txt_pollution_num;
    FragmentActivity c;
    ArrayList<SweetAlertDialog> sea;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_fragment_home, container, false);
        setRetainInstance(true);
        try {
            GBApplication.deviceService().onSetHeartRateMeasurementInterval(60);
            GBApplication.deviceService().onSendConfiguration(PREF_MI2_DISPLAY_ITEMS);

        } catch (Exception e) {
        }



        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
        cal.add(Calendar.MINUTE, 1);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("صفحه اصلی");

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.home_sv);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.HomeRecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        OH_list = new ArrayList<>();
        OH_list.add(new HomeRecycleView("وضعیت سلامت روزانه", "ff", R.drawable.exercise_icon_plus_recyclerview));
        OH_list.add(new HomeRecycleView("ورزش", " b", R.drawable.exercise_icon_ridingbike_recyclerview));
        OH_list.add(new HomeRecycleView("حالات روحی و آرام سازی", "b ", R.drawable.exercise_icon_sleeping_recyclerview));
        OH_list.add(new HomeRecycleView("علائم بیماری", "b ", R.drawable.exercise_icon_first_aid));
        OH_list.add(new HomeRecycleView("آموزش", " b", R.drawable.exercise_icon_education));
        OH_list.add(new HomeRecycleView("داروها", "b ", R.drawable.exercise_icon_pill));

        txt_date = (TextView) rootView.findViewById(R.id.txt_date);
        txt_day_of_month = (TextView) rootView.findViewById(R.id.txt_day_of_month);
        txt_pollution_num = (TextView) rootView.findViewById(R.id.pollution_num);
        txt_tmp_num = (TextView) rootView.findViewById(R.id.temp_num);
        txt_tmp_photo = (TextView) rootView.findViewById(R.id.temp_photo);

        PersianDate pdate = new PersianDate();
        txt_day_of_month.setText(String.valueOf(pdate.getShDay()));
        txt_date.setText(pdate.monthName() + " " + String.valueOf(pdate.getShYear()));

        adapter_home_recycle madapter = new adapter_home_recycle(OH_list);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        alphaAdapter.setFirstOnly(true);
        recyclerView.setAdapter(alphaAdapter);
        madapter.setOnCardClickListner(new adapter_home_recycle.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = null;
                switch (position) {
                    case 0:

                        fragment = new Fragment_AddDailyData();
                        transaction.add(R.id.container, fragment);
                        transaction.commit();
                        break;
                    case 1:

                        fragment = new Fragment_insert_exercise();
                        transaction.add(R.id.container, fragment);
                        transaction.commit();
                        break;
                    case 2:
                        fragment = new Fragment_mental();
                        transaction.add(R.id.container, fragment);
                        transaction.commit();
                        break;
                    case 3:
                        fragment = new Fragment_signs();
                        transaction.add(R.id.container, fragment);
                        transaction.commit();

                        break;
                    case 4:
                        fragment = new Fragment_trianing();
                        transaction.add(R.id.container, fragment);
                        transaction.commit();
                        break;
                    case 5:
                        fragment = new Fragment_drug();
                        transaction.add(R.id.container, fragment);
                        transaction.commit();
                        break;
                }

            }
        });




        updateWeatherData();



        return rootView;
    }

    public void get_weather_temp(final String city) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    // URL url = new URL("https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22");

                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=&" + city + "&units=metric&APPID=304dd13d07451ddabcf4cc80cb08824e");


                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while ((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());

                    if (data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {

                    System.out.println("Exception " + e.getMessage());
                    return null;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if (data != null) {
                    Log.d("my weather received", data.toString());
                    try {
                        String city = data.getString("name");

                        String country = data.getJSONObject("sys").getString("country");
                        // String temp = data.getString("description");
                        //  String temp =String.valueOf( data.getInt("temp")) ;
                        String temp = data.getJSONArray("weather").getJSONObject(0).getString("description");
                        Log.d("loc2_city", city);
                        Log.d("loc2_country", country);
                        Log.d("loc2_temp", temp);
                        String temp_num = data.getJSONObject("main").getString("temp");
                        txt_tmp_num.setText(temp_num + "°");

                        // JSONObject main = data.getJSONObject("main");

                        //todayWeather.setTemperature(main.getString("temp"));
                        final String idString = data.getJSONArray("weather").getJSONObject(0).getString("id");
                        setWeatherIcon(Integer.parseInt(idString), Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

                        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        prefsEditor.putString("last_loc", "(" + country + " " + city + ")");

                        prefsEditor.putString("lastweather_temp", temp_num);
                        prefsEditor.putString("lastweather_icon", idString);

                        prefsEditor.commit();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }.execute();

    }

    private void setWeatherIcon(int actualId, int hourOfDay) {
        try {


            int id = actualId / 100;
            String icon = "";
            if (actualId == 800) {
                if (hourOfDay >= 7 && hourOfDay < 20) {
                    icon = this.getString(R.string.weather_sunny);
                } else {
                    icon = this.getString(R.string.weather_clear_night);
                }
            } else {
                switch (id) {
                    case 2:
                        icon = this.getString(R.string.weather_thunder);
                        break;
                    case 3:
                        icon = this.getString(R.string.weather_drizzle);
                        break;
                    case 7:
                        icon = this.getString(R.string.weather_foggy);
                        break;
                    case 8:
                        icon = this.getString(R.string.weather_cloudy);
                        break;
                    case 6:
                        icon = this.getString(R.string.weather_snowy);
                        break;
                    case 5:
                        icon = this.getString(R.string.weather_rainy);
                        break;
                }

            }
            Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "font/weather.ttf");
            txt_tmp_photo.setTypeface(weatherFont);
            txt_tmp_photo.setText(icon);
        } catch (Exception e) {
        }
    }

    public void setAirPollution(String num, String state) {
        txt_pollution_num.setText(String.valueOf(num));
    }

    public void dialog_(int i) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        final Fragment[] fragment = {null};

        String message;
        String btn_cancle = "بستن پنجره";
        String btn_confirm = "باز کردن فرم";
        switch (i) {
            case 10001:
                message = "دوست عزیز جهت نظارت بر روند بهبودی شما و ارائه مشاوره بموقع لازم است اطلاعات لازم را در قسمت شاخص های روزانه ثبت نمایید.";
               SweetAlertDialog se= new SweetAlertDialog(c, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                se .setTitleText("لطفا فرم شاخص های روزانه را پر کنید");
                se.setContentText(message);
                se .setConfirmText(btn_confirm);
                se .setCancelText(btn_cancle);
                se .setCustomImage(R.drawable.exercise_icon_plus_recyclerview);
                se .showCancelButton(true);
                se .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        });
                se  .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                fragment[0] = new Fragment_AddDailyData();
                                transaction.add(R.id.container, fragment[0]);
                                transaction.commit();
                                alertdimis();

                            }
                        });
                se  .show();
                sea.add(se);
                break;
            case 10002:
                message = "";
                 se=  new SweetAlertDialog(c, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                se  .setTitleText("لطفا فرم صفحه ورزش را پر کنید");
                se  .setContentText("دوست عزیز جهت نظارت بر روند بهبودی شما و ارائه مشاوره بموقع لازم است اطلاعات مربوط به فعالیت ورزشی (گرم کردن، ورزش اصلی و سرد کردن)خود را در قسمت ورزش ثبت نمایید.");
                se .setConfirmText(btn_confirm);
                se .setCancelText(btn_cancle);

                se  .setCustomImage(R.drawable.exercise_icon_ridingbike_recyclerview);
                se  .showCancelButton(true);
                se  .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismissWithAnimation();
                            }
                        });
                se   .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                fragment[0] = new Fragment_insert_exercise();
                                transaction.add(R.id.container, fragment[0]);
                                transaction.commit();
                                alertdimis();
                            }
                        });
                se   .show();
                sea.add(se);
                break;
            case 10003:
                message = "دوست عزیز در صورتی که هر گونه ناراحتی و علائم غیر طبیعی در طول روز دارید در قسمت علائم ثبت نمایید تا بتوان بر روند بهبودی شما بدرستی نظارت داشت.";
              se=  new SweetAlertDialog(c, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                se.setTitleText("لطفا فرم صفحه علائم را پر کنید");
                se.setContentText(message);
                se.setConfirmText(btn_confirm);
                se.setCancelText(btn_cancle);
                se.setCustomImage(R.drawable.exercise_icon_first_aid);
                se.showCancelButton(true);
                se.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismissWithAnimation();
                            }
                        });
                se.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                fragment[0] = new Fragment_signs();
                                transaction.add(R.id.container, fragment[0]);
                                transaction.commit();
                                alertdimis();
                            }
                        });
                se .show();
                sea.add(se);
                break;
            case 10004:
                message = "دوست عزیز در صورتی که هر گونه ناراحتی و علائم غیر طبیعی در طول روز دارید در قسمت علائم ثبت نمایید تا بتوان بر روند بهبودی شما بدرستی نظارت داشت.";
               se= new SweetAlertDialog(c, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                se .setTitleText("لطفا فرم مربوط به افسردگی را پر کنید");
                se.setContentText(message);
                se.setConfirmText(btn_confirm);
                se.setCancelText(btn_cancle);

                se.setCustomImage(R.drawable.exercise_icon_sleeping_recyclerview);
                se.showCancelButton(true);
                se .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                fragment[0] = new Fragment_mental();
                                transaction.add(R.id.container, fragment[0]);
                                transaction.commit();
                                alertdimis();
                            }
                        });
                se .show();
                sea.add(se);
                break;
            case 10005:
                message = "دوست عزیز در صورتی که هر گونه ناراحتی و علائم غیر طبیعی در طول روز دارید در قسمت علائم ثبت نمایید تا بتوان بر روند بهبودی شما بدرستی نظارت داشت.";
                se= new SweetAlertDialog(c, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                se .setTitleText("لطفا فرم مربوط به اضطراب را پر کنید");
                se.setContentText(message);
                se .setCancelText(btn_cancle);

                se .setConfirmText(btn_confirm);
                se .setCustomImage(R.drawable.exercise_icon_sleeping_recyclerview);
                se .showCancelButton(true);
                se .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        });
                se .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                fragment[0] = new Fragment_mental();
                                transaction.add(R.id.container, fragment[0]);
                                transaction.commit();
                                alertdimis();
                            }
                        });
                se.show();
                sea.add(se);
                break;


        }
    }
public void alertdimis(){
    for (SweetAlertDialog s:sea
         ) {s.dismissWithAnimation();

    }
}
    public void updateWeatherData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
        long lastweather = prefs.getLong("lastweather", 0);
        Calendar cal = Calendar.getInstance();
        if ((cal.getTimeInMillis() - lastweather) / 1000 < 3600) {
            String lastweather_temp = prefs.getString("lastweather_temp", "0");
            String lastweather_icon = prefs.getString("lastweather_icon", "0");
            String lastpollution = prefs.getString("airPollution", "0");
            setAirPollution(lastpollution, "0");
            txt_tmp_num.setText(lastweather_temp + "°");
            setWeatherIcon(Integer.parseInt(lastweather_icon), Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            SharedPreferences prefs2 = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
            sea=new ArrayList<>();
            if (prefs2.getBoolean("10001", false)) {
                dialog_(10001);
            }
            if (prefs2.getBoolean("10002", false)) {
                dialog_(10002);
            }
            if (prefs2.getBoolean("10003", false)) {
                dialog_(10003);
            }
            if (prefs2.getBoolean("10004", false)) {
                dialog_(10004);
            }
            if (prefs2.getBoolean("10005", false)) {
                dialog_(10005);
            }

        } else {

            String latitude = String.valueOf(prefs.getFloat("latitude", (float) 36.33279));
            String longitude = String.valueOf(prefs.getFloat("longitude", (float) 59.5482514));
            Log.d("loc", latitude + " " + longitude);
            get_weather_temp("lat=" + latitude + "&" + "lon=" + longitude);
            update_server_data.getairPollution(this, MainActivity.mainContext);


        }

    }
}