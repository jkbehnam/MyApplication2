package com.example.behnam.myapplication.my_custom_component;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.behnam.myapplication.R;


/**
 * Created by behnam on 4/18/2018.
 */

public class my_AHBottomNavigation extends AHBottomNavigation {
    public my_AHBottomNavigation(Context context) {
        super(context);
        this.createNavItems();
    }

    public my_AHBottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.createNavItems();
    }

    public my_AHBottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.createNavItems();
    }
    private void createNavItems() {

        //CREATE ITEMS
        AHBottomNavigationItem infoItem = new AHBottomNavigationItem(R.string.setting, R.drawable.exercise_icon_setting_navigationbar, R.color.colorBottomNavigationInactive);
        AHBottomNavigationItem profileItem = new AHBottomNavigationItem(R.string.profile, R.drawable.exercise_icon_profile_navigationbar, R.color.colorBottomNavigationAccent);
        AHBottomNavigationItem homeItem = new AHBottomNavigationItem(R.string.home, R.drawable.exercise_icon_home_navigationbar, R.color.colorBottomNavigationAccent);
        AHBottomNavigationItem messageItem = new AHBottomNavigationItem(R.string.message, R.drawable.exercise_icon_message_navigationbar, R.color.colorBottomNavigationAccent);
        AHBottomNavigationItem chartsItem = new AHBottomNavigationItem(R.string.chart, R.drawable.exercise_icon_chart_navigationbar, R.color.colorBottomNavigationAccent);

        //ADD THEM to bar
        this.addItem(infoItem);
        this.addItem(chartsItem);
        this.addItem(homeItem);
        this.addItem(messageItem);
        this.addItem(profileItem);

//this.setAccentColor(R.color.colorBottomNavigationAccent);
        //set properties


        this.setAccentColor(getContext().getResources().getColor(R.color.colorPrimary));
        this.setInactiveColor(Color.parseColor("#747474"));
        //set current item

        this.setForceTint(true);
        this.setTranslucentNavigationEnabled(false);
        this.setTitleState(TitleState.ALWAYS_SHOW);

        //this.setNotification("1", 3);



        this.setBehaviorTranslationEnabled(false);
        this.setBackgroundColor(Color.parseColor("#f5f5f5"));
        this.setDefaultBackgroundColor(Color.parseColor("#f5f5f5"));

    }

}
