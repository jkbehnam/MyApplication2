
package com.example.behnam.myapplication.activities.Fragment_home;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_signs_list;
import com.example.behnam.myapplication.database_pack.DataBase;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.example.behnam.myapplication.objects.Sign;
import com.example.behnam.myapplication.set_alarm_notify;
import com.nshmura.snappysmoothscroller.SnapType;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import info.hoang8f.widget.FButton;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;



public class Fragment_signs extends Fragment {
    FragmentActivity c;
    View rootView;
    ArrayList<Sign> list1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView=inflater.inflate(R.layout.exercise_fragment_signs,container,false);
        setRetainInstance(true);
        c = getActivity();

        Toolbar toolbar = (Toolbar)rootView. findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("علائم");
        FButton tv_save = (FButton) rootView.findViewById(R.id.btn_save);
        tv_save.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("علائم");
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
                Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();
                DataBase_write.write_signs(list1,cal.getTimeInMillis()/1000);

                cal.add(Calendar.DATE,2);
                clearNotification(10003);
                set_alarm_notify.cancel_alarm(MainActivity.mainContext,10003);
                set_alarm_notify.set_alarm_repeat(MainActivity.mainContext,10003,cal);
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                prefsEditor.putBoolean("10003", false);
                prefsEditor.putLong("10003cal", cal.getTimeInMillis());
                prefsEditor.commit();
                Fragment fragment = new Fragment_home();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.container, fragment);
                transaction.commit();
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
        initial_signs();
        return rootView;
    }
    public void initial_signs() {


       list1 = new ArrayList<>();
        Sign main_item = new Sign();
        main_item.setTitle("تنفس سریع");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("سرگیجه غیر طبیعی");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("درد قفسه سینه");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("تنگی نفس");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("گرفتگی عضلانی");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("خستگی زیاد");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("تپش قلب");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("سرفه زیاد");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("تهوع");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("تاری دید");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("عرق سرد");
        list1.add(main_item);
        main_item = new Sign();
        main_item.setTitle("لنگی پا( خالی کردن زانو)");
        list1.add(main_item);


        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.state_chbox);
        SnappyLinearLayoutManager sllm=new SnappyLinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        sllm.setSnapType(SnapType.CENTER);
        sllm.setSnapInterpolator(new DecelerateInterpolator());
        recyclerView.setLayoutManager(sllm);

        adapter_signs_list madapter = new adapter_signs_list(list1);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);

        recyclerView.setAdapter(alphaAdapter);
        recyclerView.invalidate();
        recyclerView.setNestedScrollingEnabled(false);
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
    public void clearNotification(int id) {
        NotificationManager notificationManager = (NotificationManager) c .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

}