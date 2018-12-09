
package com.example.behnam.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_drug_desc;
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_home;
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_trianing;
import com.example.behnam.myapplication.adapters.adapter_drug_recycle;
import com.example.behnam.myapplication.connect_to_server.URLs;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.example.behnam.myapplication.druges;

import java.util.ArrayList;
import java.util.Calendar;

import nodomain.freeyourgadget.gadgetbridge.database_pack.PlayerActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Fragment_video extends Fragment {
    ArrayList<String> name;
    ArrayList<String> url;
    RecyclerView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_fragment_drug, container, false);
        setRetainInstance(true);
        final FragmentActivity c = getActivity();
        name=new ArrayList<>();
        url=new ArrayList<>();

        name.add("اندازه گیری فشار خون در منزل");
        url.add(URLs.ROOT_URL+"/blood_pressure_measurement.mp4");
        name.add("عملیات احیاء قلبی ریوی");
        url.add(URLs.ROOT_URL+"/pulmonary_cardiopulmonary_resuscitation.mp4");
        adapter_drug_recycle adapter = new adapter_drug_recycle(this.name);
        list = (RecyclerView) rootView.findViewById(R.id.list_drug);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);

        adapter.setOnCardClickListner(new adapter_drug_recycle.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                String videoUrl = url.get(position).trim();
                startActivity(nodomain.freeyourgadget.gadgetbridge.database_pack.PlayerActivity.getStartingIntent(MainActivity.mainContext,videoUrl));
            }
        });

        Toolbar  mToolbar = (Toolbar) rootView.findViewById(R.id.tlb);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("MyTitle");
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("فیلم های آموزشی");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      Fragment fragment = new Fragment_trianing();
                                                      FragmentManager manager = getActivity().getSupportFragmentManager();
                                                      FragmentTransaction transaction = manager.beginTransaction();
                                                      transaction.add(R.id.container, fragment);
                                                      transaction.commit();
                                                  }
                                              });
                //-----------------------------------------------------------------------

        return rootView;
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

                    Fragment fragment = new Fragment_trianing();
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

}