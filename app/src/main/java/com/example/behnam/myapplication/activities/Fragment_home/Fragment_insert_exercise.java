
package com.example.behnam.myapplication.activities.Fragment_home;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_insert_exercise_checkbox;
import com.example.behnam.myapplication.connect_to_server.update_server_data;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.example.behnam.myapplication.objects.Exercise;
import com.example.behnam.myapplication.set_alarm_notify;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.shuhart.stepview.StepView;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import at.grabner.circleprogress.CircleProgressView;
import info.hoang8f.widget.FButton;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;


public class Fragment_insert_exercise extends Fragment {
    ArrayList<Exercise> warmup_list, colddown_list;
    BubbleSeekBar warmup_mBubbleSeekBar, colddown_mBubbleSeekBar, main_mBubbleSeekBar;
    ArrayList<String> title;
    ExpandableRelativeLayout expandableLayout_warm, expandableLayout_cold, expandableLayout_main;
    FragmentActivity c;
    View rootView;
    ScrollView sport_scroll;
    TextView tv_signs;
    private int currentStep = 0;
    LinearLayout lay_cold,lay_main,lay_warm;
    FButton btn_save;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_fragment_insert_exercise, container, false);
        setRetainInstance(true);
        c = getActivity();


        lay_cold=(LinearLayout)rootView.findViewById(R.id.lay_cold);
        lay_main=(LinearLayout)rootView.findViewById(R.id.lay_main);
        lay_warm=(LinearLayout)rootView.findViewById(R.id.lay_warm);
btn_save=(FButton)rootView.findViewById(R.id.btn_save);
btn_save.setText("ثبت و ادامه");
        List<LinearLayout> lays=new ArrayList<>();
        lays.add(lay_warm);
        lays.add(lay_main);
        lays.add(lay_cold);
        //-------------------------------------------------------------
        final StepView stepView = rootView.findViewById(R.id.step_view);
        List<String> steps = new ArrayList<>();
       steps.add("گرم کردن");
        steps.add("ورزش اصلی");
        steps.add("سرد کردن");
        stepView.setSteps(steps);
        final Typeface typeface = TypefaceUtils.load(c.getAssets(), "font/iran_sans.ttf");
        select_lay(lays,0);
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                select_lay(lays,2-step);
                stepView.go(2-step,true);
currentStep=2-step;

            }
        });
        rootView.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    stepView.go(currentStep, true);
                    select_lay(lays,currentStep);

                    if((currentStep == stepView.getStepCount() - 1)){btn_save.setText("ثبت و پایان");}
                } else {
                    stepView.done(true);
                    DataBase_write.InsertExercise(warmup_list, colddown_list, hardness(warmup_mBubbleSeekBar.getProgress()), hardness(main_mBubbleSeekBar.getProgress()), hardness(colddown_mBubbleSeekBar.getProgress()));
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
                    cal.add(Calendar.DATE, 2);
                    clearNotification(10002);
                    set_alarm_notify.cancel_alarm(MainActivity.mainContext, 10002);
                    set_alarm_notify.set_alarm_repeat(MainActivity.mainContext, 10002, cal);
                    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    prefsEditor.putBoolean("10002", false);
                    prefsEditor.putLong("10002cal", cal.getTimeInMillis());
                    prefsEditor.commit();
                    Fragment fragment = new Fragment_home();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.container, fragment);
                    transaction.commit();
                    update_server_data.send_last_exercise_data(MainActivity.mainContext);
                }
            }
        });

        //---------------------------------------------------------------
        expandableLayout_warm
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.warm_ups_expandableLayout);
        expandableLayout_main
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.main_sport_expandableLayout);
        expandableLayout_cold
                = (ExpandableRelativeLayout) rootView.findViewById(R.id.cold_down_expandableLayout);
        sport_scroll=(ScrollView)rootView.findViewById(R.id.sport_scroll) ;
        tv_signs = (TextView) rootView.findViewById(R.id.txt_goto_signs);
        SpannableStringBuilder str = new SpannableStringBuilder("در صورت احساس ناراحتی به صفحه علائم مراجعه کنید.");
        str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, str.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_signs.setText(str);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
        Boolean show = prefs.getBoolean("show_befor_exercise", true);
        if (show) {
            Dialog_befor_exercise dbe = new Dialog_befor_exercise();
            dbe.qrcode_reader(c, c);
            dbe.show();
        } else {

        }
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        TextView tv_save = (TextView) rootView.findViewById(R.id.btn_save);
        tvToolbarTitle.setText("ورزش");
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


      /*  ImageView iv_animation = (ImageView) rootView.findViewById(R.id.warm_ups_imageView);
        iv_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animation_alertdialog aa = new animation_alertdialog();
                aa.qrcode_reader(c, "warm_up");
                aa.show();

            }
        });
        ImageView iv_animation_main = (ImageView) rootView.findViewById(R.id.main_sport_imageView_main);
        iv_animation_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_exercise_5dayago aa = new Dialog_exercise_5dayago();
                aa.qrcode_reader(c);
                aa.show();

            }
        });
        ImageView iv_animation_cold_down = (ImageView) rootView.findViewById(R.id.cold_down_imageView);
        iv_animation_cold_down.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                animation_alertdialog aa = new animation_alertdialog();
                aa.qrcode_reader(c, "cold_down");
                aa.show();


            }
        });
        */
        initialize_warm_ups();
        inistialize_main_sport();
        inistialize_code_down();

        tv_save.setVisibility(View.VISIBLE);

        return rootView;
    }

    public void initialize_warm_ups() {

        expandableLayout_warm.setInterpolator(new LinearInterpolator());

      //  expandableLayout_warm.toggle();
        CardView cv = (CardView) rootView.findViewById(R.id.warm_ups_cardview);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout_warm.toggle();

            }
        });
    //    expandmanage(expandableLayout_warm, expandableLayout_cold, expandableLayout_main);
        title = new ArrayList<>();
        title.add("درجا زدن آرام");
        title.add("بلند کردن زانو و تماس با دست موافق");
        title.add("بلند کردن زانو و تماس با دست مخالف");
        title.add("چرخش مچ پا");
        title.add("بالاآمدن روی پنجه پا");
        title.add("خم و راست کردن زانو");
        title.add("چرخش کمر");
        title.add("چرخش به پهلو همراه با حرکت دست");
        title.add("چرخش دست خم");
        title.add("چرخش دست باز");
        title.add("کشش شانه");
        title.add("حرکت گردن به طرفین");
        title.add("حرکت گردن به جلو وعقب");
        title.add("قدم زدن از پهلو");
        title.add("قدم زدن از پهلو همراه با حرکت دست");

        warmup_list = new ArrayList<>();
        for (String s : title
                ) {
            Exercise e = new Exercise();
            e.setTitle(s);
            e.setIs_checked(0);
            warmup_list.add(e);
        }

        int[] img_list = new int[]{R.raw.darja_zadan_img, R.raw.boland_kardan_zano_va_tamas_ba_dast_movafegh_img, R.raw.boland_kardan_zano_va_tamas_ba_dast_mokhalef_img,
                R.raw.charkhesh_moch_pa_img, R.raw.bala_amadan_roye_panje_pa_img, R.raw.kham_va_rast_kardan_zano_img, R.raw.charkhesh_kamar_img,
                R.raw.charkhesh_be_pahlo_harmrah_ba_charkhesh_dast_img, R.raw.charkhesh_dast_jam_img, R.raw.charkhesh_dast_baz_img, R.raw.keshesh_shane_img,
                R.raw.harkat_gardan_be_tarafein_img, R.raw.harkat_gardan_be_jelo_va_aghab_img, R.raw.ghadam_zadan_az_pahlo_img, R.raw.ghadam_zadan_az_pahlo_ba_dast_img};


        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.warm_ups_rcl_chbox);
        GridLayoutManager layoutManager = new GridLayoutManager(c, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter_insert_exercise_checkbox madapter = new adapter_insert_exercise_checkbox(warmup_list, img_list,"warmup");
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        alphaAdapter.setFirstOnly(true);
        recyclerView.setAdapter(alphaAdapter);
        expandableLayout_warm.setClosePosition(0);

        warmup_mBubbleSeekBar = (BubbleSeekBar) rootView.findViewById(R.id.warm_ups_seekBar);

        warmup_mBubbleSeekBar.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "خیلی آسان");
                array.put(1, "نسبتا آسان");
                array.put(2, "نسبتا سخت");
                array.put(3, "خیلی سخت");

                return array;
            }
        });


    }

    public void inistialize_main_sport() {

        expandableLayout_main.setInterpolator(new LinearInterpolator());
        CircleProgressView circleProgressView;
        circleProgressView = (CircleProgressView) rootView.findViewById(R.id.circleView);
        Typeface font = Typeface.createFromAsset(c.getAssets(), "font/iran_sans.ttf");
        circleProgressView.setTextTypeface(font);
        circleProgressView.setText(get_suggestion(circleProgressView));

        CardView cv = (CardView) rootView.findViewById(R.id.main_sport_cardview);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  expandableLayout_main.toggle();
                Dialog_exercise_5dayago aa = new Dialog_exercise_5dayago();
                aa.qrcode_reader(c);
                aa.show();
            }
        });
        expandmanage(expandableLayout_main, expandableLayout_cold, expandableLayout_warm);
        main_mBubbleSeekBar = (BubbleSeekBar) rootView.findViewById(R.id.main_sport_seekBar);
        main_mBubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                Log.d("seek", String.valueOf(progress));
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
        main_mBubbleSeekBar.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "خیلی آسان");
                array.put(1, "نسبتا آسان");
                array.put(2, "نسبتا سخت");
                array.put(3, "خیلی سخت");

                return array;
            }
        });


        com.codesgood.views.JustifiedTextView tv_main_ex = (com.codesgood.views.JustifiedTextView) rootView.findViewById(R.id.main_exercise_txt);
        //  tv_main_ex.setText(Jsoup.parse(DataBase_read.give_messages_daily(MainActivity.mainContext, "main_exercise")).text());

        tv_main_ex.setText(android.text.Html.fromHtml(DataBase_read.give_messages_daily(MainActivity.mainContext, "main_exercise")).toString());
    }

    public void inistialize_code_down() {

        expandableLayout_cold.setInterpolator(new LinearInterpolator());
        CardView cv = (CardView) rootView.findViewById(R.id.cold_down_cardview);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout_cold.toggle();

            }
        });
        expandmanage(expandableLayout_cold, expandableLayout_warm, expandableLayout_main);
        title = new ArrayList<>();
        title.add("درجا زدن آرام");
        title.add("حرکت گردن به طرفین");
        title.add("حرکت گردن به جلو وعقب");
        title.add("کشش دست رو به جلو");
        title.add("کش آرنج رو به عقب");
        title.add("کشش شانه");
        title.add("کشش به پهلو");
        title.add("نگه داشتن زانو در شکم");
        title.add("کشش پا از پشت");
        title.add("کشش ماهیچه پشت ساق");
        title.add("بالاآمدن روی پنجه پا");
        title.add("کشش پشت پا");


        colddown_list = new ArrayList<>();
        for (String s : title
                ) {
            Exercise e = new Exercise();
            e.setTitle(s);
            e.setIs_checked(0);
            colddown_list.add(e);
        }

        int[] img_list = new int[]{R.raw.darja_zadan_img, R.raw.harkat_gardan_be_tarafein_img, R.raw.harkat_gardan_be_jelo_va_aghab_img,
                R.raw.keshesh_dast_ro_be_jelo_img, R.raw.keshesh_arenj_ro_be_aghab_img, R.raw.keshesh_shane_img, R.raw.keshesh_be_pahlo_img,
                R.raw.negah_dashtan_zano_dar_shekam_img, R.raw.keshesh_pa_az_posht_img, R.raw.keshesh_mahiche_poshe_sagh_pa_img, R.raw.bala_amadan_roye_panje_pa_img,
                R.raw.keshesh_posht_pa_img};


        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.cold_down_rcl_chbox);
        GridLayoutManager layoutManager = new GridLayoutManager(c, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter_insert_exercise_checkbox madapter = new adapter_insert_exercise_checkbox(colddown_list, img_list,"colddown");
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        alphaAdapter.setFirstOnly(true);
        recyclerView.setAdapter(alphaAdapter);
        expandableLayout_cold.setClosePosition(0);

        colddown_mBubbleSeekBar = (BubbleSeekBar) rootView.findViewById(R.id.cold_down_seekBar);
        colddown_mBubbleSeekBar.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "خیلی آسان");
                array.put(1, "نسبتا آسان");
                array.put(2, "نسبتا سخت");
                array.put(3, "خیلی سخت");

                return array;
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

    public String get_suggestion(CircleProgressView circleProgressView) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
        Long start_date = prefs.getLong("start_date", 0);
        long daysDiff = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - start_date);
        int week = (int) (daysDiff / 7);
        switch (week) {
            case 0:
                circleProgressView.setValue(10);
                return "10 دقیقه" + "\n" + " دوی کوتاه و آهسته";


            case 1:
                circleProgressView.setValue(10);
                return "10 دقیقه" + "\n" + " دوی کوتاه و آهسته";

            case 2:
                circleProgressView.setValue(15);
                return "15 دقیقه" + "\n" + " دوی معمول و راحت";

            case 3:
                circleProgressView.setValue(20);
                return "20 دقیقه" + "\n" + " دوی معمول و راحت";

            case 4:
                circleProgressView.setValue(25);
                return "25 دقیقه" + "\n" + " دوی بلند و راحت";

            case 5:
                circleProgressView.setValue(25);
                return "25 دقیقه" + "\n" + " دوی بلند و راحت";


        }
        return "";
    }

    public int hardness(int i) {
        switch (i) {
            case 9:
                return 9;
            case 12:
                return 11;
            case 14:
                return 13;
            case 17:
                return 17;
        }
        return i;
    }

    public void clearNotification(int id) {
        NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    public void expandmanage(ExpandableRelativeLayout e1, ExpandableRelativeLayout e2, ExpandableRelativeLayout e3) {

        e1.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
                e2.collapse();
                e3.collapse();

                sport_scroll.fullScroll(ScrollView.FOCUS_UP);
            }

            @Override
            public void onPreClose() {

            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });
    }
    public void select_lay(List<LinearLayout> lays,int currentStep){
        for (LinearLayout lay:lays
             ) {lay.setVisibility(View.GONE);

        }
        lays.get(currentStep).setVisibility(View.VISIBLE);
    }

}