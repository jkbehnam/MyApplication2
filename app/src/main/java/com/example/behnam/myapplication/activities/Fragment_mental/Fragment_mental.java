
package com.example.behnam.myapplication.activities.Fragment_mental;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_home;
import com.example.behnam.myapplication.adapters.adapter_mental_ViewPager;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Calendar;

import info.hoang8f.widget.FButton;


public class Fragment_mental extends Fragment {

    private final String[] mTitles = {"موسیقی", "استرس", "اضطراب", "افسردگی"};
    SlidingTabLayout tabLayout_1;
    ViewPager pager;
    FragmentActivity c;
    static Fragment_mental fh;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    Long startActivity, endActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_fragment_mental, container, false);
        setRetainInstance(true);
        startActivity = System.currentTimeMillis();
        c = getActivity();
        fh = this;
        tabLayout_1 = (SlidingTabLayout) rootView.findViewById(R.id.tl_1);
        pager = (ViewPager) rootView.findViewById(R.id.vp);
        reload();
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        FButton tv_save = (FButton) rootView.findViewById(R.id.btn_save);
        tvToolbarTitle.setText("حالات روحی و آرام سازی");

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment s = mFragments.get(pager.getCurrentItem());
                if (s instanceof Fragment_mental_stress) {
                    //((Fragment_mental_stress)s).save();
                } else if (s instanceof Fragment_mental_ezterab)
                    ((Fragment_mental_ezterab) s).save();
                else if (s instanceof Fragment_mental_afsordegi)
                    ((Fragment_mental_afsordegi) s).save();
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Fragment s = mFragments.get(pager.getCurrentItem());
                if (s instanceof Fragment_mental_stress) {
                    tv_save.setVisibility(View.GONE);
                } else if (s instanceof Fragment_mental_ezterab)
                    tv_save.setVisibility(View.VISIBLE);
                else if (s instanceof Fragment_mental_afsordegi)
                    tv_save.setVisibility(View.VISIBLE);
                else if (s instanceof Fragment_mental_voices)
                    tv_save.setVisibility(View.GONE);
            }

            @Override
            public void onPageSelected(int position) {
                Fragment s = mFragments.get(pager.getCurrentItem());
                if (s instanceof Fragment_mental_stress) {
                    tv_save.setVisibility(View.GONE);
                } else if (s instanceof Fragment_mental_ezterab)
                    tv_save.setVisibility(View.VISIBLE);
                else if (s instanceof Fragment_mental_afsordegi)
                    tv_save.setVisibility(View.VISIBLE);
                else if (s instanceof Fragment_mental_voices)
                    tv_save.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ImageView iv_cancel = (ImageView) rootView.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = null;
                fragment = new Fragment_home();
                transaction.add(R.id.container, fragment);
                transaction.commit();
            }
        });

        return rootView;
    }

    public void reload() {
        mFragments.clear();
        mFragments.add(new Fragment_mental_voices());
        mFragments.add(new Fragment_mental_stress());
        mFragments.add(new Fragment_mental_ezterab());
        mFragments.add(new Fragment_mental_afsordegi());
        adapter_mental_ViewPager adapter = new adapter_mental_ViewPager(getChildFragmentManager(), mFragments, mTitles);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        tabLayout_1.setViewPager(pager);
        pager.setCurrentItem(3);
        // c.getFragmentManager().beginTransaction().detach().attach(c).commit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            Toast.makeText(c, "left", Toast.LENGTH_SHORT).show();
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Toast.makeText(c, "left", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(c, "come back", Toast.LENGTH_SHORT).show();
                // do what you like
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment fragment = new Fragment_home();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.container, fragment);

                    transaction.commit();
                    return true;
                }
                return false;
            }
        });
    }



    public void save_logs() {
        endActivity = System.currentTimeMillis();
        long timeSpend = endActivity - startActivity;
        Calendar cal = Calendar.getInstance();
        DataBase_write.save_action_log("3_report_page", cal.getTimeInMillis(), timeSpend);
    }
}

