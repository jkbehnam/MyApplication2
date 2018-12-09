package com.example.behnam.myapplication.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.objects.Mental_multiple_choice_questions;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_ezterab_test extends RecyclerView.Adapter<adapter_ezterab_test.MyViewHolder> {
    private List<Mental_multiple_choice_questions> data_services_list;


    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView card_title;
        public MultiLineRadioGroup mrg;

        public MyViewHolder(View view) {
            super(view);
            card_title = (TextView) view.findViewById(R.id.test_textView);

            mrg = (MultiLineRadioGroup) view.findViewById(R.id.main_activity_multi_line_radio_group);
            mrg.setMaxInRow(2);


            mrg.addButtons(0,"هرگز");
            mrg.addButtons(1,"چندین روز");
            mrg.addButtons(2,"بیش از نیمی از روز ها");
            mrg.addButtons(3,"تقریبا هر روز");


        }
    }


    public adapter_ezterab_test(ArrayList<Mental_multiple_choice_questions> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item_mental_ezterab_test, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Mental_multiple_choice_questions data_service = data_services_list.get(position);



        SpannableStringBuilder str = new SpannableStringBuilder(data_service.getTitle());
        str.setSpan(new android.text.style.StyleSpan(Typeface.NORMAL), 0, str.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.card_title.setText(str);
        holder.mrg.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
               data_service.setResult(holder.mrg.getCheckedRadioButtonIndex());
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
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}