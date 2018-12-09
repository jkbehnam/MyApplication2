package com.example.behnam.myapplication.activities.Fragment_home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_main_ViewPager;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.objects.DailyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.fragments.BackConfirmationFragment;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;


public class Fragment_AddDailyData extends Fragment implements VerticalStepperForm {

    int have_diabette = 0;
    int is_Smoker = 0;

    public static final String NEW_ALARM_ADDED = "new_alarm_added";

    // Information about the steps/fields of the form


    // Title step
    private EditText titleEditText, titleEditText2;
    private static final int MIN_CHARACTERS_TITLE = 3;
    public static final String STATE_TITLE = "title";

    // Description step
    private EditText descriptionEditText;
    public static final String STATE_DESCRIPTION = "description";
    //blood pressur step
    private EditText et_up_pb, et_down_pb;
    //blood glucose step
    private EditText et_gluose;
    // weight step
    private EditText et_weight;
    //cigarette step
    private EditText et_cigarette;
    //sleep step
    private EditText et_sleep;
    //steps step
    private EditText et_steps;
    //stress step
    private EditText et_heart_rate;
    //sign steps
    String[] stepsTitles;

    private boolean confirmBack = true;
    private ProgressDialog progressDialog;
    private com.example.behnam.myapplication.my_custom_component.VerticalStepperFormLayout verticalStepperForm;
    FragmentActivity c;
    View rootView;
    int myInt;
    LayoutInflater inflater;
    TextInputLayout til1, til2;
    TextView tv_mid;
    LinearLayout timeStepContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_activity_vertical_stepper_form, container, false);
        setRetainInstance(true);
        c = getActivity();

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("ورود شاخص\u200Cهای روزانه");
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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            myInt = bundle.getInt("last_state", 2);
        }
        initializeActivity();

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });

        return rootView;
    }

    private Spannable wrapInCustomfont(String myText) {
        final Typeface typeface = TypefaceUtils.load(c.getAssets(), "font/iran_sans.ttf");
        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(typeface);
        SpannableString spannable = new SpannableString(myText);
        spannable.setSpan(typefaceSpan, 0, myText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private View createBloodPressureStep() {
        // This step view is generated by inflating a layout XML file
        inflater = LayoutInflater.from(c);
        timeStepContent =
                (LinearLayout) inflater.inflate(R.layout.exercise_step_time_layout, null, false);

        til1 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout);
        til1.setHint("میزان فشارخون بالا");
        til2 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout2);
        tv_mid = (TextView) timeStepContent.findViewById(R.id.midText);
        tv_mid.setVisibility(View.VISIBLE);
        til2.setHint("میزان فشارخون پایین");
        til1.setErrorEnabled(true);
        til1.setError(wrapInCustomfont("بین 5 تا 30"));
        til2.setErrorEnabled(true);
        til2.setError(wrapInCustomfont("بین 3 تا 20"));
        et_up_pb = (EditText) timeStepContent.findViewById(R.id.et_step_up_bp);

        et_down_pb = (EditText) timeStepContent.findViewById(R.id.et_step_down_bp);
        et_down_pb.setVisibility(View.VISIBLE);

        et_up_pb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkStep_bp(et_down_pb.getText().toString(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_down_pb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkStep_bp(s.toString(), et_up_pb.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_down_pb.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (checkStep_bp(v.getText().toString(), et_up_pb.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }

                return false;
            }
        });

        return timeStepContent;
    }

    private View createGlucoseStep() {
        // This step view is generated by inflating a layout XML file
        inflater = LayoutInflater.from(c);
        timeStepContent =
                (LinearLayout) inflater.inflate(R.layout.exercise_step_time_layout, null, false);
        til1 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout);
        tv_mid = (TextView) timeStepContent.findViewById(R.id.midText);
        tv_mid.setVisibility(View.GONE);
        til1.setHint("میزان قند خون");
        til1.setErrorEnabled(true);
        til1.setError(wrapInCustomfont("بین 10 تا 1500"));
        til2 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout2);
        til2.setVisibility(View.GONE);
        et_gluose = (EditText) timeStepContent.findViewById(R.id.et_step_up_bp);
        et_gluose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkStep_glucose(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        et_gluose.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (checkStep_glucose(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }

                return false;
            }
        });

        return timeStepContent;
    }

    private View createWeightStep() {
        // This step view is generated by inflating a layout XML file
        inflater = LayoutInflater.from(c);
        timeStepContent =
                (LinearLayout) inflater.inflate(R.layout.exercise_step_time_layout, null, false);
        til2 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout2);
        til2.setVisibility(View.GONE);
        til1 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout);
        tv_mid = (TextView) timeStepContent.findViewById(R.id.midText);
        tv_mid.setVisibility(View.GONE);
        til1.setHint("میزان وزن");
        til1.setErrorEnabled(true);
        til1.setError(wrapInCustomfont("کیلوگرم"));
        et_weight = (EditText) timeStepContent.findViewById(R.id.et_step_up_bp);
        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkStep_weight(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        et_weight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (checkStep_glucose(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }

                return false;
            }
        });

        return timeStepContent;
    }

    private View createCigaretteStep() {
        // This step view is generated by inflating a layout XML file
        inflater = LayoutInflater.from(c);
        timeStepContent =
                (LinearLayout) inflater.inflate(R.layout.exercise_step_time_layout, null, false);
        til2 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout2);
        til2.setVisibility(View.GONE);
        til1 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout);
        tv_mid = (TextView) timeStepContent.findViewById(R.id.midText);
        tv_mid.setVisibility(View.GONE);
        et_cigarette = (EditText) timeStepContent.findViewById(R.id.et_step_up_bp);
        til1.setHint("تعداد مصرف سیگار در شبانه روز");
        et_cigarette.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkStep_cigarette(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        et_cigarette.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (checkStep_cigarette(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }

                return false;
            }
        });
        return timeStepContent;
    }

    private View createSleepStep() {
        // This step view is generated by inflating a layout XML file
        inflater = LayoutInflater.from(c);
        timeStepContent =
                (LinearLayout) inflater.inflate(R.layout.exercise_step_time_layout, null, false);
        til2 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout2);
        til2.setVisibility(View.GONE);
        til1 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout);
        tv_mid = (TextView) timeStepContent.findViewById(R.id.midText);
        tv_mid.setVisibility(View.GONE);
        et_sleep = (EditText) timeStepContent.findViewById(R.id.et_step_up_bp);
        til1.setHint("میزان ساعت خواب در شبانه روز");
        et_sleep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkStep_sleep(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        et_sleep.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (checkStep_sleep(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }

                return false;
            }
        });
        return timeStepContent;
    }

    private View createStepsStep() {
        // This step view is generated by inflating a layout XML file
        inflater = LayoutInflater.from(c);
        timeStepContent =
                (LinearLayout) inflater.inflate(R.layout.exercise_step_time_layout, null, false);
        til2 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout2);
        til2.setVisibility(View.GONE);
        tv_mid = (TextView) timeStepContent.findViewById(R.id.midText);
        tv_mid.setVisibility(View.GONE);
        til1 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout);
        et_steps = (EditText) timeStepContent.findViewById(R.id.et_step_up_bp);
        til1.setHint("تعداد قدم");
        return timeStepContent;
    }

    private View createHeartRateStep() {
        // This step view is generated by inflating a layout XML file
        inflater = LayoutInflater.from(c);
        timeStepContent =
                (LinearLayout) inflater.inflate(R.layout.exercise_step_time_layout, null, false);
        til2 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout2);
        til2.setVisibility(View.GONE);
        tv_mid = (TextView) timeStepContent.findViewById(R.id.midText);
        tv_mid.setVisibility(View.GONE);
        til1 = (TextInputLayout) timeStepContent.findViewById(R.id.text_input_layout);
        et_heart_rate = (EditText) timeStepContent.findViewById(R.id.et_step_up_bp);
        til1.setHint("میزان ضربان قلب");
        return timeStepContent;
    }

    private void initializeActivity() {

        ArrayList<String> items = new ArrayList<>();
        items.add("فشار خون");
        items.add("قند خون");
        items.add("وزن");
        items.add("سیگار");
        items.add("خواب");


        int colorPrimary = ContextCompat.getColor(c, R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(c, R.color.colorPrimaryDark);
        if (have_diabette == 0) {
            items.remove("قند خون");

        }
        if (is_Smoker == 0) {
            items.remove("سیگار");

        }
        stepsTitles = items.toArray(new String[0]);
        //String[] stepsSubtitles = getResources().getStringArray(R.array.steps_subtitles);

        // Here we find and initialize the form
        verticalStepperForm = (com.example.behnam.myapplication.my_custom_component.VerticalStepperFormLayout) rootView.findViewById(R.id.vertical_stepper_form);
        com.example.behnam.myapplication.my_custom_component.VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, stepsTitles, this, c)
                //.materialDesignInDisabledSteps(true) // false by default
                //.showVerticalLineWhenStepsAreCollapsed(true) // false by default
                .primaryColor(colorPrimary)
                .confirmationStepEnabled(true)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false)
                .init();


    }


    @Override
    public View createStepContentView(int stepNumber) {
        // Here we generate the content view of the correspondent step and we return it so it gets
        // automatically added to the step layout (AKA stepContent)
        View view = null;
        if (have_diabette == 0 && is_Smoker == 0) {


            switch (stepNumber) {
                case 0:
                    view = createBloodPressureStep();
                    break;
                case 1:
                    view = createWeightStep();

                    break;
                case 2:
                    view = createSleepStep();
                    break;


            }

        } else if (have_diabette == 0 && is_Smoker == 1) {

            switch (stepNumber) {


                case 0:
                    view = createBloodPressureStep();
                    break;
                case 1:
                    view = createWeightStep();
                    break;
                case 2:

                    view = createCigaretteStep();
                    break;
                case 3:
                    view = createSleepStep();
                    break;


            }

        } else if (have_diabette == 1 && is_Smoker == 0) {

            switch (stepNumber) {


                case 0:
                    view = createBloodPressureStep();
                    break;
                case 1:
                    view = createGlucoseStep();
                    break;
                case 2:
                    view = createWeightStep();
                    break;

                case 3:
                    view = createSleepStep();
                    break;


            }

        } else if (have_diabette == 1 && is_Smoker == 1) {

            switch (stepNumber) {


                case 0:
                    view = createBloodPressureStep();
                    break;
                case 1:
                    view = createGlucoseStep();
                    break;
                case 2:
                    view = createWeightStep();
                    break;
                case 3:

                    view = createCigaretteStep();
                    break;
                case 4:
                    view = createSleepStep();
                    break;


            }

        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        if (have_diabette == 0 && is_Smoker == 1) {

            switch (stepNumber) {
                case 0:
                    checkStep_bp(et_down_pb.getText().toString(), et_up_pb.getText().toString());
                    break;
                // When this step is open, we check that the title is correct
                case 1:
                    checkStep_weight(et_weight.getText().toString());
                    break;

                case 2:
                    checkStep_cigarette(et_cigarette.getText().toString());
                    break;

                case 3:
                    checkStep_sleep(et_sleep.getText().toString());
                    break;

                case 9:
                    verticalStepperForm.setStepAsCompleted(stepNumber);

                    break;

            }
        } else if (have_diabette == 0 && is_Smoker == 0) {

            switch (stepNumber) {
                case 0:
                    checkStep_bp(et_down_pb.getText().toString(), et_up_pb.getText().toString());
                    break;
                // When this step is open, we check that the title is correct

                case 1:
                    checkStep_weight(et_weight.getText().toString());
                    break;


                case 3:
                    checkStep_sleep(et_sleep.getText().toString());
                    break;

                case 9:
                    verticalStepperForm.setStepAsCompleted(stepNumber);

                    break;

            }
        } else if (have_diabette == 1 && is_Smoker == 0) {

            switch (stepNumber) {
                case 0:
                    checkStep_bp(et_down_pb.getText().toString(), et_up_pb.getText().toString());
                    break;
                // When this step is open, we check that the title is correct
                case 1:
                    checkStep_glucose(et_gluose.getText().toString());
                    break;
                case 2:
                    checkStep_weight(et_weight.getText().toString());
                    break;
                case 3:
                    checkStep_sleep(et_sleep.getText().toString());
                    break;

                case 9:
                    verticalStepperForm.setStepAsCompleted(stepNumber);

                    break;

            }
        } else if (have_diabette == 1 && is_Smoker == 1) {

            switch (stepNumber) {
                case 0:
                    checkStep_bp(et_down_pb.getText().toString(), et_up_pb.getText().toString());
                    break;
                // When this step is open, we check that the title is correct
                case 1:
                    checkStep_glucose(et_gluose.getText().toString());
                    break;
                case 2:
                    checkStep_weight(et_weight.getText().toString());
                    break;

                case 3:
                    checkStep_cigarette(et_cigarette.getText().toString());
                    break;

                case 4:
                    checkStep_sleep(et_sleep.getText().toString());
                    break;

                case 9:
                    verticalStepperForm.setStepAsCompleted(stepNumber);

                    break;

            }
        }
    }

    @Override
    public void sendData() {
        DailyData d_data = new DailyData();
        d_data.setUp_pb(Double.parseDouble(et_up_pb.getText().toString()) * 10);
        d_data.setDown_pb(Double.parseDouble(et_down_pb.getText().toString()) * 10);
        if (have_diabette == 1) {
            d_data.setGluose(Double.parseDouble(et_gluose.getText().toString()));
        } else {
            d_data.setGluose(0);
        }
        if (is_Smoker == 1) {
            d_data.setCigarette(Double.parseDouble(et_cigarette.getText().toString()));
        } else {
            d_data.setCigarette(0);
        }
        d_data.setEt_weight(Double.parseDouble(et_weight.getText().toString()));
        d_data.setSleep(Double.parseDouble(et_sleep.getText().toString()));
        DailyData today_s_h = DataBase_read.get_today_steps_heart_minmax(c);
        d_data.setHeart_rate_min(today_s_h.getHeart_rate_min());
        d_data.setHeart_rate_max(today_s_h.getHeart_rate_max());
        d_data.setLast_heart_rate(today_s_h.getLast_heart_rate());
        d_data.setSteps(today_s_h.getSteps());

        Dialog_AddDailyData_confirm aa = new Dialog_AddDailyData_confirm();
        Dialog ad = aa.qrcode_reader(c, d_data, c);
        ad.show();
    }

    // OTHER METHODS USED TO MAKE THIS EXAMPLE WORK


    private boolean checkStep_bp(String bp_down, String bp_up) {
        boolean titleIsCorrect = false;
        try {


            if (Integer.parseInt(bp_up) >= 5 && Integer.parseInt(bp_up) <= 30 && Integer.parseInt(bp_down) >= 3 && Integer.parseInt(bp_down) <= 20) {
                titleIsCorrect = true;

                verticalStepperForm.setActiveStepAsCompleted();
                // Equivalent to: verticalStepperForm.setStepAsCompleted(TITLE_STEP_NUM);

            } else {
                //  String titleErrorString = getResources().getString(R.string.error_title_min_characters);
                String titleErrorString = "به عنوان مثال: 12 روی 8";
                // String titleErrorString = "";
                String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

                verticalStepperForm.setActiveStepAsUncompleted(titleError);
                // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);

            }
        } catch (Exception e) {
            String titleErrorString = "به عنوان مثال: 12 روی 8";
            String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

            verticalStepperForm.setActiveStepAsUncompleted(titleError);
        }
        return titleIsCorrect;
    }

    private boolean checkStep_glucose(String title) {
        boolean titleIsCorrect = false;
        try {
            if (Integer.parseInt(title) >= 10 && Integer.parseInt(title) <= 1500) {
                titleIsCorrect = true;

                verticalStepperForm.setActiveStepAsCompleted();
                // Equivalent to: verticalStepperForm.setStepAsCompleted(TITLE_STEP_NUM);

            } else {
                //  String titleErrorString = "مقدار قند خون خود را (بین 10 تا 1500) وارد کنید";
                String titleErrorString = "";
                String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

                verticalStepperForm.setActiveStepAsUncompleted(titleError);
                // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);

            }
        } catch (Exception e) {
            String titleErrorString = "";
            String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

            verticalStepperForm.setActiveStepAsUncompleted(titleError);
        }
        return titleIsCorrect;
    }

    private boolean checkStep_weight(String title) {
        boolean titleIsCorrect = false;
        try {
            if (Integer.parseInt(title) >= 20 && Integer.parseInt(title) <= 200) {
                titleIsCorrect = true;

                verticalStepperForm.setActiveStepAsCompleted();
                // Equivalent to: verticalStepperForm.setStepAsCompleted(TITLE_STEP_NUM);

            } else {
                //  String titleErrorString = "وزن خود را (بین 20 تا 200) وارد کنید";
                String titleErrorString = "";
                String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);
                verticalStepperForm.setActiveStepAsUncompleted(titleError);
                // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);

            }
        } catch (Exception e) {
            String titleErrorString = "";
            String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

            verticalStepperForm.setActiveStepAsUncompleted(titleError);
        }
        return titleIsCorrect;
    }

    private boolean checkStep_cigarette(String title) {
        boolean titleIsCorrect = false;
        try {
            if (Integer.parseInt(title) >= 0 && Integer.parseInt(title) <= 100) {
                titleIsCorrect = true;

                verticalStepperForm.setActiveStepAsCompleted();
                // Equivalent to: verticalStepperForm.setStepAsCompleted(TITLE_STEP_NUM);

            } else {
                // String titleErrorString = "تعداد سیگار کشیده شده در روز را (بین 0 تا 100) وارد کنید ";
                String titleErrorString = "";

                String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

                verticalStepperForm.setActiveStepAsUncompleted(titleError);
                // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);

            }
        } catch (Exception e) {
            String titleErrorString = "";
            String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

            verticalStepperForm.setActiveStepAsUncompleted(titleError);
        }
        return titleIsCorrect;
    }

    private boolean checkStep_sleep(String title) {
        boolean titleIsCorrect = false;
        try {
            if (Integer.parseInt(title) >= 0 && Integer.parseInt(title) <= 24) {
                titleIsCorrect = true;

                verticalStepperForm.setActiveStepAsCompleted();
                // Equivalent to: verticalStepperForm.setStepAsCompleted(TITLE_STEP_NUM);
            } else {
                // String titleErrorString = "میزان خواب خود را (بین 0 تا 24) وارد کنید";
                String titleErrorString = "";
                String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

                verticalStepperForm.setActiveStepAsUncompleted(titleError);
                // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);

            }
        } catch (Exception e) {
            String titleErrorString = "";
            String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

            verticalStepperForm.setActiveStepAsUncompleted(titleError);
        }
        return titleIsCorrect;
    }
    // CONFIRMATION DIALOG WHEN USER TRIES TO LEAVE WITHOUT SUBMITTING

    private void confirmBack() {
        if (confirmBack && verticalStepperForm.isAnyStepCompleted()) {
            BackConfirmationFragment backConfirmation = new BackConfirmationFragment();
            backConfirmation.setOnConfirmBack(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmBack = true;
                }
            });
            backConfirmation.setOnNotConfirmBack(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmBack = false;
                    c.finish();
                }
            });
            backConfirmation.show(c.getSupportFragmentManager(), null);
        } else {
            confirmBack = false;
            c.finish();
        }
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && confirmBack) {
            confirmBack();
            return true;
        }
        return false;
    }


    // SAVING AND RESTORING THE STATE


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
}