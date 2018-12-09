
package com.example.behnam.myapplication.activities.Fragment_home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.connect_to_server.update_server_data;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.example.behnam.myapplication.druges;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class Fragment_drug_desc extends Fragment {
    TextView tv_number, tv_how_to_use, tv_title, tv_drug_name;
    com.codesgood.views.JustifiedTextView tv_content;
    Long startActivity, endActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercise_drug_desc, container, false);
startActivity= System.currentTimeMillis();
        Toolbar  mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("MyTitle");
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("جزئیات دارو");
        tv_drug_name = (TextView) rootView.findViewById(R.id.drug_name);
        tv_number = (TextView) rootView.findViewById(R.id.tv_number);
        tv_how_to_use = (TextView) rootView.findViewById(R.id.tv_how_to_use);
        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        tv_content = (com.codesgood.views.JustifiedTextView) rootView.findViewById(R.id.tv_content);


        ArrayList<String> list=DataBase_read.get_drugs(MainActivity.mainContext);
        int position = getArguments().getInt("position");
        JSONObject obj = null;
        try {
            JSONArray j=new JSONArray(list.get(position));
            obj = j.getJSONObject(0);

            druges d = druges.getInstance();
            tv_drug_name.setText(obj.getString("name"));
            tv_number.setText(obj.getString("total")+" عدد");
            String HowToUse="هر "+obj.getString("timeUnit")+" به اندازه "+obj.getString("number")+" عدد "+obj.getString("drugType")+" مصرف شود";
            tv_how_to_use.setText(HowToUse);
            for(int i=0;i<d.getD_list().size();i++){
                if(obj.getString("typeFa").equals(d.getD_list().get(i).getDrug_name())||obj.getString("typeEn").equals(d.getD_list().get(i).getDrug_code()
                )){
                    tv_title.setText(d.getD_list().get(i).getDrug_name());
                    tv_content.setText(d.getD_list().get(i).getDrug_describe());
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment_drug();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.container, fragment);
                transaction.commit();
            }
        });




        setRetainInstance(true);
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
                    save_logs();
                    Fragment fragment = new Fragment_drug();
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
        DataBase_write.save_action_log("Drugs", cal.getTimeInMillis(), timeSpend);
    }
}