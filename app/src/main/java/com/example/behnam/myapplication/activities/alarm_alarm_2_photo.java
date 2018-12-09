package com.example.behnam.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.Calendar;
import java.util.TimeZone;

import at.markushi.ui.CircleButton;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class alarm_alarm_2_photo extends AppCompatActivity {
    Ringtone r;
    LinearLayout layText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_heart_alarm_photo);
        ImageView iv_alarm=(ImageView)findViewById(R.id.heartAlarmPhoto) ;
        TextView tv_alarm=(TextView)findViewById(R.id.heartAlarmtext);
        layText=(LinearLayout)findViewById(R.id.layText);
        tv_alarm.setText("ضربان قلب شما بالاست. لطفا تدریجا فعالیت خود را کم کنید و با سرعت و شدت کمتری فعالیت را ادامه دهید. در صورتی که علائمی چون درد شدید در قفسه سینه دارید فورافعالیت خود را متوقف کنید، استراحت کنید. به این طریق عمل کنید. بعد از رفع مشکل علائم خود را ثبت کنید. ");
        iv_alarm.setVisibility(View.GONE);

        tv_alarm.setText(Html.fromHtml("ضربان قلب شما بالاست. لطفا تدریجا فعالیت خود را کم کنید و با سرعت و شدت کمتری فعالیت را ادامه دهید. در صورتی که علائمی چون درد شدید در قفسه سینه دارید فورافعالیت خود را متوقف کنید، استراحت کنید." + "<font color=blue>" + " به این طریق عمل کنید." + "</font><br><br>"
                +" بعد از رفع مشکل علائم خود را ثبت کنید. "));

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
        tv_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_alarm.setVisibility(View.VISIBLE);
                layText.setVisibility(View.GONE);
                r.stop();

            }
        });
        iv_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//---------------------------
       getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);




        //------cancel alarm---------------------------------------------------------


    }

    @Override
    public void onBackPressed() {
        r.stop();

        finish();
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
}
