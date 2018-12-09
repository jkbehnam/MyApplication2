
package com.example.behnam.myapplication.activities.Fragment_mental;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.zagum.expandicon.ExpandIconView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;



public class Fragment_mental_afsordegi extends Fragment {
    FragmentActivity c;
    View rootView;
    ArrayList<Mental_multiple_choice_questions> mu_list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView=inflater.inflate(R.layout.exercise_fragment_mental_afsordegi,container,false);
        setRetainInstance(true);
        c = getActivity();




        initialize_1();
        initialize_2();
        initialize_3();
        initialize_4();
        initialize_test();
        return rootView;
    }
    public void initialize_1(){
        TextView tvTitle=(TextView)rootView.findViewById(R.id.what_is_ezterab_title1) ;
        com.codesgood.views.JustifiedTextView tvx = (com.codesgood.views.JustifiedTextView)  rootView.findViewById(R.id.what_is_ezterab_textView1);
        tvx.setText("افسردگی حالتی بیشتر از داشتن خلق و خوی بد یا احساس ناخوشی است، یک بیماری جدی است. این وضعیت برروی احساسات، افکار و رفتار فرد تاثیر می گذارد و میتواند منجر به مشکلات هیجانی و جسمی متعددی شود. فرد ممکن است در انجام فعالیت های معمول روزانه دچار مشکل شود و گاهی ممکن است احساس کند زندگی بی\u200Cارزش است.\n" +
                "اگر در انجام بعضی کارها در طول روز مشکل دارید شما افسرده هستید. ممکن است تمایل نداشته باشید با دوستان یا خانواده خود باشید یا از کارکردن و فعالیتهای ورزشی لذت نمی برید. افسردگی همچنین بر روی روابط و حال عمومی شما تاثیر می گذارد.\n" +
                "افسردگی می تواند به دلایل مختلفی باشد. ممکن است بدلیل مشکلات قلبی، مسائل خانوادگی یا کاری یا ایجاد تغییر در شیوه زندگی باشد. افسردگی می تواند از عوارض مصرف بعضی از داروها باشد.\n" +
                "درمورد علایم و نشانه های افسردگی بدانید. اگر نیاز داشتید کمک بخواهید. پزشکتان می تواند شما را با روش های مختلف درمانی آشنا کند\n");
        final ExpandIconView eiv1 = (ExpandIconView) rootView.findViewById(R.id.eiv1);
        final ExpandableRelativeLayout expandableLayout
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.what_is_ezterab_expandableLayout1);
        expandableLayout.setInterpolator(new LinearInterpolator());
        CardView cv = (CardView) rootView.findViewById(R.id.what_is_ezterab_cardview1);
        expand_listener(tvTitle,cv,expandableLayout,eiv1);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();

            }
        });
    }
    public void initialize_2(){
        TextView tvTitle=(TextView)rootView.findViewById(R.id.what_is_ezterab_title2) ;
        com.codesgood.views.JustifiedTextView tvx = (com.codesgood.views.JustifiedTextView)  rootView.findViewById(R.id.what_is_ezterab_textView2);
        tvx.setText("افسردگی و بیماری قلبی عروقی غالبا باهم اتفاق می افتند. مطالعات نشان میدهند که افسردگی مانند سیگار کشیدن، چربی خون و فشارخون بالا می تواند یکی از عوامل خطرآفرین برای بیماری قلبی عروقی باشد. \n" +
                "افسردگی می تواند روند بهبودی بیماری قلبی را کند نماید و خطر مشکلات قلبی را افزایش دهد.کنترل افسردگی به شما در بهبودی بهتر، کم کردن خطر ابتلا به مشکلات قلبی و سلامتی بیشتر کمک خواهد کرد.\n");
        final ExpandIconView eiv1 = (ExpandIconView) rootView.findViewById(R.id.eiv2);
        final ExpandableRelativeLayout expandableLayout
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.what_is_ezterab_expandableLayout2);
        expandableLayout.setInterpolator(new LinearInterpolator());
        CardView cv = (CardView) rootView.findViewById(R.id.what_is_ezterab_cardview2);
        expand_listener(tvTitle,cv,expandableLayout,eiv1);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();

            }
        });
    }
    public void initialize_3(){
        TextView tvTitle=(TextView)rootView.findViewById(R.id.what_is_ezterab_title3) ;
        com.codesgood.views.JustifiedTextView tvx = (com.codesgood.views.JustifiedTextView) rootView.findViewById(R.id.what_is_ezterab_textView3);
        tvx.setText("یکی از علایم افسردگی زمانی است که برای بیش از 2 هفته احساس ناراحتی، سرخوردگی و ناخوشی دارید یا در اکثر اوقات از انجام فعالیتهای روزمره لذت نمی برید.\n" +
                "همچنین می تواند مواردی چون:\n" +
                "•\tاز خانه بیرون نرفتن\n" +
                "•\tصحبت نکردن یا ندیدن اعضا خانواده یا دوستان از نزدیک \n" +
                "•\tامتناع از انجام کاری که قبلا از ان لذت می بردید\n" +
                "•\tنداشتن تمرکز \n" +
                "•\tاحساس سرخوردگی، گناه یا ناتوانی\n" +
                "•\tاحساس پوچی، غم و ناراحتی\n" +
                "•\tاحساس ناامیدی، بدبختی و دودلی\n" +
                "•\tداشتن سردرد و درد عضلانی\n" +
                "•\tخواب نامنظم یا زیاد خوابیدن\n" +
                "•\tاز دست دادن ذائقه یا تغییر آن\n");
        final ExpandIconView eiv1 = (ExpandIconView) rootView.findViewById(R.id.eiv3);
        final ExpandableRelativeLayout expandableLayout
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.what_is_ezterab_expandableLayout3);
        expandableLayout.setInterpolator(new LinearInterpolator());
        CardView cv = (CardView) rootView.findViewById(R.id.what_is_ezterab_cardview3);
        expand_listener(tvTitle,cv,expandableLayout,eiv1);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();

            }
        });
    }
    public void initialize_4(){
        TextView tvTitle=(TextView)rootView.findViewById(R.id.what_is_ezterab_title4) ;
        com.codesgood.views.JustifiedTextView tvx = (com.codesgood.views.JustifiedTextView)  rootView.findViewById(R.id.what_is_ezterab_textView4);
        tvx.setText("اگر احساس می کنید افسرده هستید، راه های مختلفی برای کنترل آن وجود دارد. این موارد ممکن است از بروز مجدد مشکلات قلبی جلوگیری نماید.\n" +
                "•\tبا پزشک خود در این رابطه و راههای درمان آن صحبت کنید.\n" +
                "•\tاز خانواده و دوستان خود کمک و حمایت و تشویق بخواهید.\n" +
                "•\tبرای دوری از تنهایی به دیدن خانواده و دوستان خود بروید یا به آنها زنگ زنید. \n" +
                "•\tفعال باشید، فعالیت فیزیکی باعث بهبود سلامت ذهنی و سلامت جسمانی می شود.\n" +
                "•\tغذاهای متنوع و سالم بخورید.\n" +
                "•\tسعی کنید به وزن ایده ال برسید.\n" +
                "•\tخواب کافی داشته باشید.\n" +
                "•\tزمانی برای استراحت و کاهش استرس های خود در نظر بگیرید.\n" +
                "•\tمراجعات پزشکی منظم داشته باشید و داروها را سر موقع مصرف نمایید.\n");
        final ExpandIconView eiv1 = (ExpandIconView) rootView.findViewById(R.id.eiv4);
        final ExpandableRelativeLayout expandableLayout
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.what_is_ezterab_expandableLayout4);
        CardView cv = (CardView) rootView.findViewById(R.id.what_is_ezterab_cardview4);
        expandableLayout.setInterpolator(new LinearInterpolator());
        expand_listener(tvTitle,cv,expandableLayout,eiv1);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();
            }
        });
    }
    public void initialize_test() {
        TextView tvTitle=(TextView)rootView.findViewById(R.id.main_sport_title) ;
        final ExpandIconView eiv2 = (ExpandIconView) rootView.findViewById(R.id.eiv5);
        final ExpandableRelativeLayout expandableLayout2
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.ezterab_test_expandableLayout5);
        expandableLayout2.setInterpolator(new LinearInterpolator());
        CardView cv = (CardView) rootView.findViewById(R.id.ezterab_test_cardview);
        expand_listener(tvTitle,cv,expandableLayout2,eiv2);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout2.toggle();
            }
        });


        Mental_multiple_choice_questions mcq = new Mental_multiple_choice_questions();
       mu_list=new ArrayList<>();
        mcq.setTitle("1- عدم علاقه یا لذت در انجام کار");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("2- احساس غمگینی یا ناامیدی");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("3- اشکال در به خواب رفتن یا تداوم خواب یا زیاد خوابیدن");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("4- احساس خستگی یا کاهش انرژی");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("5- کاهش اشتها یا پرخوری");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("6- داشتن احساس بد در مورد خود یا احساس اینکه یک فرد شکست خورده هستید یا اینکه باعث بدبختی خود و خانواده تان شده اید");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("7- اختلال در تمرکز روی موارد مختلف، مانند خواندن روزنامه یا تماشای تلویزیون");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("8- حرکت کردن یا صحبت کردن آهسته به طوری که سایر افراد متوجه آن شده باشند. یا برعکس آنچنان بی قرار باشید که بسیار بیش از معمول حرکت کنید");
        mu_list.add(mcq);
        mcq = new Mental_multiple_choice_questions();
        mcq.setTitle("9- داشتن افکاری مبنی بر اینکه بهتر است بمیرید یا به طریقی به خودتان آسیب برسانید");
        mu_list.add(mcq);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.ezterab_test_rcl_chbox);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        adapter_ezterab_test madapter = new adapter_ezterab_test(mu_list);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        alphaAdapter.setFirstOnly(true);
        recyclerView.setAdapter(alphaAdapter);
    }
    public void save(){
        int i=0;
        Boolean checkall=true;
        Toast.makeText(c, "افسردگی", Toast.LENGTH_SHORT).show();
        for (Mental_multiple_choice_questions mcq:mu_list
                ) {
            if (mcq.getResult()==5){
                checkall_alert();
                checkall=false;
                break;
            }
            i=i+mcq.getResult();

        }
        if(checkall) {
         //   Toast.makeText(c, String.valueOf(i), Toast.LENGTH_SHORT).show();

            HomeRecycleView o = new HomeRecycleView();

            if (i < 5) {
                o.setMainText("افسردگی بدون علامت یا حداقل علائم");
                o.setSubText("در حال حاضر درمانی لازم نیست، 2 هفته بعد مجددا این پرسشنامه را پر کنید.");
            } else if (i >= 5 && i <= 9) {
                o.setMainText("افسردگی خفیف");
                o.setSubText("درحال حاضر درمانی لازم نیست، 2 هفته بعد مجددا\" این پرسشنامه را پر کنید.");
            } else if (i >= 10 && i <= 14) {
                o.setMainText("افسردگی متوسط");
                o.setSubText("جهت مشخص شدن برنامه ی درمانی، لازم است به روانپزشک مراجعه کنید تا براساس طول مدت علائم و میزان افت عملکردی که این علائم برای شما ایجاد کرده تصمیم گیری صورت گیرد");
            } else if (i >= 15 && i <= 19) {
                o.setMainText("افسردگی نسبتا شدید");
                o.setSubText("حتما\" جهت انجام درمان (روان درمانی- درمان دارویی یا درمان ترکیبی) در اولین فرصت به روانپزشک مراجعه نمایید." +
                        "روان\u200Cدرمانی\n" +
                        "•\tدرمانی است که از طریق صحبت کردن با یک متخصص سلامت\u200Cروان درباره وضعیتتان صورت میگیرد .\n" +
                        "•\tانواع مختلف روان\u200Cدرمانی ها می\u200Cتوانند برای افسردگی موثر باشند. مانند درمان شناختی\u200Cرفتاری یا درمان بین\u200Cفردی .\n" + "\n" +
                        "درمان بستری\n" +
                        "در برخی افراد افسردگی به حدی شدید است که بستری شدن فرد در بیمارستان ضرورت می\u200Cیابد . این در شرایطی است که فرد به طور کامل قادر به مراقبت از خود نیست و یا در هنگامی که خطر آنی آسیب ، به خود یا دیگران وجود دارد .\n");
            } else if (i >= 20 && i <= 27) {
                o.setMainText("افسردگی شدید");
                o.setSubText("حتما\" جهت انجام درمان (روان درمانی- درمان دارویی یا درمان ترکیبی) در اولین فرصت به روانپزشک مراجعه نمایید." + "\n" +
                        "روان\u200Cدرمانی\n" +
                        "•\tدرمانی است که از طریق صحبت کردن با یک متخصص سلامت\u200Cروان درباره وضعیتتان صورت میگیرد .\n" +
                        "•\tانواع مختلف روان\u200Cدرمانی ها می\u200Cتوانند برای افسردگی موثر باشند. مانند درمان شناختی\u200Cرفتاری یا درمان بین\u200Cفردی .\n" +
                        "درمان بستری\n" +
                        "در برخی افراد افسردگی به حدی شدید است که بستری شدن فرد در بیمارستان ضرورت می\u200Cیابد . این در شرایطی است که فرد به طور کامل قادر به مراقبت از خود نیست و یا در هنگامی که خطر آنی آسیب ، به خود یا دیگران وجود دارد .\n");
            }


            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
            DataBase_write.write_mental_test("afsordegi", i, cal.getTimeInMillis() / 1000);
            my_mental_result_alertdialog dialog = new my_mental_result_alertdialog();
            android.support.v7.app.AlertDialog a = dialog.get_dialog(c, o);
            a.show();
            a.setCancelable(true);


            cal.add(Calendar.DATE, 14);
            clearNotification(10004);
            set_alarm_notify.cancel_alarm(MainActivity.mainContext, 10004);
            set_alarm_notify.set_alarm_repeat(MainActivity.mainContext, 10004, cal);
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.putBoolean("10004", false);
            prefsEditor.putLong("10004cal", cal.getTimeInMillis());
            prefsEditor.commit();
        }
    }
    public void expand_listener(TextView tvTitle,CardView cv,ExpandableRelativeLayout expandableLayout, ExpandIconView eiv2){
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
        NotificationManager notificationManager = (NotificationManager) c .getSystemService(Context.NOTIFICATION_SERVICE);
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