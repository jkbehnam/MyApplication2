
package com.example.behnam.myapplication.activities.messages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.adapters.adapter_mental_ViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;


public class Fragment_message extends Fragment {
    private final String[] mTitles = {"پیام سیستم", "ارتباط با پرستار"
    };
    SlidingTabLayout tabLayout_1;
    ViewPager pager;
    FragmentActivity c;
    static Fragment_message fh;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.exercise_fragment_message,container,false);
        setRetainInstance(true);
        c = getActivity();

        fh=this;
        tabLayout_1 = (SlidingTabLayout) rootView.findViewById(R.id.tl_1);
        pager = (ViewPager) rootView.findViewById(R.id.vp);

        Toolbar toolbar = (Toolbar)rootView. findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("پیام ها");
        return rootView;
    }
    public void reload() {
        mFragments.clear();
        mFragments.add(new Fragment_system_message());
        mFragments.add(new Fragment_doctor_message());
        adapter_mental_ViewPager adapter = new adapter_mental_ViewPager(getChildFragmentManager(), mFragments, mTitles);

        pager.setAdapter(adapter);
        tabLayout_1.setViewPager(pager);
        pager.setCurrentItem(0);
        // c.getFragmentManager().beginTransaction().detach().attach(c).commit();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
               // Toast.makeText(c, "not_see", Toast.LENGTH_SHORT).show();
            } else {
                reload();

              //  Toast.makeText(c, "see", Toast.LENGTH_SHORT).show();
                // do what you like
            }
        }
    }
}