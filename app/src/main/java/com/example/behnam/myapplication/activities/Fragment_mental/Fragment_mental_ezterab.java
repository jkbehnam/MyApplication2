
package com.example.behnam.myapplication.activities.Fragment_mental;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_home;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_ezterab_test;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.example.behnam.myapplication.my_custom_component.my_mental_result_alertdialog;
import com.example.behnam.myapplication.objects.HomeRecycleView;
import com.example.behnam.myapplication.objects.Mental_multiple_choice_questions;
import com.example.behnam.myapplication.set_alarm_notify;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.zagum.expandicon.ExpandIconView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;


public class Fragment_mental_ezterab extends Fragment {
    FragmentActivity c;
    View rootView;
    ArrayList<Mental_multiple_choice_questions> mu_list;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_fragment_mental_ezterab, container, false);
        setRetainInstance(true);
        c = getActivity();

        TextView tvTitle=(TextView)rootView.findViewById(R.id.what_is_ezterab_title) ;
        final ExpandIconView eiv1 = (ExpandIconView) rootView.findViewById(R.id.eiv1);
        final ExpandableRelativeLayout expandableLayout
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.what_is_ezterab_expandableLayout);
        expandableLayout.setInterpolator(new LinearInterpolator());
        expandableLayout.collapse();

        CardView cv = (CardView) rootView.findViewById(R.id.what_is_ezterab_cardview);
        expand_listener(tvTitle,cv,expandableLayout, eiv1);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();

            }
        });

        com.codesgood.views.JustifiedTextView tvx = (com.codesgood.views.JustifiedTextView) rootView.findViewById(R.id.what_is_ezterab_textView);
        tvx.setText("همه ی افراد گاهی احساس اضطراب می کنند که این احساس یک هیجان جسمی است.\n" +
                " برای مثال شما ممکن است هنگام رو\u200Cبه\u200Cرو شدن با یک مشکل در محل کار، یا قبل از گرفتن یک تصمیم مهم احساس اضطراب کنید. اما اختلالات اضطرابی متفاوت هستند. آنها گروهی از اختلالات روانی هستند که می\u200Cتوانند در روند طبیعی زندگی شما اختلال ایجاد کنند. درافراد مبتلا به این اختلالات احساس نگرانی و ترس، پایدار و فراگیر است و می\u200Cتواند کاملا ناتوان کننده باشد. ");

        initialize_test();
        return rootView;
    }

    public void initialize_test() {
        TextView tvTitle=(TextView)rootView.findViewById(R.id.main_sport_title) ;
        final ExpandIconView eiv2 = (ExpandIconView) rootView.findViewById(R.id.eiv2);
        final ExpandableRelativeLayout expandableLayout2
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.ezterab_test_expandableLayout);

        expandableLayout2.collapse();

        expandableLayout2.setInterpolator(new LinearInterpolator());
        CardView cv = (CardView) rootView.findViewById(R.id.ezterab_test_cardview);
        expand_listener(tvTitle,cv,expandableLayout2, eiv2);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout2.toggle();

            }
        });


        Mental_multiple_choice_questions mcq = new Mental_multiple_choice_questions();
        mu_list = new ArrayList<>();
        mcq.setTitle("1- احساس عصبی بودن، اضطراب یا تحریک پذیری");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("2- ناتوانی در متوقف کردن یا کنترل نگرانی");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("3- نگرانی بیش از حد در مورد چیز های مختلف");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("4- مشکل در آرام شدن");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("5- بی قراری شدید به طوریکه نسشتن سخت باشد");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("6- به آسانی رنجیده خاطر شدن یا تحریک پذیر بودن");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("7- احساس ترس از اینکه اتفاق وحشتناکی ممکن است رخ دهد");
        mu_list.add(mcq);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.ezterab_test_rcl_chbox);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        adapter_ezterab_test madapter = new adapter_ezterab_test(mu_list);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        recyclerView.setAdapter(alphaAdapter);
        recyclerView.invalidate();
        recyclerView.setNestedScrollingEnabled(false);
    }

    public void save() {
        int i = 0;
        Boolean checkall=true;
        Toast.makeText(c, "اضطراب", Toast.LENGTH_SHORT).show();
        for (Mental_multiple_choice_questions mcq : mu_list
                ) {
            if(mcq.getResult()==5){
                    checkall_alert();
                    checkall=false;
                    break;

            }
            i = i + mcq.getResult();

        }
        if(checkall) {
            HomeRecycleView o = new HomeRecycleView();

            if (i < 5) {
                o.setMainText("اضطراب بدون علامت");
                o.setSubText("در حال حاضر درمان لازم نیست، 2 هفته بعد مجددا این پرسشنامه را پر کنید.");
            } else if (i >= 5 && i <= 9) {
                o.setMainText("اضطراب خفیف");
                o.setSubText("در حال حاضر درمانی لازم نیست. دوهفته بعد مجددا\" این پرسشنامه را پر کنید. برای بدست آوردن اطلاعات بیشتر به صفحه مدیریت استرس مراجعه نمایید.");
            } else if (i >= 10 && i <= 14) {
                o.setMainText("اضطراب متوسط");
                o.setSubText("ممکن است این وضعیت از نظر بالینی دارای اهمیت باشد. به روانپزشک مراجعه نمایید. برای بدست آوردن اطلاعات بیشتر به صفحه مدیریت استرس مراجعه نمایید.");
            } else if (i >= 15) {
                o.setMainText("اضطراب شدید");
                o.setSubText("در اولین فرصت به روانپزشک مراجعه نمایید، به احتمال زیاد نیاز به درمان دارید.");
            }

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
            DataBase_write.write_mental_test("ezterab", i, cal.getTimeInMillis() / 1000);


            Toast.makeText(c, String.valueOf(i), Toast.LENGTH_SHORT).show();
            my_mental_result_alertdialog dialog = new my_mental_result_alertdialog();
            android.support.v7.app.AlertDialog a = dialog.get_dialog(c, o);
            a.show();

            cal.add(Calendar.DATE, 14);
            clearNotification(10005);
            set_alarm_notify.cancel_alarm(MainActivity.mainContext, 10005);
            set_alarm_notify.set_alarm_repeat(MainActivity.mainContext, 10005, cal);
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.putBoolean("10005", false);
            prefsEditor.putLong("10005cal", cal.getTimeInMillis());
            prefsEditor.commit();
        }
    }

    public void expand_listener(TextView tvTitle, CardView cv, ExpandableRelativeLayout expandableLayout, ExpandIconView eiv2){
        expandableLayout.collapse();
        eiv2.switchState();
        expandableLayout.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
                eiv2.switchState();

                cv.setBackgroundColor(Color.parseColor("#e5faf9"));

                //   eiv2.setti
                tvTitle.setTextColor(Color.parseColor("#02c9c8"));
                SpannableStringBuilder str = new SpannableStringBuilder(tvTitle.getText().toString());
                str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvTitle.setText(str);

            }

            @Override
            public void onPreClose() {
                eiv2.switchState();
                cv.setBackgroundColor(Color.parseColor("#fafafa"));
//eiv2.setDrawingCacheBackgroundColor((Color.parseColor("#7b7b7b")));
                tvTitle.setTextColor(Color.parseColor("#7b7b7b"));
                SpannableStringBuilder str = new SpannableStringBuilder(tvTitle.getText().toString());
                str.setSpan(new android.text.style.StyleSpan(Typeface.NORMAL), 0, str.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvTitle.setText(str);
            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });
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
        NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }
    public void checkall_alert(){
        String message;

        String btn_confirm = "فهمیدم";
        message = "دوست عزیز جهت نظارت بر روند بهبودی شما و ارائه مشاوره بموقع لازم است اطلاعات لازم را در قسمت شاخص های روزانه ثبت نمایید.";
        new SweetAlertDialog(c, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("لطفا تمام گزینه ها را علامت بزنید")
                .setConfirmText(btn_confirm)
                .setCustomImage(R.drawable.exercise_icon_sleeping_recyclerview)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}