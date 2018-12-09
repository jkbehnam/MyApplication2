
package com.example.behnam.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.objects.User;

import java.util.Calendar;

import saman.zamani.persiandate.PersianDate;


public class Fragment_profile extends Fragment {
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_fragment_profile, container, false);
        setRetainInstance(true);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText("پروفایل بیمار");

        initial_ets(DataBase_read.get_user_profile(MainActivity.mainContext));
        return rootView;
    }

    public void initial_ets(User user) {
        EditText et_firstName = (EditText) rootView.findViewById(R.id.et_firstName);
        EditText et_lastName = (EditText) rootView.findViewById(R.id.et_lastName);
        EditText et_parentName = (EditText) rootView.findViewById(R.id.et_parentName);
        EditText et_gender = (EditText) rootView.findViewById(R.id.et_gender);
        EditText et_phone = (EditText) rootView.findViewById(R.id.et_phone);
        EditText et_nationalCode = (EditText) rootView.findViewById(R.id.et_nationalCode);
        EditText et_job = (EditText) rootView.findViewById(R.id.et_job);
        EditText et_education = (EditText) rootView.findViewById(R.id.et_education);
        EditText et_address = (EditText) rootView.findViewById(R.id.et_address);
        EditText et_phone2 = (EditText) rootView.findViewById(R.id.et_phone2);
        EditText et_birthday = (EditText) rootView.findViewById(R.id.et_birthDate);

        pref_edittext(et_firstName);
        pref_edittext(et_lastName);
        pref_edittext(et_parentName);
        pref_edittext(et_gender);
        pref_edittext(et_phone);
        pref_edittext(et_nationalCode);
        pref_edittext(et_job);
        pref_edittext(et_education);
        pref_edittext(et_address);
        pref_edittext(et_phone2);
        pref_edittext(et_birthday);
        try {
         Long L=   Long.parseLong(user.getBirthDate())*1000;
            PersianDate pdate = new PersianDate(L);
            et_birthday.setText(String.valueOf(pdate.getShDay()) + " " + pdate.monthName() + " " + String.valueOf(pdate.getShYear()));
        } catch (Exception e) {
        }

        et_firstName.setText(user.getFirstName());
        et_lastName.setText(user.getLastName());
        et_parentName.setText(user.getParentName());
        et_gender.setText(user.getGender());
        et_phone.setText(user.getUsername());
        et_nationalCode.setText(user.getNationalCode());
        et_job.setText(user.getJob());
        et_education.setText(user.getEducation());
        et_address.setText(user.getAddress());
        et_phone2.setText(user.getPhone());

    }

    public void pref_edittext(EditText e) {
        e.setFocusable(false);
        e.setClickable(true);

    }
}