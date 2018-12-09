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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;


import com.codesgood.views.JustifiedTextView;
import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_list_animation;
import com.example.behnam.myapplication.database_pack.DataBase;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.objects.Object_animation_list;
import com.example.behnam.myapplication.objects.Animation;
import com.nshmura.snappysmoothscroller.SnapType;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.ArrayList;


public class animation_alertdialog {

    Context context;
    JustifiedTextView tv_anim_text;
    TextView tv_anim_name;
    Dialog dialog;
    ArrayList<Animation> anim_list;
    VideoView vid;
    String[] anim;
    int[] img_list;

    @SuppressLint("ResourceAsColor")
    public Dialog qrcode_reader(Context context, String type,int position) {
        this.context = context;

        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog = new Dialog(context, android.R.style.Theme_Holo_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.exercise_dialog_exercise_animation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;



        ImageView iv_cancel = (ImageView) dialog.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.primarydark_dark));
        }

        //  ald_insert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        vid = (VideoView) dialog.findViewById(R.id.videoView);

        if (type.equals("warm_up")) {
            anim = new String[]{String.valueOf(R.raw.darja_zadan), String.valueOf(R.raw.boland_kardan_zano_va_tamas_ba_dast_movafegh), String.valueOf(R.raw.boland_kardan_zano_va_tamas_ba_dast_mokhalef), String.valueOf(R.raw.charkhesh_moch_pa),
                    String.valueOf(R.raw.bala_amadan_roye_panje_pa), String.valueOf(R.raw.kham_va_rast_kardan_zano), String.valueOf(R.raw.charkhesh_kamar), String.valueOf(R.raw.charkhesh_be_pahlo_harmrah_ba_charkhesh_dast),
                    String.valueOf(R.raw.charkhesh_dast_jam), String.valueOf(R.raw.charkhesh_dast_baz), String.valueOf(R.raw.keshesh_shane), String.valueOf(R.raw.harkat_gardan_be_tarafein)
                    , String.valueOf(R.raw.harkat_gardan_be_jelo_va_aghab),
                    String.valueOf(R.raw.ghadam_zadan_az_pahlo), String.valueOf(R.raw.ghadam_zadan_az_pahlo_hamrah_ba_harkat_dast)
            };
            img_list = new int[]{R.raw.darja_zadan_img, R.raw.boland_kardan_zano_va_tamas_ba_dast_movafegh_img, R.raw.boland_kardan_zano_va_tamas_ba_dast_mokhalef_img,
                    R.raw.charkhesh_moch_pa_img, R.raw.bala_amadan_roye_panje_pa_img, R.raw.kham_va_rast_kardan_zano_img, R.raw.charkhesh_kamar_img,
                    R.raw.charkhesh_be_pahlo_harmrah_ba_charkhesh_dast_img, R.raw.charkhesh_dast_jam_img, R.raw.charkhesh_dast_baz_img, R.raw.keshesh_shane_img,
                    R.raw.harkat_gardan_be_tarafein_img, R.raw.harkat_gardan_be_jelo_va_aghab_img, R.raw.ghadam_zadan_az_pahlo_img, R.raw.ghadam_zadan_az_pahlo_ba_dast_img};
        } else {
            anim = new String[]{String.valueOf(R.raw.darja_zadan), String.valueOf(R.raw.harkat_gardan_be_tarafein), String.valueOf(R.raw.harkat_gardan_be_jelo_va_aghab), String.valueOf(R.raw.keshesh_dast_ro_be_jelo),
                    String.valueOf(R.raw.keshesh_arenj_ro_be_aghab), String.valueOf(R.raw.keshesh_shane), String.valueOf(R.raw.keshesh_be_pahlo), String.valueOf(R.raw.negah_dashtan_zano_dar_shekam),
                    String.valueOf(R.raw.keshesh_pa_az_posht), String.valueOf(R.raw.keshesh_mahiche_poshe_sagh_pa), String.valueOf(R.raw.bala_amadan_roye_panje_pa), String.valueOf(R.raw.keshesh_posht_pa)

            };
            img_list = new int[]{R.raw.darja_zadan_img, R.raw.harkat_gardan_be_tarafein_img, R.raw.harkat_gardan_be_jelo_va_aghab_img,
                    R.raw.keshesh_dast_ro_be_jelo_img, R.raw.keshesh_arenj_ro_be_aghab_img, R.raw.keshesh_shane_img, R.raw.keshesh_be_pahlo_img,
                    R.raw.negah_dashtan_zano_dar_shekam_img, R.raw.keshesh_pa_az_posht_img, R.raw.keshesh_mahiche_poshe_sagh_pa_img, R.raw.bala_amadan_roye_panje_pa_img,
                    R.raw.keshesh_posht_pa_img};
        }
        vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });


        ArrayList<Object_animation_list> oal_list = new ArrayList<>();
        Object_animation_list oal = new Object_animation_list();

        DataBase.getInstance(context).getDb();
        anim_list = new ArrayList<>();
        anim_list = DataBase_read.give_animation(context, type);
        int i = 0;
        for (Animation a : anim_list
                ) {

            oal.setText(a.getAnim_name());
            oal.setImg(img_list[i]);
            oal_list.add(oal);
            oal = new Object_animation_list();
            i++;
        }


        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.rcl_animation_list);
        SnappyLinearLayoutManager sllm = new SnappyLinearLayoutManager(dialog.getContext(), LinearLayoutManager.HORIZONTAL, false);

        sllm.setSnapType(SnapType.CENTER);
// Set the Interpolator
        sllm.setSnapInterpolator(new DecelerateInterpolator());


        recyclerView.setLayoutManager(sllm);
        adapter_list_animation madapter = new adapter_list_animation(oal_list);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        recyclerView.setAdapter(madapter);
        recyclerView.invalidate();

        tv_anim_text = (JustifiedTextView) dialog.findViewById(R.id.tv_anim_text);
        tv_anim_name = (TextView) dialog.findViewById(R.id.tv_anim_name);
        madapter.setOnCardClickListner(new adapter_list_animation.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                play_video(position);
            }
        });
        play_video(position);
        recyclerView.scrollToPosition(position);
        return dialog;
    }

    public void show() {
        dialog.show();


    }

    public void play_video(int position) {
        vid.stopPlayback();
        tv_anim_text.setText(anim_list.get(position).getAnim_text());
        tv_anim_name.setText(anim_list.get(position).getAnim_name());
        String path = "android.resource://" + MainActivity.PACKAGE_NAME + "/" + anim[position];
        vid.setVideoURI(Uri.parse(path));
        vid.start();

    }

    public int Screen_width() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int width2 = displayMetrics.heightPixels;
        return width;
    }

}
