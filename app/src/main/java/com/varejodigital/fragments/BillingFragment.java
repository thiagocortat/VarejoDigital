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
import android.widget.ListView;
import android.widget.TextView;

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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.varejodigital.R;
import com.varejodigital.activities.LineChartActivity;
import com.varejodigital.fragments.base.BaseFragment;
import com.varejodigital.model.ChartLineModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class BillingFragment extends BaseFragment {

//    protected String[] mMonths = new String[] {
//            "Jan", "Feb", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"
//    };

    ListView lv;
    ValueFormatter customFormat;

//    protected LineChart mChart;

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

        lv = (ListView) view.findViewById(R.id.listView1);

        customFormat = new MyValueFormatter();

        ArrayList<ChartLineModel> list = new ArrayList<ChartLineModel>();

        // 30 items
        for (int i = 0; i < 4; i++) {

            if(i == 0) {
                list.add(new ChartLineModel("Ticket Médio Agora",  generateDataLine(i)));
            } else if(i == 1) {
                list.add(new ChartLineModel("Ticket Médio na Semana", generateDataLine(i)));
            } else if(i == 2) {
                list.add(new ChartLineModel("Ticket Médio no Mês", generateDataLine(i)));
            } else if(i == 3) {
                list.add(new ChartLineModel("Ticket Médio no Ano", generateDataLine(i)));
            }
        }


        ChartDataAdapter cda = new ChartDataAdapter(getActivity(), list);
        lv.setAdapter(cda);

//        mChart = (LineChart) view.findViewById(R.id.chart1);
//        mChart.setOnChartValueSelectedListener(this);
//
//        mChart.setDescription("");
//        // if more than 60 entries are displayed in the chart, no values will be drawn
//        mChart.setMaxVisibleValueCount(60);
//        // scaling can now only be done on x- and y-axis separately
//        mChart.setPinchZoom(false);
//        mChart.setDrawGridBackground(false);
//        mChart.setTouchEnabled(false);
//        mChart.setScaleEnabled(false);
//
//        Legend l = mChart.getLegend();
//        l.setEnabled(false);
//
//        mChart.getAxisRight().setEnabled(false);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setSpaceBetweenLabels(2);
//
//        ValueFormatter custom = new MyValueFormatter();
//
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setLabelCount(8);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//
//        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setLabelCount(8);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//
//        setData(12, 50);

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

//    private void setData(int count, float range) {
//
//        ArrayList<String> xVals = new ArrayList<String>();
//        for (int i = 0; i < count; i++) {
//            xVals.add(mMonths[i % 12]);
//        }
//
//        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//
//        for (int i = 0; i < count; i++) {
//            float mult = (range + 1);
//            float val = (float) (Math.random() * mult);
//            yVals1.add(new BarEntry(val, i));
//        }
//
//        LineDataSet set1 = new LineDataSet(yVals1, "");
//
//        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
//        dataSets.add(set1);
//
//        LineData data = new LineData(xVals, dataSets);
//        data.setValueTextSize(10f);
//
//        mChart.setData(data);
//    }

//    @SuppressLint("NewApi")
//    @Override
//    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//
//        if (e == null)
//            return;
//
////        RectF bounds = mChart.getBarBounds((BarEntry) e);
//        PointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);
//
////        Log.i("bounds", bounds.toString());
//        Log.i("position", position.toString());
//    }
//
//    public void onNothingSelected() {
//    };


    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

//        public MyValueFormatter() {
//            mFormat = new DecimalFormat("###,###,###,##0.00");
//        }
        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,###,##0");
        }

        @Override
        public String getFormattedValue(float value) {
            return  "R$ " + mFormat.format(value);
        }

    }




    private class ChartDataAdapter extends ArrayAdapter<ChartLineModel> {

        public ChartDataAdapter(Context context, List<ChartLineModel> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ChartLineModel data = getItem(position);
            ViewHolder holder;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_linechart, null);
                holder.chart = (LineChart) convertView.findViewById(R.id.chart);
                holder.tx = (TextView) convertView.findViewById(R.id.txTitle);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tx.setText(data.getTitle());

            // apply styling
            holder.chart.setDescription("");
            holder.chart.setDrawGridBackground(false);
            holder.chart.setPinchZoom(false);
            holder.chart.setTouchEnabled(false);
            holder.chart.setScaleEnabled(false);

            Legend l = holder.chart.getLegend();
            l.setEnabled(false);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(true);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setLabelCount(5);
            leftAxis.setValueFormatter(customFormat);

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setLabelCount(5);
            rightAxis.setDrawGridLines(false);
            rightAxis.setValueFormatter(customFormat);

            // set data
            holder.chart.setData(data.getLineData());

            // do not forget to refresh the chart
            // holder.chart.invalidate();
            holder.chart.animateX(1000);

            return convertView;
        }

        private class ViewHolder {
            LineChart chart;
            TextView tx;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }

    private LineData generateDataLine(int index) {

        ArrayList<String> labels;
        if(index == 0) {
            labels = getNow();
        } else if(index == 1) {
            labels = getWeek();
        } else if(index == 2) {
            labels = getMonth();
        } else {
            labels = getMonths();
        }

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < labels.size(); i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 10, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "");
        d1.setLineWidth(3.0f);
        d1.setHighLightColor(ColorTemplate.COLORFUL_COLORS[index]);
//        d1.setCircleSize(4.5f);
//        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setColor(ColorTemplate.COLORFUL_COLORS[index]);
        d1.setCircleColor(ColorTemplate.COLORFUL_COLORS[index]);
        d1.setDrawValues(false);

        LineData cd = new LineData(labels, d1);
        return cd;
    }


    private ArrayList<String> getNow() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("10:00");
        m.add("11:00");
        m.add("12:00");
        m.add("13:00");
        m.add("14:00");
        m.add("15:00");
        m.add("16:00");
        m.add("17:00");
        return m;
    }

    private ArrayList<String> getWeek() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("SEG");
        m.add("TER");
        m.add("QUA");
        m.add("QUI");
        m.add("SEX");
        m.add("SAB");
        m.add("DOM");
        return m;
    }

    private ArrayList<String> getMonth() {

        ArrayList<String> m = new ArrayList<String>();
        for (int i = 1; i <=30; i++){
            m.add("" + i);
        }

        return m;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Abr");
        m.add("Mai");
        m.add("Jun");
        m.add("Jul");
        m.add("Ago");
        m.add("Set");
        m.add("Out");
        m.add("Nov");
        m.add("Dez");
        return m;
    }
}
