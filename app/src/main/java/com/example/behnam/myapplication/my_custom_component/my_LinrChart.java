package com.example.behnam.myapplication.my_custom_component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class my_LinrChart extends LineChart {
    public my_LinrChart(Context context) {
        super(context);
    }

    public my_LinrChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public my_LinrChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void set_prefrence(Context co) {

        Typeface type1 = Typeface.createFromAsset(co.getAssets(), "font/iran_sans.ttf");
        // scaling can now only be done on x- and y-axis separately
        this.setDrawMarkers(true);

        this.setPinchZoom(true);
        this.setClickable(true);
        this.setDrawGridBackground(false);
        XAxis xAxis = this.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(45);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "\n" + "%";
            }
        });
        this.getAxisLeft().setDrawGridLines(false);
        this.setMaxVisibleValueCount(5);
        this.getXAxis().setGranularity(1f);
        this.animateY(3000);
        this.animateX(3000);
        this.setVisibleXRangeMaximum(10);
       // this.getAxisLeft().setLabelCount(2, true);
        //this.getAxisRight().setLabelCount(2, true);
        this.getAxisLeft().setTypeface(type1);
        this.getAxisRight().setTypeface(type1);
        this.getXAxis().setTypeface(type1);
        this.moveViewToX(this.getXChartMax());
        this.invalidate();
        this.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        YAxis y = this.getAxisLeft();
        y.setDrawGridLines(false);
        y.setDrawTopYLabelEntry(false);
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
    }

    public void chart_data(ArrayList<String> labels, ArrayList<Entry> yVals1) {

        //------------------------------------------------
        this.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        LineDataSet set1;
        if (this.getData() != null && this.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) this.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            set1.setFormLineWidth(1);
            set1.setLineWidth(1);
            set1.setHighlightLineWidth(15);
            this.getData().notifyDataChanged();
            this.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "");
            set1.setColors(ColorTemplate.JOYFUL_COLORS);
            set1.setFormLineWidth(10);

            set1.setLineWidth(6);
            set1.setMode(LineDataSet.Mode.LINEAR);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            this.setData(data);
        }

    }
}
