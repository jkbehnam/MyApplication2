package com.example.behnam.myapplication.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.behnam.myapplication.R;


public class adapet_drug_list extends ArrayAdapter<String> {
    private final Activity context;

    private final String[] web;

    public adapet_drug_list(Activity context, String[] web) {
        super(context, R.layout.exercise_item_drug, web);
        this.context = context;
        this.web = web;

    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = this.context.getLayoutInflater().inflate(R.layout.exercise_item_drug, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.menu_txt);
        txtTitle.setText(this.web[position]);

        return rowView;
    }
}
