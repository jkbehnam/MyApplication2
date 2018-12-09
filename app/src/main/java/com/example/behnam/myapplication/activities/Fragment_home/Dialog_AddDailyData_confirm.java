package com.example.behnam.myapplication.activities.Fragment_home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.RecyclerViewRecyclerAdapter;
import com.example.behnam.myapplication.connect_to_server.update_server_data;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.example.behnam.myapplication.objects.DailyData;
import com.example.behnam.myapplication.objects.DailyData_confirm_list;
import com.example.behnam.myapplication.set_alarm_notify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import info.hoang8f.widget.FButton;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;


public class Dialog_AddDailyData_confirm {
    int have_diabette = 0;
    int is_Smoker=0;
    Context context;
    Dialog dialog;
    DailyData d_data;
    int bp, glu, wei, cig, sle, ste, hea;
    FButton btn_save;
    Long startActivity, endActivity;

    @SuppressLint("ResourceAsColor")
    public Dialog qrcode_reader(Context context, DailyData d_data, FragmentActivity a) {
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
            String factorDM = prefs.getString("factorDM", "");
            if (factorDM.equals("true")) {
                have_diabette = 1;
            } else {
                have_diabette = 0;
            }
            String factorSmoker = prefs.getString("factorSmoker", "");
            if (factorSmoker.equals("true")) {
                is_Smoker = 1;
            } else {
                is_Smoker = 0;
            }
        }
        startActivity = System.currentTimeMillis();
        this.context = context;
        this.d_data = d_data;
        dialog = new Dialog(context, android.R.style.Theme_Holo_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.exercise_dialog_add_daily_data_confirm);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.primarydark_dark));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        initial_items();

        TextView tvToolbarTitle = (TextView) dialog.findViewById(R.id.tvToolbarAllPage);
        FButton tv_edit = (FButton) dialog.findViewById(R.id.btn_save);
        ImageView iv_cancel = (ImageView) dialog.findViewById(R.id.iv_cancel);
        tvToolbarTitle.setText("ثبت نهایی شاخص\u200Cها");
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_save = (FButton) dialog.findViewById(R.id.btn_save_daily);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_logs();
                DataBase_write.write_daily_indicators(d_data);
                update_server_data.send_last_daily_data(context);
                //   Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
                cal.add(Calendar.DATE, 2);
                clearNotification(10001);
                set_alarm_notify.cancel_alarm(context, 10001);
                set_alarm_notify.set_alarm_repeat(context, 10001, cal);
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                prefsEditor.putBoolean("10001", false);
                prefsEditor.putLong("10001cal", cal.getTimeInMillis());
                prefsEditor.commit();
                FragmentManager manager = a.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = null;
                fragment = new Fragment_home();
                transaction.add(R.id.container, fragment);
                transaction.commit();
                dialog.dismiss();


            }
        });
        return dialog;
    }


    public void initial_items() {
        ArrayList<DailyData_confirm_list> dd = new ArrayList<>();
        DailyData_confirm_list d;
        {
            d = new DailyData_confirm_list();
            d.setTitle("فشار خون");
            String result = "<div class=\"body2\">\n" +
                    "فشار خون حداکثر=" + "<span class=\"c1\">\n" + d_data.getUp_pb() / 10 + "</span>" +
                    "</div>" + "<div class=\"body2\">\n" +
                    "فشار خون حداقل=" + "<span class=\"c1\">\n" + d_data.getDown_pb() / 10 +
                    "</div>";
            d.setContent_message(result + "<div class=\"c1\">\n" +
                    bp_message() +
                    "</div>" + "<div></div>" + DataBase_read.give_messages_daily(context, "blood_p"));
            d.setImg(bp);
            dd.add(d);
        }
        {
            if (have_diabette == 1) {
                d = new DailyData_confirm_list();
                d.setTitle("قند خون");
                String result = "<div class=\"body2\">\n" +
                        "میزان قند خون=" + "<span class=\"c1\">\n" + d_data.getGluose() + "</span>" +
                        "</div>";
                d.setContent_message(result + "<div class=\"c1\">\n" +
                        glu_message() +
                        "</div>" + "<div></div>" + DataBase_read.give_messages_daily(context, "blood_g"));
                d.setImg(glu);
                dd.add(d);
            }
        }
        {

            d = new DailyData_confirm_list();
            d.setTitle("وزن");
            String result = "<div class=\"body2\">\n" +
                    "میزان وزن شما=" + "<span class=\"c1\">\n" + d_data.getEt_weight() + "</span>" +
                    "</div>";
            d.setContent_message(result + "<div class=\"c1\">\n" +
                    weight_message() +
                    "</div>" + "<div></div>" + DataBase_read.give_messages_daily(context, "weight"));
            d.setImg(wei);
            dd.add(d);

        }
        {
            if(is_Smoker==1){
            d = new DailyData_confirm_list();
            d.setTitle("سیگار");
            String result = "<div class=\"body2\">\n" +
                    "تعداد سیگار مصرفی=" + "<span class=\"c1\">\n" + (int) d_data.getCigarette() + "</span>" +
                    "</div>";
            d.setContent_message(result + "<div class=\"c1\">\n" +
                    cigar_message() +
                    "</div>" + "<div></div>" + DataBase_read.give_messages_daily(context, "cigarette"));
            d.setImg(cig);
            dd.add(d);}
        }
        {
            d = new DailyData_confirm_list();
            d.setTitle("خواب");
            String result = "<div class=\"body2\">\n" +
                    "میزان خواب شما=" + "<span class=\"c1\">\n" + d_data.getSleep() + "</span>" +
                    "</div>";
            d.setContent_message(result + "<div class=\"c1\">\n" +
                    sleep_message() +
                    "</div>");
            d.setImg(sle);
            dd.add(d);

        }
        {
            d = new DailyData_confirm_list();
            d.setTitle("تعداد قدم");
            String result = "<div class=\"body2\">\n" +
                    "تعداد قدم شما=" + "<span class=\"c1\">\n" + (int) d_data.getSteps() + "</span>" +
                    "</div>";
            d.setContent_message(result + "<div class=\"c1\">\n" +
                    steps_message() +
                    "</div>");
            d.setImg(ste);
            dd.add(d);
        }
        {
            d = new DailyData_confirm_list();
            d.setTitle("ضربان قلب");
            String result = "<div class=\"body2\">\n" +
                    "آخرین ضربان قلب شما=" + "<span class=\"c1\">\n" + (int) d_data.getLast_heart_rate() + "</span>" +
                    "</div>";
            d.setContent_message(result + "<div class=\"c1\">\n" +
                    heart_rate_message() +
                    "</div>");
            d.setImg(hea);
            dd.add(d);
        }

        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.state_chbox);
        LinearLayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewRecyclerAdapter madapter = new RecyclerViewRecyclerAdapter(dd);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        alphaAdapter.setFirstOnly(true);
        recyclerView.setAdapter(alphaAdapter);
        //recyclerView.invalidate();
        //recyclerView.setNestedScrollingEnabled(false);
    }

    public String bp_message() {
        String message = "";
        if (d_data.getUp_pb() <= 120 && d_data.getDown_pb() <= 80) {
            bp = 0;
            message =
                    "فشار خون شما طبیعی است.آیا هنگام اندازه گیری فشار خون این <a href=\"hrupin://second_activity\">نکات</a> را رعایت کردید؟ میتوانید\n" +
                            "    <a href=\"hrupin://second_activity2\">راههای کنترل فشارخون</a> را مطالعه نمایید.\n"
            ;
        } else if ((d_data.getUp_pb() <= 129 && d_data.getUp_pb() >= 120) && d_data.getDown_pb() <= 80) {
            bp = 1;
            message =
                    "    فشار خون شما بالاتر از حد طبیعی است. آیا درهنگام اندازه گیری فشار خون این <a href=\"hrupin://second_activity\">نکات</a> را رعایت کردید؟ و میتوانید <a href=\"hrupin://second_activity2\">راههای کنترل فشارخون</a> را مطالعه نمایید.\n"
            ;

        } else if ((d_data.getUp_pb() >= 130 && d_data.getUp_pb() <= 139) || (d_data.getDown_pb() <= 89 && d_data.getDown_pb() >= 80)) {
            bp = 2;
            message =
                    "    فشارخون شما بالاست. در صورتی که داروهای کاهش فشار خون خود را مصرف نکرده اید، توصیه می شود به پزشک مراجعه نمایید. آیا هنگام اندازه گیری فشار خون این <a href=\"hrupin://second_activity\">نکات</a> را رعایت کردید؟.\n"
            ;
        } else if ((d_data.getUp_pb() >= 140 && d_data.getUp_pb() <= 159) || (d_data.getDown_pb() <= 90 && d_data.getDown_pb() >= 99)) {
            bp = 2;
            message =
                    "    فشارخون شما بالاست. در صورتی که داروهای کاهش فشار خون خود را مصرف نکرده اید، توصیه می شود به پزشک مراجعه نمایید. آیا هنگام اندازه گیری فشار خون این <a href=\"hrupin://second_activity\">نکات</a> را رعایت کردید؟.\n"
            ;
        } else if (d_data.getUp_pb() >= 160 || d_data.getDown_pb() >= 100) {
            bp = 2;
            message =
                    "    فشارخون شما خیلی بالاست. در صورتی که داروهای کاهش فشار خون خود را مصرف نکرده اید، توصیه می شود به پزشک مراجعه نمایید. آیا هنگام اندازه گیری فشار خون این <a href=\"hrupin://second_activity\">نکات</a> را رعایت کردید؟.\n"
            ;
        }
        return message;
    }

    public String glu_message() {

        String message = "";
        if (d_data.getGluose() < 180) {
            glu = 0;
            message = "قند خون شما مطلوب است\n";
        } else if (d_data.getGluose() >= 180) {
            glu = 2;
            message =
                    "    قند خون شما بالا است.توصیه می شود به متخصص غدد جهت کنترل قند خون مراجعه نمایید.\n";
        }
        return message;
    }

    public String weight_message() {
        String message = "";
        try {


            Double w = d_data.getEt_weight();
            String height = DataBase_read.get_height(MainActivity.mainContext);
            w = w / (Double.parseDouble(height) * Double.parseDouble(height));
            if (w < 18.5) {
                wei = 3;
                message = "    شما دچار کمبود وزن هستید. توصیه می شود به متخصص تغذیه مراجعه نمایید. لازم است <a href=\\\"hrupin://second_activity\\3\">تغذیه متعادل سالم</a> داشته باشید.\n" +
                        "\n";
            } else if (w >= 18.5 && w < 25) {
                wei = 0;
                message = "وزن شما در محدوده طبیعی است. ";
            } else if (w >= 25 && w < 30) {
                wei = 1;
                message = "شما دارای اضافه وزن هستید. ";
            } else if (w >= 30) {
                wei = 2;
                message = "وزن شما در محدوده چاقی است. ";
            }
        } catch (Exception e) {

        }
        return message;
    }

    public String cigar_message() {
        String message = "";
        if (d_data.getCigarette() == 0) {
            cig = 0;
            message = "بسیار عالی است که سیگار مصرف نمی کنید.";
        } else if (d_data.getCigarette() > 0) {
            cig = 2;
            message = "سیگار کشیدن بسیار مضر است. تلاش کنید حتی یک عدد سیگار هم نکشید.";
        }
        return message;
    }

    public String sleep_message() {
        String message = "";
        ;
        if (d_data.getSleep() < 7) {
            sle = 1;
            message = "    میزان خواب شما کم است، این مساله را با پزشک خود در میان بگذارید. در صورت داشتن استرس می توانید <a href=\\\"hrupin://second_activity4\\\">تمرین های آرام سازی</a>  را  انجام دهید.";
        } else if (d_data.getSleep() >= 7 && d_data.getSleep() <= 8) {
            sle = 0;
            message = "میزان خواب شما مطلوب است";
        } else if (d_data.getSleep() > 8) {
            sle = 2;
            message = "    میزان خواب خود را کم کنید. انجام ورزش و <a href=\\\"hrupin://second_activity5\\\">فعالیتهای ورزشی منظم</a> را به شما توصیه می کینم.\n";
        }
        return message;
    }

    public String heart_rate_message() {
        String message = "";
        if (d_data.getLast_heart_rate() <= 49) {
            hea = 1;
            message = "ضربان قلب شما پایین تر از حد طبیعی است . توصیه میشود برای بررسی بیشتر و احتمالا بررسی دارو ها یتان به پزشک مراجعه کنید.\n";
        } else if (d_data.getLast_heart_rate() >= 50 && d_data.getLast_heart_rate() <= 80) {
            hea = 0;
            message = "ضربان در محدوده طبیعی قرار دارد\n";
        } else if (d_data.getLast_heart_rate() >= 81) {
            hea = 3;
            message = "ضربان قلب شما بیشتر از حد  طبیعی است. توصیه میشود برای بررسی بیشتر و احتمالا بررسی دارو ها یتان به پزشک مراجعه کنید.\n";
        }
        return message;
    }

    public String steps_message() {
        String message = "";
        if (d_data.getSteps() < 3000) {
            ste = 1;
            message = "    فعالیت بدنی شما پایین است. داشتن فعالیت بدنی یکی از راههای جلوگیری از مشکلات قلبی بیشتر در آینده است. توصیه می شود از مطالب قسمت <a href=\\\"hrupin://second_activity5\\\">فعالیتهای ورزشی</a> استفاده کنید.\n";
        } else if (d_data.getSteps() >= 3000 && d_data.getSteps() <= 9999) {
            ste = 0;
            message = "    فعالیت بدنی شما در حد طبیعی است می باشد. بسیار عالی است همین روند را ادامه دهید. توصیه می شود برای داشتن فعالیت بدنی مناسب از مطالب قسمت <a href=\\\"hrupin://second_activity5\\\">فعالیتهای ورزشی</a> استفاده نمایید.\n";
        } else if (d_data.getSteps() >= 10000) {
            ste = 2;
            message = "    فعالیت بدنی شما بالاست. سعی کنید در حد طبیعی فعالیت نمایید. می توانید از مطالب قسمت <a href=\\\"hrupin://second_activity5\\\">فعالیتهای ورزشی</a> برای این منظور کمک بگیرید.\n";
        }
        return message;
    }

    static class data {
        String text;
        int state;
    }

    public void clearNotification(int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    public void save_logs() {
        endActivity = System.currentTimeMillis();
        long timeSpend = endActivity - startActivity;
        Calendar cal = Calendar.getInstance();
        DataBase_write.save_action_log("Summary_of_the_situation", cal.getTimeInMillis(), timeSpend);
    }
}
