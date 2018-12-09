
package com.example.behnam.myapplication.activities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.Fragment_mental.Fragment_mental_afsordegi;
import com.example.behnam.myapplication.activities.Fragment_mental.Fragment_mental_ezterab;
import com.example.behnam.myapplication.activities.Fragment_mental.Fragment_mental_stress;
import com.example.behnam.myapplication.activities.Fragment_mental.Fragment_mental_voices;
import com.example.behnam.myapplication.adapters.adapter_mental_ViewPager;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.my_custom_component.ImageBarChartRenderer;
import com.example.behnam.myapplication.my_custom_component.MyMarkerView;
import com.example.behnam.myapplication.my_custom_component.my_BarChart;
import com.example.behnam.myapplication.my_custom_component.my_BarChart_multiple;
import com.example.behnam.myapplication.my_custom_component.my_LinrChart;
import com.example.behnam.myapplication.objects.Chart_data;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarEntry;
import com.takusemba.spotlight.OnSpotlightStateChangedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.Spotlight;
import com.takusemba.spotlight.shape.Circle;
import com.takusemba.spotlight.target.SimpleTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.hamsaa.RtlMaterialSpinner;

import static java.util.stream.Collectors.groupingBy;


public class Fragment_charts extends Fragment {


    FragmentActivity c;
    RtlMaterialSpinner spinner;
    View included_form;
    ImageView ivScroll;
    SlidingTabLayout tabLayout_1;
    ViewPager pager;
    View rootView;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_fragment_charts, container, false);
        setRetainInstance(true);
        c = getActivity();
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("گزارش");

        tabLayout_1 = (SlidingTabLayout) rootView.findViewById(R.id.tl_2);
        pager = (ViewPager) rootView.findViewById(R.id.vp);
        ivScroll = (ImageView) rootView.findViewById(R.id.scrollArrow);
        ivScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayout_1.smoothScrollTo(0, 0);
            }
        });
        included_form = rootView.findViewById(R.id.chart_content);
        initial();
        get_bp();

        return rootView;
    }

    public void initial() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
        String factorDM = prefs.getString("factorDM", "");
        if (factorDM.equals("true")) {
            {
                reload();
                tabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
                                                       @Override
                                                       public void onTabSelect(int position) {
                                                           switch (position) {
                                                               case 6:
                                                                   get_bp();
                                                                   break;
                                                               case 5:
                                                                   get_glu_cig_weight_sleep("glucose");
                                                                   break;
                                                               case 4:
                                                                   get_glu_cig_weight_sleep("weight");
                                                                   break;
                                                               case 3:
                                                                   get_glu_cig_weight_sleep("cigarette");
                                                                   break;
                                                               case 2:
                                                                   get_glu_cig_weight_sleep("sleep");
                                                                   break;
                                                               case 1:
                                                                   get_step_data();
                                                                   break;
                                                               case 0:
                                                                   get_heartrate_data();
                                                                   break;
                                                           }
                                                       }

                                                       @Override
                                                       public void onTabReselect(int position) {

                                                       }
                                                   }
                );

            }

        } else {
            {
                reload2();
                tabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
                                                       @Override
                                                       public void onTabSelect(int position) {
                                                           switch (position) {
                                                               case 5:
                                                                   get_bp();
                                                                   break;

                                                               case 4:
                                                                   get_glu_cig_weight_sleep("weight");
                                                                   break;
                                                               case 3:
                                                                   get_glu_cig_weight_sleep("cigarette");
                                                                   break;
                                                               case 2:
                                                                   get_glu_cig_weight_sleep("sleep");
                                                                   break;
                                                               case 1:
                                                                   get_step_data();
                                                                   break;
                                                               case 0:
                                                                   get_heartrate_data();
                                                                   break;
                                                           }
                                                       }

                                                       @Override
                                                       public void onTabReselect(int position) {

                                                       }
                                                   }
                );

            }

        }
    }

    public void get_step_data() {
        my_BarChart chart_1;
        my_BarChart_multiple chart2;
        ArrayList<BarEntry> yVals;
        ArrayList<String> xVals;
        yVals = new ArrayList<BarEntry>();
        xVals = new ArrayList<String>();
        chart_1 = (my_BarChart) included_form.findViewById(R.id.chart1);
        chart2 = (my_BarChart_multiple) included_form.findViewById(R.id.chart2);
        chart_1.setVisibility(View.VISIBLE);
        chart2.setVisibility(View.GONE);
        ArrayList<Chart_data> chartData = DataBase_read.get_steps(c);
        Collections.reverse(chartData);
        //----------------------------------------
        String s = "s";
        ArrayList<Integer> color = new ArrayList<Integer>();
        //--------------------------------------
        int i = 0;
        for (int j = 0; j < chartData.size(); j++) {
            // xVals.add(d.get(j).getText());
            xVals.add(chartData.get(j).getText());
            yVals.add(new BarEntry(j, (float) (chartData.get(j).getValue()), " شما در تاریخ "));
            Float w = (float) (chartData.get(j).getValue());
            if (w < 3000) {
                color.add(ContextCompat.getColor(c, R.color.yellow));
            } else if (w >= 3000 && w <= 9999) {
                color.add(ContextCompat.getColor(c, R.color.green));
            } else if (w >= 10000) {
                color.add(ContextCompat.getColor(c, R.color.red));
            }
        }


        MyMarkerView mv = new MyMarkerView(c, R.layout.custom_marker_view);
/*
        chart_1.setDrawValueAboveBar(true);
        Bitmap starBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.medal);
        if (chartData.size() > 0) {
            chart_1.setRenderer(new ImageBarChartRenderer(chart_1, chart_1.getAnimator(), chart_1.getViewPortHandler(), starBitmap));
        }
        chart_1.setMarker(mv);

        */

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(c, R.color.green));
        colors.add(ContextCompat.getColor(c, R.color.red));
        colors.add(ContextCompat.getColor(c, R.color.yellow));
        colors.add(ContextCompat.getColor(c, R.color.fbutton_color_orange));
        {
            String[] mTitles3 = {"طبیعی", "بالا", "پایین"};
            Legend l3 = chart_1.getLegend();
            List<LegendEntry> entries3 = new ArrayList<>();
            for (i = 0; i < 3; i++) {
                LegendEntry entry = new LegendEntry();
                entry.formColor = colors.get(i);
                entry.label = mTitles3[i];
                entries3.add(entry);
            }

            l3.setCustom(entries3);
        }
        chart_1.chart_data(xVals, yVals, c, color);
        chart_1.set_prefrence(c.getApplicationContext());

    }

    public void get_heartrate_data() {
        my_BarChart chart_1;
        my_BarChart_multiple chart2;
        ArrayList<BarEntry> yVals;
        ArrayList<String> xVals;
        yVals = new ArrayList<BarEntry>();
        xVals = new ArrayList<String>();
        chart_1 = (my_BarChart) included_form.findViewById(R.id.chart1);
        chart2 = (my_BarChart_multiple) included_form.findViewById(R.id.chart2);
        chart_1.setVisibility(View.VISIBLE);
        chart2.setVisibility(View.GONE);
        ArrayList<Chart_data> chartData = DataBase_read.get_heartrate(c);
        Collections.reverse(chartData);
        //----------------------------------------
        ArrayList<Integer> color2 = new ArrayList<Integer>();
        //--------------------------------------
        for (int j = 0; j < chartData.size(); j++) {
            // xVals.add(d.get(j).getText());
            xVals.add(chartData.get(j).getText());
            yVals.add(new BarEntry(j, (float) (chartData.get(j).getValue()), " شما در تاریخ "));
            int w = (int) (chartData.get(j).getValue());

            if (w >= 80) {
                color2.add(ContextCompat.getColor(c, R.color.red));
            } else if (w >= 50 && w < 80) {
                color2.add(ContextCompat.getColor(c, R.color.green));
            } else if (w < 50) {
                color2.add(ContextCompat.getColor(c, R.color.yellow));
            }
        }


        MyMarkerView mv = new MyMarkerView(c, R.layout.custom_marker_view);
/*
        chart_1.setDrawValueAboveBar(true);
        Bitmap starBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.medal);
        if (chartData.size() > 0) {
            chart_1.setRenderer(new ImageBarChartRenderer(chart_1, chart_1.getAnimator(), chart_1.getViewPortHandler(), starBitmap));
        }
        chart_1.setMarker(mv);

        */
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(c, R.color.green));
        colors.add(ContextCompat.getColor(c, R.color.red));
        colors.add(ContextCompat.getColor(c, R.color.yellow));
        colors.add(ContextCompat.getColor(c, R.color.fbutton_color_orange));
        {
            String[] mTitles3 = {"طبیعی", "بیشتر از حد طبیعی", "پایین تر از حد طبیعی"};
            Legend l3 = chart_1.getLegend();
            List<LegendEntry> entries3 = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                LegendEntry entry = new LegendEntry();
                entry.formColor = colors.get(i);
                entry.label = mTitles3[i];
                entries3.add(entry);
            }

            l3.setCustom(entries3);
        }

        chart_1.chart_data(xVals, yVals, c, color2);
        chart_1.set_prefrence(c.getApplicationContext());

    }

    public void get_bp() {
        my_BarChart chart2;
        my_BarChart_multiple chart_1;
        ArrayList<BarEntry> yVals;
        ArrayList<BarEntry> yVals2;
        ArrayList<String> xVals;
        yVals = new ArrayList<BarEntry>();
        yVals2 = new ArrayList<BarEntry>();
        xVals = new ArrayList<String>();
        chart_1 = (my_BarChart_multiple) included_form.findViewById(R.id.chart2);
        chart2 = (my_BarChart) included_form.findViewById(R.id.chart1);
        chart_1.setVisibility(View.VISIBLE);
        chart2.setVisibility(View.GONE);

        ArrayList<Chart_data> chartData = DataBase_read.get_bp(c);
        Collections.reverse(chartData);
        //----------------------------------------
        ArrayList<Integer> color = new ArrayList<Integer>();
        //--------------------------------------
        int i = 0;
        for (int j = 0; j < chartData.size(); j++) {
            // xVals.add(d.get(j).getText());
            xVals.add(chartData.get(j).getText());
            yVals.add(new BarEntry(j, (float) (chartData.get(j).getValue()), " شما در تاریخ "));
            yVals2.add(new BarEntry(j, (float) (chartData.get(j).getValue2()), " شما در تاریخ "));
        }


        MyMarkerView mv = new MyMarkerView(c, R.layout.custom_marker_view);

        chart_1.setDrawValueAboveBar(true);
        Bitmap starBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.medal);
        if (chartData.size() > 0) {
            chart_1.setRenderer(new ImageBarChartRenderer(chart_1, chart_1.getAnimator(), chart_1.getViewPortHandler(), starBitmap));
        }
        chart_1.setMarker(mv);
        chart_1.chart_data(xVals, yVals, yVals2, c, color);
        chart_1.set_prefrence(c.getApplicationContext());

    }

    public void get_glu_cig_weight_sleep(String item) {
        my_BarChart chart_1;
        my_BarChart_multiple chart2;
        ArrayList<BarEntry> yVals;
        ArrayList<String> xVals;
        yVals = new ArrayList<BarEntry>();
        xVals = new ArrayList<String>();
        chart_1 = (my_BarChart) included_form.findViewById(R.id.chart1);
        chart2 = (my_BarChart_multiple) included_form.findViewById(R.id.chart2);
        chart_1.setVisibility(View.VISIBLE);
        chart2.setVisibility(View.GONE);
        ArrayList<Chart_data> chartData = DataBase_read.get_glu_cig_weight_sleep(c, item);
        Collections.reverse(chartData);
        //----------------------------------------
        ArrayList<Integer> color = ChartColors_glu_cig_weight_sleep(chartData, item, chart_1);
        //--------------------------------------
        int i = 0;
        for (int j = 0; j < chartData.size(); j++) {

            xVals.add(chartData.get(j).getText());
            yVals.add(new BarEntry(j, (float) (chartData.get(j).getValue()), " شما در تاریخ "));


        }
       /*  MyMarkerView mv = new MyMarkerView(c, R.layout.custom_marker_view);

        //chart_1.setDrawValueAboveBar(true);
  Bitmap starBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.medal);
    if (chartData.size() > 0) {
        chart_1.setRenderer(new ImageBarChartRenderer(chart_1, chart_1.getAnimator(), chart_1.getViewPortHandler(), starBitmap));
    }
    chart_1.setMarker(mv);
*/
        chart_1.chart_data(xVals, yVals, c, color);
        chart_1.set_prefrence2(c.getApplicationContext());
    }

    public ArrayList<Integer> ChartColors_glu_cig_weight_sleep(ArrayList<Chart_data> chartData, String item, my_BarChart chart_1) {
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(c, R.color.green));
        colors.add(ContextCompat.getColor(c, R.color.red));
        colors.add(ContextCompat.getColor(c, R.color.yellow));
        colors.add(ContextCompat.getColor(c, R.color.fbutton_color_orange));
        ArrayList<Integer> color = new ArrayList<Integer>();
        switch (item) {

            case "glucose":
                for (int j = 0; j < chartData.size(); j++) {
                    if (chartData.get(j).getValue() < 180) {
                        color.add(ContextCompat.getColor(c, R.color.green));
                    } else if (chartData.get(j).getValue() >= 180) {
                        color.add(ContextCompat.getColor(c, R.color.red));
                    }

                }
                String[] mTitles = {"مطلوب", "بالا"};
                Legend l = chart_1.getLegend();
                List<LegendEntry> entries = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    LegendEntry entry = new LegendEntry();
                    entry.formColor = colors.get(i);
                    entry.label = mTitles[i];
                    entries.add(entry);
                }

                l.setCustom(entries);
                return color;
            case "cigarette":
                for (int j = 0; j < chartData.size(); j++) {
                    if (chartData.get(j).getValue() == 0) {
                        color.add(ContextCompat.getColor(c, R.color.green));
                    } else if (chartData.get(j).getValue() > 0) {
                        color.add(ContextCompat.getColor(c, R.color.red));
                    }

                }
                String[] mTitles2 = {"مفید", "مضر"};
                Legend l2 = chart_1.getLegend();
                List<LegendEntry> entries2 = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    LegendEntry entry = new LegendEntry();
                    entry.formColor = colors.get(i);
                    entry.label = mTitles2[i];
                    entries2.add(entry);
                }

                l2.setCustom(entries2);
                return color;
            case "weight":
                for (int j = 0; j < chartData.size(); j++) {
                    float w = chartData.get(j).getValue();
                    String height = DataBase_read.get_height(MainActivity.mainContext);
                    w = w / (Float.parseFloat(height) * Float.parseFloat(height));
                    if (w < 18.5) {
                        color.add(ContextCompat.getColor(c, R.color.yellow));
                    } else if (w >= 18.5 && w < 25) {
                        color.add(ContextCompat.getColor(c, R.color.green));
                    } else if (w >= 25 && w < 30) {
                        color.add(ContextCompat.getColor(c, R.color.fbutton_color_orange));
                    } else if (w >= 30) {
                        color.add(ContextCompat.getColor(c, R.color.red));
                    }
                }
            {
                String[] mTitles3 = {"حد طبیعی", "چاق", "کمتر از حد طبیعی", "اضافه وزن"};
                Legend l3 = chart_1.getLegend();
                List<LegendEntry> entries3 = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    LegendEntry entry = new LegendEntry();
                    entry.formColor = colors.get(i);
                    entry.label = mTitles3[i];
                    entries3.add(entry);
                }

                l3.setCustom(entries3);
            }
            return color;
            case "sleep":
                for (int j = 0; j < chartData.size(); j++) {
                    float w = chartData.get(j).getValue();
                    if (w < 7) {
                        color.add(ContextCompat.getColor(c, R.color.yellow));
                    } else if (w >= 7 && w <= 8) {
                        color.add(ContextCompat.getColor(c, R.color.green));
                    } else if (w > 8) {
                        color.add(ContextCompat.getColor(c, R.color.red));
                    }
                }
            {
                String[] mTitles3 = {"مطلوب", "زیاد", "کم"};
                Legend l3 = chart_1.getLegend();
                List<LegendEntry> entries3 = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    LegendEntry entry = new LegendEntry();
                    entry.formColor = colors.get(i);
                    entry.label = mTitles3[i];
                    entries3.add(entry);
                }

                l3.setCustom(entries3);
            }
            return color;


        }
        return color;
    }

    public void reload() {
        mFragments.clear();

        final String[] mTitles = {"ضربان قلب", "تعداد قدم", "خواب", "سیگار", "وزن", "قند خون", "فشار خون"};
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        adapter_mental_ViewPager adapter = new adapter_mental_ViewPager(getChildFragmentManager(), mFragments, mTitles);
        pager.setAdapter(adapter);
        tabLayout_1.setViewPager(pager);
        pager.setCurrentItem(6);
        // c.getFragmentManager().beginTransaction().detach().attach(c).commit();
    }

    public void reload2() {
        mFragments.clear();
        final String[] mTitles = {"ضربان قلب", "تعداد قدم", "خواب", "سیگار", "وزن", "فشار خون"};

        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        mFragments.add(new Fragment_empty());
        adapter_mental_ViewPager adapter = new adapter_mental_ViewPager(getChildFragmentManager(), mFragments, mTitles);
        pager.setAdapter(adapter);
        tabLayout_1.setViewPager(pager);
        pager.setCurrentItem(5);

        // c.getFragmentManager().beginTransaction().detach().attach(c).commit();
    }
}

