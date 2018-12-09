package com.example.behnam.myapplication.my_custom_component;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.example.behnam.myapplication.activities.Fragment_home.Dialog_befor_exercise;
import com.example.behnam.myapplication.activities.Fragment_home.Dialog_show_confirm;
import com.example.behnam.myapplication.activities.Fragment_home.animation_alertdialog;
import com.example.behnam.myapplication.activities.Login_acirivty;
import com.example.behnam.myapplication.database_pack.DataBase_read;

import java.util.ArrayList;


public class MyWebViewClient extends WebViewClient {

    private Context context;

    public MyWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url.equals("hrupin://second_activity")){
            ArrayList<String> s= DataBase_read.get_traningS_items4(context, "اندازه گیری فشار خون در منزل","main_training_tb");
            Dialog_show_confirm d=new Dialog_show_confirm();
            d.qrcode_reader(context,"اندازه گیری فشار خون در منزل",s.get(0));
            d.show();
            return true;
        }
        if(url.equals("hrupin://second_activity2")){
            ArrayList<String> s= DataBase_read.get_traningS_items4(context, "چه کاری می توانم انجام دهم تا فشارخونم را کنترل کنم؟","main_training_tb");
            Dialog_show_confirm d=new Dialog_show_confirm();
            d.qrcode_reader(context,"چه کاری می توانم انجام دهم تا فشارخونم را کنترل کنم؟",s.get(0));
            d.show();
            return true;
        }
        if(url.equals("hrupin://second_activity3")){
            ArrayList<String> s= DataBase_read.get_traningS_items4(context, "مصرف روزانه  انواع غذاهای مفید","main_training_tb");
            Dialog_show_confirm d=new Dialog_show_confirm();
            d.qrcode_reader(context,"مصرف روزانه  انواع غذاهای مفید",s.get(0));
            d.show();
            return true;
        }
        if(url.equals("hrupin://second_activity4")){
          //  Dialog_show_confirm d=new Dialog_show_confirm();
          //  d.qrcode_reader(context,"سلام","سلام");
            //d.show();
            return true;
        }
        if(url.equals("hrupin://second_activity5")){
          //  Dialog_show_confirm d=new Dialog_show_confirm();
          //  d.qrcode_reader(context,"سلام","سلام");
            return true;
        }
        if(url.equals("hrupin://second_activity12")){
         //   animation_alertdialog aa=new animation_alertdialog();
          //  aa.qrcode_reader(context,"warm_up");
          //  aa.show();
            return true;
        }
        if(url.equals("hrupin://second_activity13")){
           // animation_alertdialog aa=new animation_alertdialog();
           // aa.qrcode_reader(context,"cold_down");
           // aa.show();
            return true;
        }
        if(url.equals("hrupin://second_activity14")){
            //alaem fiziki dar tol varzesh
            /*
            ArrayList<String> s= DataBase_read.get_traningS_items4(context, "اندازه گیری فشار خون در منزل","main_training_tb");
            Dialog_show_confirm d=new Dialog_show_confirm();
            d.qrcode_reader(context,"اندازه گیری فشار خون در منزل",s.get(0));
            d.show();
            */
            return true;
        }

        return true;
    }
}