package com.example.behnam.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.objects.Daily_system_massage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_string_recycle extends RecyclerView.Adapter<adapter_string_recycle.MyViewHolder> {
    private List<String> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public JustifiedTextView card_title;
        TextView card_info;


        public CardView CV;

        public MyViewHolder(View view) {
            super(view);
            card_title = (JustifiedTextView) view.findViewById(R.id.card_title);

          //  card_date = (TextView) view.findViewById(R.id.card_date);
            CV = (CardView) view.findViewById(R.id.cardview_be_intelligent);


        }
    }


    public adapter_string_recycle(ArrayList<String> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_befor_exercise, parent, false);
        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
         String data_service = data_services_list.get(position);
        data_service = data_service.replace("hh","\n");

        holder.card_title.setText(data_service);
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