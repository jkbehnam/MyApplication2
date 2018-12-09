package com.example.behnam.myapplication.activities.Fragment_home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;


import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.my_custom_component.MyMarkerView;
import com.example.behnam.myapplication.my_custom_component.my_BarChart;
import com.example.behnam.myapplication.objects.Chart_data;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;


public class Dialog_exercise_5dayago {

    Context context;
    Dialog dialog;
    my_BarChart chart_1;

    @SuppressLint("ResourceAsColor")
    public Dialog qrcode_reader(Context context) {
        this.context = context;

        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog = new Dialog(context);


        dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_Alert);

        dialog.setContentView(R.layout.exercise_alert_5daysago);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.primarydark_dark));
        }
*/
        get_step_data();
        //  ald_insert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    public void get_step_data() {

        ArrayList<BarEntry> yVals;
        ArrayList<String> xVals;
        yVals = new ArrayList<BarEntry>();
        xVals = new ArrayList<String>();
        chart_1 = (my_BarChart) dialog.findViewById(R.id.chart1);

        chart_1.setVisibility(View.VISIBLE);
        ArrayList<Chart_data> chartData = DataBase_read.get_hardness_5dayago(context);


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
            color.add(ContextCompat.getColor(context, R.color.primary_dark));

        }


        MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);

        chart_1.setDrawValueAboveBar(true);
   /*     Bitmap starBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.medal);
        if (chartData.size() > 0) {
            chart_1.setRenderer(new ImageBarChartRenderer(chart_1, chart_1.getAnimator(), chart_1.getViewPortHandler(), starBitmap));
        }
        chart_1.setMarker(mv);
        */
        chart_1.chart_data(xVals, yVals, context, color);
        chart_1.set_prefrence2(context.getApplicationContext());

    }

    public void show() {
        dialog.show();


    }
}
