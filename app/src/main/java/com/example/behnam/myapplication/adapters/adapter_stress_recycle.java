package com.example.behnam.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.behnam.myapplication.R;

import java.util.ArrayList;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_stress_recycle extends RecyclerView.Adapter<adapter_stress_recycle.MyViewHolder> {
    private ArrayList<String> data_services_list;


    OnCardClickListner onCardClickListner;
Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView card_title;
        public CardView CV;
        public MyViewHolder(View view) {
            super(view);
            card_title = (TextView) view.findViewById(R.id.menu_txt);

            CV = (CardView) view.findViewById(R.id.cardview_be_intelligent);
            // Toast.makeText(view.getContext(),"تست", Toast.LENGTH_SHORT).show();


        }
    }


    public adapter_stress_recycle(ArrayList<String> web) {
        this.data_services_list = web;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item_stress, parent, false);

context=parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String data_service = data_services_list.get(position);


        holder.card_title.setText(data_service);

        holder.CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
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