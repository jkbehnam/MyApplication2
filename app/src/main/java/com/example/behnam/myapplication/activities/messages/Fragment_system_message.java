
package com.example.behnam.myapplication.activities.messages;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_system_message_recycle;
import com.example.behnam.myapplication.database_pack.DataBase;
import com.example.behnam.myapplication.database_pack.DataBase_issend;
import com.example.behnam.myapplication.objects.Daily_system_massage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;


public class Fragment_system_message extends Fragment {
    ArrayList<Daily_system_massage> dv_list;
    FragmentActivity c;
    static Fragment_system_message fh;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_fragment_system_message_new, container, false);
        setRetainInstance(true);
        c = getActivity();
        fh = this;


        rv = (RecyclerView) rootView.findViewById(R.id.recycle_);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(c);
        rv.setLayoutManager(mLayoutManager);
        rv.setNestedScrollingEnabled(false);

        dv_list = new ArrayList<>();

        Daily_system_massage dv = null;
        Cursor cr = DataBase.getInstance(c).getDb().rawQuery("select * from system_message order by id", null);
        cr.moveToFirst();

        adapter_system_message_recycle madapter = null;
        cr.moveToFirst();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
        Long start_date = prefs.getLong("start_date", 0);
        long daysDiff = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - start_date);

        int i = 0;

        if (cr.getCount() != 0) {
            do {
                dv = new Daily_system_massage();
                dv.setQuestion(cr.getString(cr.getColumnIndex("title")));
                  dv.setCode(cr.getInt(cr.getColumnIndex("is_read")));
                dv.setDay(cr.getInt(cr.getColumnIndex("id")));
               // i = cr.getInt(cr.getColumnIndex("id"));
                dv.setAnswer(cr.getString(cr.getColumnIndex("content")));
                dv_list.add(dv);
                i++;
            } while (cr.moveToNext() && (daysDiff > i));
            //} while ( cr.moveToNext() );
        }
        Collections.reverse(dv_list);
        madapter = new adapter_system_message_recycle(dv_list);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        alphaAdapter.setFirstOnly(true);
        rv.setAdapter(alphaAdapter);

        DataBase_issend.system_messages_isread(MainActivity.mainContext,dv_list);
        return rootView;

    }



}