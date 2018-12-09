package com.example.behnam.myapplication.my_custom_component;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.example.behnam.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

/**
 * Created by User on 1/23/2018.
 */

public class my_BarChart_multiple extends BarChart {
    static Context co;

    public my_BarChart_multiple(Context context) {
        super(context);
        co = context;

    }

    public my_BarChart_multiple(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public my_BarChart_multiple(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void set_prefrence(Context co) {

        Typeface type1 = Typeface.createFromAsset(co.getAssets(), "font/iran_sans.ttf");
        this.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        this.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        this.setPinchZoom(true);
        this.setDrawBarShadow(false);

        XAxis xAxis = this.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setLabelRotationAngle(45);
        this.getAxisLeft().setDrawGridLines(true);
        this.setClickable(true);

        this.setMaxVisibleValueCount(60);
       // this.getXAxis().setGranularity(1f);
        this.setExtraOffsets(10, 10, 10, 10);
        this.animateY(5000);
        this.setDrawMarkers(true);
        this.setHighlightPerTapEnabled(true);
        //this.getBarData().setBarWidth(0.9f);
        this.setTouchEnabled(true);

        LimitLine ll1 = new LimitLine(4f, "");
          ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setTypeface(type1);
        YAxis leftAxis = this.getAxisLeft();
        leftAxis.removeAllLimitLines();
      //  leftAxis.addLimitLine(ll1);
        this.getAxisLeft().setTypeface(type1);
        this.getAxisRight().setTypeface(type1);
        this.setVisibleXRangeMinimum(0f);
        this.getXAxis().setTypeface(type1);
        this.setVisibleXRangeMaximum(7);
        this.moveViewToX(this.getXChartMax());


        this.getDescription().setText("");
        this.setFitBars(true);

        YAxis y = this.getAxisLeft();
        y.setValueFormatter(new YValueFormatter());
        y.setDrawGridLines(false);
       // y.setDrawTopYLabelEntry(false);
        //   y.setTextColor(CHART_TEXT_COLOR);
        y.setDrawZeroLine(true);
        y.setSpaceBottom(0);
        y.setAxisMinimum(0);
        //   y.setValueFormatter(getYAxisFormatter());
        y.setEnabled(true);

        YAxis yAxisRight = this.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setEnabled(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawTopYLabelEntry(false);

this.getDescription().setEnabled(true);
        this.getLegend().setEnabled(true);


        this.getLegend().setWordWrapEnabled(true);

        //   yAxisRight.setTextColor(CHART_TEXT_COLOR);
        this.invalidate();
    }

    public void chart_data(ArrayList<String> labels, ArrayList<BarEntry> yVals1,ArrayList<BarEntry> yVals2,Context co,ArrayList<Integer> color) {

        this.setDrawBarShadow(false);
        this.setDrawValueAboveBar(true);
        this.setMaxVisibleValueCount(50);
        this.setPinchZoom(false);
        this.setDrawGridBackground(false);

        XAxis xl = this.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);

        YAxis leftAxis = this.getAxisLeft();

        leftAxis.setGranularity(1f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true
        this.getAxisRight().setEnabled(false);

        //data
        float groupSpace = 0.04f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.46f; // x2 dataset
        // (0.46 + 0.02) * 2 + 0.04 = 1.00 -> interval per "group"

        int startYear = 0;
        int endYear = yVals1.size();



        BarDataSet set1, set2;

        if (this.getData() != null && this.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)this.getData().getDataSetByIndex(0);
            set2 = (BarDataSet)this.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            this.getData().notifyDataChanged();
            this.notifyDataSetChanged();
        } else {
            // create 2 datasets with different types
            set1 = new BarDataSet(yVals1, "فشار خون حداکثر");
            set1.setColor(ContextCompat.getColor(co, R.color.primary_dark));
            set2 = new BarDataSet(yVals2, "فشار خون حداقل");
            set2.setColor(ContextCompat.getColor(co, R.color.accent));
            Typeface type1 = Typeface.createFromAsset(co.getAssets(), "font/iran_sans.ttf");
            set1.setValueTypeface(type1);
            set2.setValueTypeface(type1);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);

            BarData data = new BarData(dataSets);
            this.setData(data);
        }

        this.getXAxis().setDrawLabels(true);


        this.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        this.getBarData().setBarWidth(barWidth);
        this.getXAxis().setAxisMinValue(startYear);
        this.getXAxis().setAxisMaximum(endYear);
        this.groupBars(startYear, groupSpace, barSpace);

        this.setFitBars(true);

        this.invalidate();
          //  this.setFitBars(true);


            //data.setValueTypeface(mTfLight);

        }

    private  class YValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            float[] yAxis = axis.mEntries;
            String result = (int) value + "";
            if (value == yAxis[yAxis.length - 1]) {
                result = "مقادیر \nفشار خون";
            }
            return result;
        }
    }

}
