package com.example.behnam.myapplication.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.objects.Stress_list;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.github.zagum.expandicon.ExpandIconView;

import java.util.List;



public class stress_list extends RecyclerView.Adapter<stress_list.ViewHolder> {
    ExpandableLinearLayout parent_layer;
    private final List<Stress_list> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public stress_list(final List<Stress_list> data, ExpandableLinearLayout parent_layer) {
        this.data = data;
        this.parent_layer = parent_layer;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.exercise_item_stress_outer, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Stress_list item = data.get(position);

        holder.textView.setText(item.getTitle());
        holder.setIsRecyclable(false);
        holder.expandableLayout.setInRecyclerView(true);
         holder.expandableLayout.setInterpolator(new LinearInterpolator());
         holder.expandableLayout.setExpanded(expandState.get(position));
        holder.content.setText(item.getContent_message());
        if (item.getS_list() != null) {
            holder.rv.removeAllViewsInLayout();
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            holder.rv.setLayoutManager(layoutManager);
            stress_list madapter = new stress_list(item.getS_list(), holder.expandableLayout);
            holder.rv.setAdapter(madapter);

        }

        holder.expandableLayout.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
               // holder.expandableLayout.initLayout();
                holder.eiv1.switchState();
                expandState.put(position, true);


            }

            @Override
            public void onPreClose() {
              //  holder.expandableLayout.initLayout();
                holder.eiv1.switchState();
                expandState.put(position, false);

            }

            @Override
            public void onOpened() {
                // holder.expandableLayout.initLayout();
                if (parent_layer != null) {
                    parent_layer.initLayout();

                    parent_layer.setExpanded(true);

                }
            }

            @Override
            public void onClosed() {
                // holder.expandableLayout.initLayout();
                if (parent_layer != null) {
                    parent_layer.initLayout();

                     parent_layer.setExpanded(true);

                }

            }
        });


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);

            }
        });


    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        com.codesgood.views.JustifiedTextView content;
        public RelativeLayout buttonLayout;
        public CardView cv;
        public ExpandIconView eiv1;
        RecyclerView rv;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.what_is_ezterab_title1);
            buttonLayout = (RelativeLayout) v.findViewById(R.id.button);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.expandableLayout);
            cv = (CardView) v.findViewById(R.id.what_is_ezterab_cardview1);
            eiv1 = (ExpandIconView) v.findViewById(R.id.eiv1);
            rv = (RecyclerView) v.findViewById(R.id.contetnt_rv);
            content=(com.codesgood.views.JustifiedTextView)v.findViewById(R.id._content);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}