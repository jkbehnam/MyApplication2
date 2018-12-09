package com.example.behnam.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.Fragment_home.Dialog_befor_exercise;

import com.example.behnam.myapplication.connect_to_server.Login;
import com.example.behnam.myapplication.connect_to_server.URLs;
import com.example.behnam.myapplication.connect_to_server.VolleySingleton;
import com.example.behnam.myapplication.connect_to_server.update_server_data;
import com.example.behnam.myapplication.database_pack.DataBase_login;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.set_alarm_notify;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


import info.hoang8f.widget.FButton;
import nodomain.freeyourgadget.gadgetbridge.entities.MiBandActivitySample;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Login_acirivty extends AppCompatActivity {
    EditText et_username;
    EditText et_password;
    FButton btnLogin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_login_acirivty);
DataBase_login.logout(MainActivity.mainContext);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btnLogin = (FButton) findViewById(R.id.btnLoginActivityLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Intent i = new Intent(Login_acirivty.this, MainActivityvid.class);
               // startActivity(i);
              //  Intent i = new Intent(Login_acirivty.this, com.adwaitvyas.offlineplayer.MainActivity.class);
             //   startActivity(i);

                 userLogin();
                //update_server_data.GetRiskFactors();
               //   update_server_data.send_last_band_data(MainActivity.mainContext);
                //  update_server_data.GetChatMessages();
                // update_server_data. GetDrugData();
                // update_server_data. GetMaxHeart();
                //update_server_data.send_last_exercise_data();
                // update_server_data.send_band_data(MainActivity.mainContext,new MiBandActivitySample());
                //  update_server_data.send_last_sign_data();
                // Toast.makeText(Login_acirivty.this, DataBase_read.getlastmental("afsordegi"), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void userLogin() {
        //first getting the values
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            et_username.setError("لطفا نام کاربری خود را وارد نمایید");
            et_username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            et_password.setError("لطفا شماره تلفن خود را وارد نمایید");
            et_password.requestFocus();
            return;
        }
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        try {
                            if (response.equals("not_exist")) {

                                Toast.makeText(Login_acirivty.this, "ترکیب نام کاربری و رمز عبور اشتباه است\n" +
                                        "\n", Toast.LENGTH_SHORT).show();
                            }

                            if (response.equals("dbError")) {

                                Toast.makeText(Login_acirivty.this, "خطا در ارتباط با پایگاه داده\n" +
                                        "\n", Toast.LENGTH_SHORT).show();
                            }
                            //converting response to json object

                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(Login_acirivty.this, obj.getString("firstName") + " عزیز به سامانه تپش خوش آمدید", Toast.LENGTH_LONG).show();

                            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            prefsEditor.putString("username", obj.getString("username"));
                            prefsEditor.putString("userId", obj.getString("userId"));
                            prefsEditor.putString("firstName", obj.getString("firstName"));
                            prefsEditor.putString("lastName", obj.getString("lastName"));
                            prefsEditor.putString("role", obj.getString("role"));
                            prefsEditor.putString("token", obj.getString("token"));
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(System.currentTimeMillis());
                           // cal.add(Calendar.DATE, -20);
                            prefsEditor.putLong("start_date", cal.getTimeInMillis());
                            prefsEditor.commit();
                            Login.GetUserData(Login_acirivty.this);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }/* catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "خطا در اتصال به اینترنت", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", faToEn(username));
                params.put("password", faToEn(password));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public static String faToEn(String num) {
        return num
                .replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9");
    }
}
