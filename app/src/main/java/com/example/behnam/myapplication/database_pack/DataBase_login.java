package com.example.behnam.myapplication.database_pack;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static android.content.Context.ACTIVITY_SERVICE;

public class DataBase_login {
    public static Boolean write_profile(User user) {
        DataBase.getInstance(MainActivity.mainContext).getDb().execSQL("delete from " + "UserInfo");
        ContentValues cv;
        cv = new ContentValues();
        cv.put("id", user.getId());
        cv.put("username", user.getUsername());
        cv.put("firstName", user.getFirstName());
        cv.put("lastName", user.getLastName());
        cv.put("parentName", user.getParentName());
        cv.put("nationalCode", user.getNationalCode());
        cv.put("education", user.getEducation());
        cv.put("birthDate", user.getBirthDate());
        cv.put("gender", user.getGender());
        cv.put("job", user.getJob());
        cv.put("phone", user.getPhone());
        cv.put("address", user.getAddress());
        cv.put("weight", user.getWeight());
        cv.put("height", user.getHeight());
        DataBase.getInstance(MainActivity.mainContext).getDb().insert("UserInfo", null, cv);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("birthDate", user.getBirthDate());
        prefsEditor.commit();

        return true;
    }

    public static boolean islogin(Context context) {

        Cursor cr = DataBase.getInstance(context).getDb().rawQuery("select * from UserInfo", null);
        cr.moveToFirst();
        if (cr.getCount() == 0) {
            return false;
        } else return true;
    }

    public static void logout(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("token", "");
        prefsEditor.commit();
        DataBase.getInstance(context).getDb().execSQL("delete from " + "UserInfo");
        DataBase.getInstance(context).getDb().execSQL("delete from " + "Signs");
        DataBase.getInstance(context).getDb().execSQL("delete from " + "DailyData");
        DataBase.getInstance(context).getDb().execSQL("delete from " + "doctor_message");
        DataBase.getInstance(context).getDb().execSQL("delete from " + "MI_BAND_ACTIVITY_SAMPLE");
//
    }
}
