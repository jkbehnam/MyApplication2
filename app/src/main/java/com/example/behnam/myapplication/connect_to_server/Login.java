package com.example.behnam.myapplication.connect_to_server;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.behnam.myapplication.activities.Login_acirivty;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.database_pack.DataBase_login;
import com.example.behnam.myapplication.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login {
    public static void GetUserData(AppCompatActivity a){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_IDENTITY,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            response= response.replace("[","");
                            response= response.replace("]","");
                            JSONObject obj = new JSONObject(response);
                            DataBase_login.write_profile(Login.getUser(obj));
                            //if no error in response
                            Intent i = new Intent(a, MainActivity.class);
                            a.startActivity(i);
                            a.finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.mainContext, "خطا در اتصال به اینترنت", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);
                String userToken = prefs.getString("token", "");
                params.put("authorization", userToken);
                return params;
            }
        };

        VolleySingleton.getInstance(MainActivity.mainContext).addToRequestQueue(stringRequest);
    }

    public static User getUser(JSONObject obj){
        User user=new User();
        try {

        user.setId(obj.getInt("id"));
        user.setUsername(obj.getString("username"));
        user.setFirstName(obj.getString("firstName"));
        user.setLastName(obj.getString("lastName"));
        user.setParentName(obj.getString("parentName"));
        user.setNationalCode(obj.getString("nationalCode"));
        user.setBirthDate(obj.getString("birthDate"));
        user.setGender(obj.getString("gender"));
        user.setJob(obj.getString("job"));
        user.setEducation(obj.getString("education"));
        user.setPhone(obj.getString("phone"));
        user.setAddress(obj.getString("address"));
        user.setWeight(obj.getString("weight"));
        user.setHeight(obj.getString("height"));

        }catch (Exception e){

        }
        return user;
    }
}
