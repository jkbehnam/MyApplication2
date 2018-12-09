package com.example.behnam.myapplication.activities.Fragment_home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.VideoView;


import com.codesgood.views.JustifiedTextView;
import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_list_animation;
import com.example.behnam.myapplication.adapters.adapter_string_recycle;
import com.example.behnam.myapplication.adapters.adapter_system_message_recycle;
import com.example.behnam.myapplication.database_pack.DataBase;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.objects.Object_animation_list;
import com.example.behnam.myapplication.objects.Animation;
import com.nshmura.snappysmoothscroller.SnapType;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;

import info.hoang8f.widget.FButton;


public class Dialog_befor_exercise {
    FButton submit, cancel;

    Context context;
    Dialog dialog;
    ArrayList<String> dv_list;

    public CheckBox title_chbox;
    @SuppressLint("ResourceAsColor")
    public Dialog qrcode_reader(Context context,FragmentActivity a) {
        this.context = context;

        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog = new Dialog(context, android.R.style.Theme_Holo_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog_befor_exercise);
       //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
      //  dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.primarydark_dark));
        }

        //  ald_insert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        title_chbox = (CheckBox) dialog.findViewById(R.id.show_again_checkBox);
        title_chbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    prefsEditor.putBoolean("show_befor_exercise", false);
                    prefsEditor.commit();
                }
            }
        });
        cancel = (FButton) dialog.findViewById(R.id.btn_report2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* FragmentManager manager =a.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = null;
                fragment = new Fragment_insert_exercise();
                transaction.add(R.id.container, fragment);
                transaction.commit();
                */
                dialog.dismiss();
            }
        });
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.rv_report);
        SnappyLinearLayoutManager sllm = new SnappyLinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false);

        sllm.setSnapType(SnapType.CENTER);
// Set the Interpolator
        sllm.setSnapInterpolator(new DecelerateInterpolator());
        recyclerView.setLayoutManager(sllm);


        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from messages_daily where m_type='before_exercise'", null);
        dv_list = new ArrayList<>();
        cr.moveToFirst();
        int i = 0;

        if (cr.getCount() != 0) {
            do {
                dv_list.add(cr.getString(cr.getColumnIndex("m_text")));

            } while (cr.moveToNext());
        }
        adapter_string_recycle madapter = null;
        madapter = new adapter_string_recycle(dv_list);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        recyclerView.setAdapter(madapter);
        return dialog;
    }

    public void show() {
        dialog.show();


    }


}
