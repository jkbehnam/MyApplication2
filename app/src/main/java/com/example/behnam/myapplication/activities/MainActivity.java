package com.example.behnam.myapplication.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.behnam.myapplication.broadcast_reciver.NetworkChangeReceiver;
import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.adapters.adapter_main_ViewPager;

import com.example.behnam.myapplication.database_pack.DataBase_login;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.my_custom_component.my_AHBottomNavigation;
import com.example.behnam.myapplication.send_notification_strategy;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    public static Context mainContext;


    my_AHBottomNavigation bottomNavigation;
    public static String PACKAGE_NAME;
    public AHBottomNavigationViewPager viewPager;
    adapter_main_ViewPager adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        PACKAGE_NAME = getApplicationContext().getPackageName();
        setContentView(R.layout.exercise_activity_main);
        mainContext = this;

        mainContext.getApplicationContext().registerReceiver(new NetworkChangeReceiver(),
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));




        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/iran_sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

     //  Intent i2 = new Intent(MainActivity.this, alarm_alarm.class);
       // startActivity(i2);

       if (!DataBase_login.islogin(this)) {
            Intent i = new Intent(MainActivity.this, Login_acirivty.class);
            startActivity(i);
            this.finish();

        }




        viewPager = findViewById(R.id.viewPagerMain);
//--------------------------------------------------------------
        bottomNavigation = (my_AHBottomNavigation) findViewById(R.id.bottom_navigation);
        viewPager.setOffscreenPageLimit(5);
        adapter = new adapter_main_ViewPager(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        bottomNavigation.setCurrentItem(2);
        viewPager.setCurrentItem(2, true);

        bottomNavigation.setNotification(String.valueOf(DataBase_read.getnewmessage_number(MainActivity.mainContext)), 3);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (viewPager.getCurrentItem() == position) {
                    //  adapter.set_clear();
                    viewPager.setAdapter(adapter);
                }
                viewPager.setCurrentItem(position, true);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();
        }

        //---------------------------------------------------------------

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermissions() {
        List<String> wantedPermissions = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.BLUETOOTH);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.BLUETOOTH_ADMIN);
      /*  if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.READ_CONTACTS);
            */
     //   if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED)
      //      wantedPermissions.add(Manifest.permission.CALL_PHONE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.READ_PHONE_STATE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.PROCESS_OUTGOING_CALLS);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.RECEIVE_SMS);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.READ_SMS);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.SEND_SMS);
            */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_DENIED)
            wantedPermissions.add(Manifest.permission.READ_CALENDAR);
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.MEDIA_CONTENT_CONTROL) == PackageManager.PERMISSION_DENIED)
                wantedPermissions.add(Manifest.permission.MEDIA_CONTENT_CONTROL);
        } catch (Exception ignored) {
        }

        if (!wantedPermissions.isEmpty())
            ActivityCompat.requestPermissions(this, wantedPermissions.toArray(new String[wantedPermissions.size()]), 0);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    public interface IOnBackPressed {
        /**
         * If you return true the back press will not be taken into account, otherwise the activity will act naturally
         *
         * @return true if your processing has priority if not false
         */
        boolean onBackPressed();
    }

}
