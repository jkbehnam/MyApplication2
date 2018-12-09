package com.example.behnam.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.my_custom_component.MyWebViewClient;
import com.example.behnam.myapplication.objects.DailyData_confirm_list;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.zagum.expandicon.ExpandIconView;

import java.util.List;



public class RecyclerViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolder> {
int[] img_state=new int[]{R.drawable.exercise_state_green,R.drawable.exercise_state_yellow,R.drawable.exercise_state_red,R.drawable.exercise_state_orange};
    private final List<DailyData_confirm_list> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    public RecyclerViewRecyclerAdapter(final List<DailyData_confirm_list> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.exer_item, parent, false));
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DailyData_confirm_list item = data.get(position);
        holder.setIsRecyclable(false);
        holder.textView.setText(item.getTitle());

        String summary = "";

        summary = "<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\" />\n" +"<html>\n" +
                "<head><title>Title of the document</title></head>\n" +
                "<body>\n" +
                "<div class=\"c2\">\n" + item.getContent_message()+
                "</div>\n" +
                "</body>\n" +
                "</html>";
        holder.webView1.loadDataWithBaseURL("file:///android_asset/", summary, "text/html; charset=utf-8", null, null);
        holder.webView1.setWebViewClient(new MyWebViewClient(context) );

        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setExpanded(false);
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {

            @Override
            public void onPreOpen() {

                holder.eiv1.switchState();
                expandState.put(position, true);
                super.onPreOpen();
            }

            @Override
            public void onPreClose() {

                holder.eiv1.switchState();
                expandState.put(position, false);
                super.onPreClose();
            }

            @Override
            public void onOpened() {
               // holder.expandableLayout.initLayout();
              //  holder.expandableLayout.expand(0, new LinearInterpolator());
                super.onOpened();
            }
        });

        holder.iv_state.setImageResource(img_state[item.getImg()]);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                onClickButton(holder.expandableLayout);
            }
        });


    }

    private void onClickButton(final ExpandableLinearLayout expandableLayout) {
        if(!expandableLayout.isExpanded()){
            expandableLayout.initLayout();
            expandableLayout.expand(0,new LinearInterpolator());
        }else{
        expandableLayout.collapse();}
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout buttonLayout;
        public CardView cv;
        public ExpandIconView eiv1;
        public WebView webView1;
        ImageView iv_state;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            iv_state=(ImageView)v.findViewById(R.id.iv_state);
            textView = (TextView) v.findViewById(R.id._title);
            buttonLayout = (RelativeLayout) v.findViewById(R.id.button);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.expandableLayout);
            cv = (CardView) v.findViewById(R.id.warm_ups_cardview);
            eiv1 = (ExpandIconView) v.findViewById(R.id.eiv1);

            webView1 = (WebView) v.findViewById(R.id.webView1);


        }
    }


}