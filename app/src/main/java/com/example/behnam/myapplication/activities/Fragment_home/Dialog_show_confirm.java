package com.example.behnam.myapplication.activities.Fragment_home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.VideoView;


import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_list_animation;
import com.example.behnam.myapplication.database_pack.DataBase;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.objects.Animation;
import com.example.behnam.myapplication.objects.Object_animation_list;
import com.nshmura.snappysmoothscroller.SnapType;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;


public class Dialog_show_confirm {

    Context context;
    JustifiedTextView tv_anim_text;
    Dialog dialog;
    WebView wv;

    @SuppressLint("ResourceAsColor")
    public Dialog qrcode_reader(Context context, String title, String content) {
        this.context = context;

        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog = new Dialog(context, android.R.style.Theme_Holo_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog_show);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.primarydark_dark));
        }

        //  ald_insert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        tv_anim_text = (JustifiedTextView) dialog.findViewById(R.id.card_title);
        tv_anim_text.setText(title);
        wv = (WebView) dialog.findViewById(R.id.webView1);
        String summary = "";

        summary = "<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\" />\n" + "<html>\n" +
                "<head><title>Title of the document</title></head>\n" +
                "<body>\n" +
                "<div class=\"c2\">\n" + content +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        wv.loadDataWithBaseURL("file:///android_asset/", summary, "text/html; charset=utf-8", null, null);
        FButton cancel = (FButton) dialog.findViewById(R.id.btn_report2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* FragmentManager manager =a.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = null;
                fragment = new Fragment_insert_exercise();
                transaction.add(R.id.container, fragment);
                transaction.commit();
                */
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public void show() {
        dialog.show();


    }


}
