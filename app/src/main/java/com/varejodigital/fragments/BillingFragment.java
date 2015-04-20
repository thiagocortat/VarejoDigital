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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.varejodigital.model.ChartBarModel;
import com.varejodigital.model.ChartLineModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;


public class BillingFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {


    ListView lv;
    ValueFormatter customFormat;
    SegmentedGroup segmented;
    ChartDataLineAdapter chartLineAdapter;
    ChartBarDataAdapter chartBarAdapter;

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

        // 4 items
        ArrayList<ChartLineModel> listLine = new ArrayList<ChartLineModel>();
        ArrayList<ChartBarModel> listBar = new ArrayList<ChartBarModel>();
        for (int i = 0; i < 4; i++) {

            if(i == 0) {
                listLine.add(new ChartLineModel("Ticket Médio Agora - "     + getRandomValue() ,  generateDataLine(i)));
                listBar.add(new ChartBarModel("Valor Acumulado Agora - "    + getRandomValueAccumulate(i), generateDataBar(i)));
            } else if(i == 1) {
                listLine.add(new ChartLineModel("Ticket Médio na Semana - "     + getRandomValue(), generateDataLine(i)));
                listBar.add(new ChartBarModel("Valor Acumulado na Semana - "    + getRandomValueAccumulate(i), generateDataBar(i)));
            } else if(i == 2) {
                listLine.add(new ChartLineModel("Ticket Médio no Mês - "    + getRandomValue(), generateDataLine(i)));
                listBar.add(new ChartBarModel("Valor Acumulado no Mês - "   + getRandomValueAccumulate(i), generateDataBar(i)));
            } else if(i == 3) {
                listLine.add(new ChartLineModel("Ticket Médio no Ano - "    + getRandomValue(), generateDataLine(i)));
                listBar.add(new ChartBarModel("Valor Acumulado no Ano - "   + getRandomValueAccumulate(i), generateDataBar(i)));
            }
        }

        chartLineAdapter = new ChartDataLineAdapter(getActivity(), listLine);
        chartBarAdapter = new ChartBarDataAdapter(getActivity(), listBar);
        lv.setAdapter(chartLineAdapter);

        segmented = (SegmentedGroup) view.findViewById(R.id.segmented);
        segmented.setOnCheckedChangeListener(this);

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.button21:
//                Toast.makeText(getActivity(), "One", Toast.LENGTH_SHORT).show();
                lv.setAdapter(chartLineAdapter);
                return;
            case R.id.button22:
//                Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                lv.setAdapter(chartBarAdapter);
                return;
            default:
                // Nothing to do
        }
    }

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

    private String getRandomValue(){

        DecimalFormat mFormat = new DecimalFormat("###,###,###,##0.00");
        return  "R$ " + mFormat.format((Math.random() * 65) + 10);
    }

    private String getRandomValueAccumulate(int index){

        DecimalFormat mFormat = new DecimalFormat("#########,##0");
        if(index == 0) {
            return  "R$ " + mFormat.format((Math.random() * 5000) + 5000);
        } else if(index == 1) {
            return  "R$ " + mFormat.format((Math.random() * 20000) + 20000);
        } else if(index == 2) {
            return  "R$ " + mFormat.format((Math.random() * 100000) + 100000);
        } else {
            return  "R$ " + mFormat.format((Math.random() * 1000000) + 1000000);
        }

    }


    private class ChartDataLineAdapter extends ArrayAdapter<ChartLineModel> {

        public ChartDataLineAdapter(Context context, List<ChartLineModel> objects) {
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

    private class ChartBarDataAdapter extends ArrayAdapter<ChartBarModel> {

        public ChartBarDataAdapter(Context context, List<ChartBarModel> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ChartBarModel data = getItem(position);
            ViewHolder holder;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_barchart, null);
                holder.chart = (BarChart) convertView.findViewById(R.id.chart);
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
            leftAxis.setEnabled(false);

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setLabelCount(5);
            rightAxis.setDrawGridLines(false);
            rightAxis.setValueFormatter(customFormat);

            // set data
            holder.chart.setData(data.getBarData());

            // do not forget to refresh the chart
            // holder.chart.invalidate();
            holder.chart.animateX(1000);

            return convertView;
        }

        private class ViewHolder {
            BarChart chart;
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
        d1.setColor(ColorTemplate.COLORFUL_COLORS[index]);
        d1.setCircleColor(ColorTemplate.COLORFUL_COLORS[index]);
        d1.setDrawValues(false);

        LineData cd = new LineData(labels, d1);
        return cd;
    }

    private BarData generateDataBar(int index){
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

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        for (int i = 0; i < labels.size(); i++) {
            if(index == 0) {
                entries.add(new BarEntry((int)(Math.random() * 5000) + 5000, i));
            } else if(index == 1) {
                entries.add(new BarEntry((int)(Math.random() * 20000) + 20000, i));
            } else if(index == 2) {
                entries.add(new BarEntry((int)(Math.random() * 100000) + 100000, i));
            } else {
                entries.add(new BarEntry((int)(Math.random() * 1000000) + 1000000, i));
            }
        }

        BarDataSet d = new BarDataSet(entries, "");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        return new BarData(labels, d);
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
