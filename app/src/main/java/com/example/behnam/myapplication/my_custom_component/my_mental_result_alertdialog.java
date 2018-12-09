package com.example.behnam.myapplication.my_custom_component;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.objects.HomeRecycleView;


public class my_mental_result_alertdialog {
    AlertDialog.Builder builder;
    View alert_dialog_newitem;
    AlertDialog ald_insert;
Context context;
    public AlertDialog get_dialog(Context context, HomeRecycleView item) {
        this.context=context;
        builder = new AlertDialog.Builder(context);
        ald_insert = builder.create();
        alert_dialog_newitem = LayoutInflater.from(context).inflate(R.layout.exercise_mental_result, null);
        ald_insert.setView(alert_dialog_newitem);
       // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_title=(TextView)alert_dialog_newitem.findViewById(R.id.alert_title) ;
        TextView tv_content=(TextView)alert_dialog_newitem.findViewById(R.id.alert_content) ;
        tv_title.setText(item.getMainText());
        tv_content.setText(item.getSubText());
        ald_insert.setCancelable(true);
        return ald_insert;
    }
    public int Screen_width() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        return width;
    }

}
