package com.example.behnam.myapplication.adapters;

import android.animation.ObjectAnimator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.objects.DailyData_confirm_list;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.github.zagum.expandicon.ExpandIconView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_daily_data_config extends RecyclerView.Adapter<adapter_daily_data_config.MyViewHolder> {
    private List<DailyData_confirm_list> data_services_list;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content;
        public ImageView sign;
        public ExpandableLinearLayout expandableLayout;
        public ExpandIconView eiv1;
        public CardView cv;


        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id._title);
            content = (TextView) view.findViewById(R.id._content_1);
            expandableLayout = (ExpandableLinearLayout) view.findViewById(R.id._expandableLayout);
            eiv1 = (ExpandIconView) view.findViewById(R.id.eiv1);
            cv = (CardView) view.findViewById(R.id.warm_ups_cardview);

        }
    }


    public adapter_daily_data_config(ArrayList<DailyData_confirm_list> data_services_list) {
        this.data_services_list = data_services_list;
        for (int i = 0; i < data_services_list.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item_expand_layout_add_daily_data, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DailyData_confirm_list data_service = data_services_list.get(position);

        holder.setIsRecyclable(false);
        holder.title.setText(data_service.getTitle());
        holder.content.setText(data_service.getContent_message());
        holder.expandableLayout.setInRecyclerView(true);

        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setInterpolator(new LinearInterpolator());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandableLayout.initLayout();
                holder.expandableLayout.toggle();
                holder.eiv1.switchState();
            }
        });
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.content.setVisibility(View.GONE);
            }
        });
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.cv, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.cv, 180f, 0f).start();
                expandState.put(position, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_services_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}