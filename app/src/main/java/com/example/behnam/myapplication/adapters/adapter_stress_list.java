package com.example.behnam.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.objects.Stress_list;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.zagum.expandicon.ExpandIconView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_stress_list extends RecyclerView.Adapter<adapter_stress_list.MyViewHolder> {
    private List<Stress_list> data_services_list;
    Context co;
    static int i = 0;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content;
        public ImageView sign;
        public ExpandableLinearLayout expandableLayout;
        public ExpandIconView eiv1;
        public CardView cv;
        RecyclerView rv;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id._title);
          //  content = (TextView) view.findViewById(R.id._content);
            expandableLayout = (ExpandableLinearLayout) view.findViewById(R.id._expandableLayout);
            eiv1 = (ExpandIconView) view.findViewById(R.id.eiv1);
            cv = (CardView) view.findViewById(R.id.warm_ups_cardview);
            rv = (RecyclerView) view.findViewById(R.id.contetnt_rv);
        }
    }


    public adapter_stress_list(ArrayList<Stress_list> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item_stress_outer, parent, false);
        co = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Stress_list data_service = data_services_list.get(position);

holder.setIsRecyclable(false);
        holder.title.setText(data_service.getTitle());
        holder.content.setText(data_service.getContent_message());
        holder.expandableLayout.setInRecyclerView(true);

        holder.expandableLayout.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
                holder.expandableLayout.initLayout();
            }

            @Override
            public void onPreClose() {
                holder.expandableLayout.initLayout();
            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });
        holder.expandableLayout.setInterpolator(new LinearInterpolator());
        //
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.expandableLayout.toggle();
                holder.eiv1.switchState();

            }
        });
        holder.setIsRecyclable(false);
      /*  if (data_service.getS_list() != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(co);
            holder.rv.setLayoutManager(layoutManager);
            adapter_stress_list madapter = new adapter_stress_list(data_service.getS_list());
            SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
            alphaAdapter.setFirstOnly(true);
            holder.rv.setAdapter(alphaAdapter);

        } */

    }


    @Override
    public int getItemViewType(int position) {
        return position;
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