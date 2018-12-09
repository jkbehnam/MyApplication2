package com.example.behnam.myapplication.my_custom_component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
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

public class my_BarChart_exercise extends BarChart {
    static Context co;

    public my_BarChart_exercise(Context context) {
        super(context);
        co = context;

    }

    public my_BarChart_exercise(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public my_BarChart_exercise(Context context, AttributeSet attrs, int defStyle) {
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
        this.getXAxis().setGranularity(1f);

        this.animateY(5000);
        this.setDrawMarkers(true);
        this.setHighlightPerTapEnabled(true);
        this.getBarData().setBarWidth(0.9f);
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
        this.invalidate();

        this.getDescription().setText("");
        this.setFitBars(true);

        YAxis y = this.getAxisLeft();
        y.setDrawGridLines(false);
        y.setDrawTopYLabelEntry(false);
        //   y.setTextColor(CHART_TEXT_COLOR);
        y.setDrawZeroLine(true);
        y.setSpaceBottom(0);
        y.setAxisMinimum(0);
        //   y.setValueFormatter(getYAxisFormatter());
        y.setEnabled(true);
        y.setValueFormatter(new YValueFormatter());
        YAxis yAxisRight = this.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setEnabled(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawTopYLabelEntry(false);

this.getDescription().setEnabled(false);
        this.getLegend().setEnabled(false);
        //   yAxisRight.setTextColor(CHART_TEXT_COLOR);

    }

    public void chart_data(ArrayList<String> labels, ArrayList<BarEntry> yVals1,Context co,ArrayList<Integer> color) {

        this.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        // this.getXAxis().setTypeface(Typeface.createFromAsset(co.getAssets(), "font/iran_sans.ttf"));
        BarDataSet set1;
        if (this.getData() != null &&
                this.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) this.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);

            this.getData().notifyDataChanged();
            this.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "میزان استفاده");
            set1.setDrawIcons(false);
            //above number
            set1.setDrawValues(true);


            set1.setColors( color);
            set1.setHighlightEnabled(true);
            // allow highlighting for DataSet
           Typeface type1 = Typeface.createFromAsset(co.getAssets(), "font/iran_sans.ttf");
            set1.setValueTypeface(type1);
            // set this to false to disable the drawing of highlight indicator (lines)
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            this.setData(data);
        }
    }
    private  class YValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            float[] yAxis = axis.mEntries;
            String result = (int) value + "";

            if(value==0){
                return "خیلی آسان";
            }
            if(value==1){
                return "نسبتا آسان";
            }
            if(value==2){
                return "نسبتا سخت";
            }
            if(value==3){
                return "خیلی سخت";
            }
            return result;
        }
    }

}
