package com.example.behnam.myapplication.adapters;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.my_custom_component.TextViewEx;
import com.example.behnam.myapplication.objects.HomeRecycleView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_home_recycle extends RecyclerView.Adapter<adapter_home_recycle.MyViewHolder> {
    private List<HomeRecycleView> data_services_list;

    ArrayList<Integer> colorlist = new ArrayList<>();
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView card_title, card_info;
        public ImageView card_image;
        public CardView CV;

        public MyViewHolder(View view) {
            super(view);


            card_title = (TextView) view.findViewById(R.id.main_sport_title);
            // card_info = (TextViewEx) view.findViewById(R.id.home_card_info);
            card_image = (ImageView) view.findViewById(R.id.home_card_image);
            CV = (CardView) view.findViewById(R.id.cardview_be_intelligent);
            // Toast.makeText(view.getContext(),"تست", Toast.LENGTH_SHORT).show();


        }
    }


    public adapter_home_recycle(ArrayList<HomeRecycleView> data_services_list) {
        this.data_services_list = data_services_list;

        SharedPreferences prefs2 = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainContext);


        if (prefs2.getBoolean("10001", false)) {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.primary_dark));
        } else {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.chart_not_worn_light));
        }
        if (prefs2.getBoolean("10002", false)) {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.primary_dark));
        } else {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.chart_not_worn_light));
        }
        if (prefs2.getBoolean("10003", false)) {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.primary_dark));
        } else {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.chart_not_worn_light));
        }
        if (prefs2.getBoolean("10004", false)) {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.primary_dark));
        } else {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.chart_not_worn_light));
        }
        if (prefs2.getBoolean("10005", false)) {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.primary_dark));
        } else {
            colorlist.add(ContextCompat.getColor(MainActivity.mainContext, R.color.chart_not_worn_light));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item_home_recycleview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final HomeRecycleView data_service = data_services_list.get(position);
        if (data_service.getMainText().equals("وضعیت سلامت روزانه")) {
            // holder.card_title.setTextColor(colorlist.get(0));
            //   holder.card_image.setColorFilter(colorlist.get(0));
            ImageViewCompat.setImageTintList(holder.card_image, ColorStateList.valueOf(colorlist.get(0)));
        } else if (data_service.getMainText().equals("ورزش")) {
            // holder.card_title.setTextColor(colorlist.get(1));
            ImageViewCompat.setImageTintList(holder.card_image, ColorStateList.valueOf(colorlist.get(1)));
        } else if (data_service.getMainText().equals("علائم بیماری")) {
            // holder.card_title.setTextColor(colorlist.get(2));
            ImageViewCompat.setImageTintList(holder.card_image, ColorStateList.valueOf(colorlist.get(2)));
        } else if (data_service.getMainText().equals("حالت روحی و آرام سازی")) {
            // holder.card_title.setTextColor(colorlist.get(3));
            ImageViewCompat.setImageTintList(holder.card_image, ColorStateList.valueOf(colorlist.get(3)));
        }


        holder.card_title.setText(data_service.getMainText());
      /*  SpannableStringBuilder str = new SpannableStringBuilder(data_service.getMainText());
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.card_title.setText(str);
        */
        //  holder.card_info.setText(data_service.getSubText());
        holder.card_image.setImageResource(data_service.getMianImage());
        holder.CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_services_list.size();
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }
}