package com.example.behnam.myapplication.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.objects.Sign;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.List;


public class adapter_signs_list extends RecyclerView.Adapter<adapter_signs_list.ViewHolder> {

    private final List<Sign> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public adapter_signs_list(final List<Sign> data) {
        this.data = data;

        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.exercise_item_signs, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Sign item = data.get(position);


        holder.setIsRecyclable(false);
        holder.title.setText(item.getTitle());
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setInterpolator(new LinearInterpolator());
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.title_chbox.setChecked(item.getTitle_chbox());
        holder.title_chbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setTitle_chbox(true);
                    holder.expandableLayout.expand();
                    item.setDuration((double)36);
                } else {
                    holder.expandableLayout.collapse();
                    holder.time_seekbar.setProgress(0);
                    holder.what_did_et.setText("");
                    item.setTitle_chbox(false);
                }
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.title_chbox.isChecked()) {
                    holder.title_chbox.setChecked(true);

                } else {
                    holder.title_chbox.setChecked(false);

                }
            }
        });
        holder.in_exercise_chbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setIn_exercise(1);
                } else {
                    item.setIn_exercise(0);
                }
            }
        });
        final int[] line = {1};
        holder.what_did_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                line[0] = holder.what_did_et.getLineCount();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (line[0] < holder.what_did_et.getLineCount()) {
                    ++line[0];
                    holder.expandableLayout.initLayout();
                    holder.expandableLayout.expand(0, new LinearInterpolator());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setReaction(s.toString());
                Log.d("after",s.toString());
            }
        });

        String[] s = context.getResources().getStringArray(R.array.seekbar_array);
        holder.time_seekbar.customTickTexts(s);
        holder.time_seekbar.setIndicatorTextFormat("${TICK_TEXT} ");
        holder.time_seekbar.setFocusableInTouchMode(true);
        holder.time_seekbar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                item.setDuration((double) seekBar.getProgressFloat());
            }
        });
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

                expandState.put(position, true);


            }

            @Override
            public void onPreClose() {
                //  holder.expandableLayout.initLayout();

                expandState.put(position, false);

            }

            @Override
            public void onOpened() {
                // holder.expandableLayout.initLayout();

            }

            @Override
            public void onClosed() {
                // holder.expandableLayout.initLayout();

            }
        });

        holder.setIsRecyclable(false);
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CheckBox title_chbox, in_exercise_chbox;
        IndicatorSeekBar time_seekbar;
        EditText what_did_et;

        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_title);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.expandableLayout);
            title_chbox = (CheckBox) v.findViewById(R.id.checkBox_title);
            in_exercise_chbox = (CheckBox) v.findViewById(R.id.in_exercise_checkBox);
            // time_seekbar = (BubbleSeekBar) v.findViewById(R.id.time_seekBar);
            what_did_et = (EditText) v.findViewById(R.id.what_did_et);
            time_seekbar = (IndicatorSeekBar) v.findViewById(R.id.sign_seekbar);

        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}