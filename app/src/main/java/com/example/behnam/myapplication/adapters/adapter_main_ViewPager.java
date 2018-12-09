package com.example.behnam.myapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.behnam.myapplication.activities.Fragment_charts;

import com.example.behnam.myapplication.activities.Fragment_home.main_home;
import com.example.behnam.myapplication.activities.messages.Fragment_message;
import com.example.behnam.myapplication.activities.Fragment_profile;
import com.example.behnam.myapplication.activities.settring.Fragment_setting;
import com.example.behnam.myapplication.activities.settring.Fragment_setting_home;

import java.util.ArrayList;



/**
 * Created by Mohammad on 29/11/2017.
 */

public  class  adapter_main_ViewPager extends FragmentStatePagerAdapter {
    Fragment_setting_home infoFragment = new Fragment_setting_home();
    Fragment_charts chartsFragment = new Fragment_charts();
    main_home homeFragment = new main_home();
    Fragment_message messageFragment = new Fragment_message();
    Fragment_profile progileFragment = new Fragment_profile();


   static adapter_main_ViewPager instans;
    public ArrayList<Fragment> fragments = new ArrayList<>();
    public ArrayList<Fragment> fragments_main = new ArrayList<>();
    public adapter_main_ViewPager(FragmentManager fm) {
        super(fm);



        fragments.clear();
        fragments.add(infoFragment);
        fragments.add(chartsFragment);
        fragments.add(homeFragment);
        fragments.add(messageFragment);
        fragments.add(progileFragment);
/*
        fragments_main.clear();
        fragments_main.add(infoFragment);
        fragments_main.add(chartsFragment);
        fragments_main.add(homeFragment);
        fragments_main.add(messageFragment);
        fragments_main.add(progileFragment);

*/

    }
    public static adapter_main_ViewPager getInstance(FragmentManager fm)
    {
        if (instans==null)
            instans = new adapter_main_ViewPager(fm);
        return instans;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
public void set_clear(){
    fragments.clear();
    fragments.add(infoFragment);
    fragments.add(chartsFragment);
    fragments.add(homeFragment);
    fragments.add(messageFragment);
    fragments.add(progileFragment);


}


}