package com.varejodigital.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.varejodigital.R;
import com.varejodigital.activities.LineChartActivity;
import com.varejodigital.fragments.base.BaseFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class BillingFragment extends BaseFragment  implements OnChartValueSelectedListener {

    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"
    };

    protected LineChart mChart;

    public static BillingFragment newInstance() {
        BillingFragment f = new BillingFragment();
        return (f);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_billing, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mChart = (LineChart) view.findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

//        mChart.setDrawBarShadow(false);
//        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");
        // if more than 60 entries are displayed in the chart, no values will be drawn
        mChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.setTouchEnabled(false);
        mChart.setScaleEnabled(false);

        Legend l = mChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        ValueFormatter custom = new MyValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

//        Legend l = mChart.getLegend();
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);

        setData(12, 50);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_billing, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_date) {
            startActivity(new Intent(getActivity(), LineChartActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(mMonths[i % 12]);
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(val, i));
        }

        LineDataSet set1 = new LineDataSet(yVals1, "");

//        BarDataSet set1 = new BarDataSet(yVals1, "");
//        set1.setBarSpacePercent(35f);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1);

        LineData data = new LineData(xVals, dataSets);
        data.setValueTextSize(10f);

        mChart.setData(data);
    }

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;

//        RectF bounds = mChart.getBarBounds((BarEntry) e);
        PointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

//        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());
    }

    public void onNothingSelected() {
    };


    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value) + " $";
        }

    }




    private class ChartDataAdapter extends ArrayAdapter<LineData> {

        public ChartDataAdapter(Context context, List<LineData> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LineData data = getItem(position);
            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_linechart, null);
                holder.chart = (LineChart) convertView.findViewById(R.id.chart);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // apply styling
            // holder.chart.setValueTypeface(mTf);
            holder.chart.setDescription("");
            holder.chart.setDrawGridBackground(false);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(true);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setLabelCount(5);

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setLabelCount(5);
            rightAxis.setDrawGridLines(false);

            // set data
            holder.chart.setData(data);

            // do not forget to refresh the chart
            // holder.chart.invalidate();
            holder.chart.animateX(750);

            return convertView;
        }

        private class ViewHolder {
            LineChart chart;
        }
    }
}
