package com.example.behnam.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.objects.Object_animation_list;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_list_animation extends RecyclerView.Adapter<adapter_list_animation.MyViewHolder> {
    private List<Object_animation_list> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView card_title;
        public ImageView img;
        public CardView cv;
        public MultiLineRadioGroup mrg;

        public MyViewHolder(View view) {
            super(view);
            card_title = (TextView) view.findViewById(R.id.tvTitle);

            img = (ImageView) view.findViewById(R.id.itemImage);
            cv = (CardView) view.findViewById(R.id.cv_anim);
        }
    }


    public adapter_list_animation(ArrayList<Object_animation_list> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item_animation, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Object_animation_list data_service = data_services_list.get(position);

        holder.card_title.setText(data_service.getText());
       // holder.img.setImageResource(data_service.getImg());
        Glide.with(context).load("android.resource://" + com.example.behnam.myapplication.activities.MainActivity.PACKAGE_NAME + "/" + String.valueOf(data_service.getImg())).into(holder.img);
        holder.cv.setOnClickListener(new View.OnClickListener() {
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