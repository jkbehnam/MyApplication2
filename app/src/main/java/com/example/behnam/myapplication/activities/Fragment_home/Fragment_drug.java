
package com.example.behnam.myapplication.activities.Fragment_home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_drug_recycle;
import com.example.behnam.myapplication.adapters.adapter_main_ViewPager;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.druges;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_drug extends Fragment {
    ArrayList<String> name;
    ArrayList<String> id;
    RecyclerView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_fragment_drug, container, false);
        setRetainInstance(true);
        final FragmentActivity c = getActivity();
        name = new ArrayList<>();
        id = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
       /* String drugs = prefs.getString("DrugList", "");
        JSONArray us = null;
        try {
            us = new JSONArray(drugs);
            for (int i = 0; i < us.length(); i++) {
                JSONObject e = us.getJSONObject(i);
                name.add(e.getString("name"));
                id.add(e.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        ArrayList<String> list2 = DataBase_read.get_drugs(MainActivity.mainContext);
        for (String s : list2
                ) {
            JSONObject obj = null;
            try {
                JSONArray j=new JSONArray(s);
                 obj = j.getJSONObject(0);
                //obj = new JSONObject(s);
                druges d = druges.getInstance();
                name.add(obj.getString("name"));
                Log.d("drg", obj.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        adapter_drug_recycle adapter = new adapter_drug_recycle(this.name);
        list = (RecyclerView) rootView.findViewById(R.id.list_drug);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);

        adapter.setOnCardClickListner(new adapter_drug_recycle.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = null;
                fragment = new Fragment_drug_desc();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                Log.d("listpos", String.valueOf(position));
// set Fragmentclass Arguments
                fragment.setArguments(bundle);
                transaction.add(R.id.container, fragment);
                transaction.commit();
            }
        });

        Toolbar mToolbar = (Toolbar) rootView.findViewById(R.id.tlb);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("MyTitle");
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("دارو");

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment_home();
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
}