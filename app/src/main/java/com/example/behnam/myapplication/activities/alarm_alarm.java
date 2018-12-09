package com.example.behnam.myapplication.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import at.markushi.ui.CircleButton;

import saman.zamani.persiandate.PersianDate;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class alarm_alarm extends AppCompatActivity {
    int n = 0;
    int duration;
    Intent i;
    View included_form, included_form2;
    public MultiLineRadioGroup mrg;
    CircleButton submit, cancel;
    Ringtone r;
    LinearLayout howmuch, main_lay, howmuch2;
    TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_heart_alarm);
        submit = (CircleButton) findViewById(R.id.button1);
        cancel = (CircleButton) findViewById(R.id.button2);
        howmuch = (LinearLayout) findViewById(R.id.howmuch);
        howmuch2 = (LinearLayout) findViewById(R.id.howmuch2);
        howmuch.setVisibility(View.GONE);
        howmuch2.setVisibility(View.GONE);
        included_form = (View) findViewById(com.example.behnam.myapplication.R.id.hm);
        included_form2 = (View) findViewById(com.example.behnam.myapplication.R.id.hm2);
        tv_time = (TextView) findViewById(R.id.tv_time_show);
        Intent intent = getIntent();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(intent.getLongExtra("HR_time", 0));
        tv_time.setText(cal.getTime().toString());

        mrg = (MultiLineRadioGroup) included_form.findViewById(R.id.main_activity_multi_line_radio_group);
        mrg.setMaxInRow(1);
        mrg.addButtons(0, "حدود 5 دقیقه");
        mrg.addButtons(1, "حدود 15 دقیقه");
        mrg.addButtons(2, "حدود 30 دقیقه");
        mrg.checkAt(0);
        main_lay = (LinearLayout) findViewById(R.id.main_lay);
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 250000);

        Button howmuch1b = (Button) included_form.findViewById(R.id.save);
        howmuch1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DataBase_write.save_action_exer_duration(calendar.getTimeInMillis() / 1000, index_to_min(mrg.getCheckedRadioButtonIndex()),"1");
                finish();
            }
        });
        Button howmuch1b2 = (Button) included_form2.findViewById(R.id.save);
        howmuch1b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefsEditor = prefs1.edit();
                prefsEditor.putInt("inSport", 1);

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));

                cal.add(Calendar.MINUTE, 60);

                prefsEditor.putLong("60min", cal.getTimeInMillis());
                prefsEditor.commit();
                r.stop();
                howmuch.setVisibility(View.VISIBLE);
                main_lay.setVisibility(View.GONE);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefsEditor = prefs1.edit();
                prefsEditor.putInt("inSport", 0);

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
                DataBase_write.save_action_exer_duration(cal.getTimeInMillis() / 1000, 0,"0");

                cal.add(Calendar.MINUTE, 30);
                prefsEditor.putLong("60min", cal.getTimeInMillis());
                prefsEditor.commit();
                howmuch2.setVisibility(View.VISIBLE);
                main_lay.setVisibility(View.GONE);
                r.stop();

            }
        });
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/iran_sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
//---------------------------
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        i = getIntent();


        //------cancel alarm---------------------------------------------------------


    }

    @Override
    public void onBackPressed() {

        finish();
        r.stop();
    }

    public class myPhoneStateChangeListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public int index_to_min(int i) {
        switch (i) {
            case 0:
                return 5;
            case 1:
                return 15;
            case 2:
                return 30;

        }
        return 5;
    }

}
