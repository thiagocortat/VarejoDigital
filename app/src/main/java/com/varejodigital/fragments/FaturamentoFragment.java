package com.varejodigital.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.varejodigital.R;
import com.varejodigital.activities.BillingByDateActivity;
import com.varejodigital.fragments.base.BaseFragment;
import com.varejodigital.model.ApiFaturamento;
import com.varejodigital.model.ChartBarModel;
import com.varejodigital.model.ChartLineModel;
import com.varejodigital.repository.RestClient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;



public class FaturamentoFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {


    ListView lv;
    ValueFormatter customFormat;
    SegmentedGroup segmented;
    ChartDataLineAdapter chartLineAdapter;
    ChartBarDataAdapter chartBarAdapter;

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String DATEPICKER_2_TAG = "datepicker2";

    int selectedSegmentIndex = 0;

    public static FaturamentoFragment newInstance() {
        return (new FaturamentoFragment());
    }

    public static FaturamentoFragment newInstance(String dateOne, String dateTwo) {
        FaturamentoFragment f = new FaturamentoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATEPICKER_TAG, dateOne);
        bundle.putString(DATEPICKER_2_TAG, dateTwo);
        f.setArguments(bundle);
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

        segmented = (SegmentedGroup) view.findViewById(R.id.segmented);
        segmented.setOnCheckedChangeListener(this);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(DATEPICKER_TAG) && bundle.containsKey(DATEPICKER_2_TAG) ) {
            connectService(bundle.getString(DATEPICKER_TAG),
                    bundle.getString(DATEPICKER_2_TAG));
        }
        else {
            connectService();
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_billing, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_date) {
            startActivity(new Intent(getActivity(), BillingByDateActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.button21:
                selectedSegmentIndex = 0;
                lv.setAdapter(chartLineAdapter);
                return;
            case R.id.button22:
                selectedSegmentIndex = 1;
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

/*    private String getRandomValue(){

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

    }*/


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

    public void connectService() {

        showProgress();
        RestClient restClient = new RestClient();
        restClient.getApiService().obtainFaturamento(connection);
    }

    public void connectService(String dateOne, String datetwo) {

        showProgress();
        RestClient restClient = new RestClient();
        restClient.getApiService().obtainFaturamentoByDate(dateOne, datetwo, connection);
    }


    public void pupulateData(ApiFaturamento apiFaturamento) {

        //        // 4 items
        ArrayList<ChartLineModel> listLine = new ArrayList<>();
        ArrayList<ChartBarModel> listBar = new ArrayList<>();

        ApiFaturamento.Faturamento fat = apiFaturamento.getFaturamento().get(0);
        List<ApiFaturamento.Faturamento.PorMes.PorSemana> porSemanas = new ArrayList<>();

        for (ApiFaturamento.Faturamento.PorMes porMes : fat.getPorMes()) {
            porSemanas.addAll(porMes.getPorSemana());
        }

        for (int i = 0; i < 3; i++) {

//            ApiFaturamento.Faturamento fat = apiFaturamento.getFaturamento().get(0);
            if(i == 0) {
                listLine.add(new ChartLineModel("Ticket Médio  - $ "     + fat.getMedio(),  getDaysLine(fat.getPorDia(), 0)));
                listBar.add(new ChartBarModel("Valor Acumulado - $"    + fat.getAcumulado(), getDaysBar(fat.getPorDia(), 1) ));
            }
            else if(i == 1) {
                listLine.add(new ChartLineModel("Ticket Médio nas últimas semanas", getWeeksLine(porSemanas, 0)));
                listBar.add(new ChartBarModel("Valor Acumulado nas últimas semanas ", getSemanasBar(porSemanas, 1)));
            }
            else if(i == 2) {
                listLine.add(new ChartLineModel("Ticket Médio nos Meses", getMonthsLine(fat.getPorMes(), 0) ));
                listBar.add(new ChartBarModel("Valor Acumulado nos Meses ",getMonthsBar(fat.getPorMes(), 1) ));
            }
// else if(i == 3) {
//                listLine.add(new ChartLineModel("Ticket Médio no Ano - "    + getRandomValue(), generateDataLine(i)));
//                listBar.add(new ChartBarModel("Valor Acumulado no Ano - "   + getRandomValueAccumulate(i), generateDataBar(i)));
//            }
        }

        chartLineAdapter = new ChartDataLineAdapter(getActivity(), listLine);
        chartBarAdapter = new ChartBarDataAdapter(getActivity(), listBar);
        lv.setAdapter(chartLineAdapter);

    }

    private LineData getDaysLine(List<ApiFaturamento.Faturamento.PorDia> porDiaList, int segmentIndex){

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Entry> e1 = new ArrayList<>();

        for (int i = 0; i < porDiaList.size(); i++) {
            ApiFaturamento.Faturamento.PorDia porDia = porDiaList.get(i);
            labels.add(porDia.getDia());

            if (segmentIndex == 0)
                e1.add(new Entry(Float.parseFloat(porDia.getMedio()), i));
            else
                e1.add(new Entry(Float.parseFloat(porDia.getAcumulado()), i));
        }

        return new LineData(labels, getLineDataSet(e1, "", 0));
    }

    private LineData getWeeksLine(List<ApiFaturamento.Faturamento.PorMes.PorSemana> porSemanaList, int segmentIndex){

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Entry> e1 = new ArrayList<>();

        for (int i = 0; i < porSemanaList.size(); i++) {
            ApiFaturamento.Faturamento.PorMes.PorSemana porSemana = porSemanaList.get(i);
            labels.add("" + porSemana.getSemana());

            if (segmentIndex == 0)
                e1.add(new Entry(Float.parseFloat(porSemana.getMedio()), i));
            else
                e1.add(new Entry(Float.parseFloat(porSemana.getAcumulado()), i));
        }

        return new LineData(labels, getLineDataSet(e1, "", 0));
    }

    private LineData getMonthsLine(List<ApiFaturamento.Faturamento.PorMes> porMesList, int segmentIndex){

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Entry> e1 = new ArrayList<>();

        for (int i = 0; i < porMesList.size(); i++) {
            ApiFaturamento.Faturamento.PorMes porMes = porMesList.get(i);
            labels.add("Mês " + porMes.getMes());

            if (segmentIndex == 0)
                e1.add(new Entry(Float.parseFloat(porMes.getMedio()), i));
            else
                e1.add(new Entry(Float.parseFloat(porMes.getAcumulado()), i));
        }

        return new LineData(labels, getLineDataSet(e1, "", 0));
    }

    private BarData getDaysBar(List<ApiFaturamento.Faturamento.PorDia> porDiaList, int segmentIndex){

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < porDiaList.size(); i++) {
            ApiFaturamento.Faturamento.PorDia porDia = porDiaList.get(i);
            labels.add(porDia.getDia());

            if (segmentIndex == 0)
                entries.add(new BarEntry(Float.parseFloat(porDia.getMedio()), i));
            else {
                entries.add(new BarEntry(Float.parseFloat(porDia.getAcumulado()), i));
            }
        }

        return new BarData(labels, getBarDataSet(entries, ""));
    }

    private BarData getSemanasBar(List<ApiFaturamento.Faturamento.PorMes.PorSemana> porSemanaList, int segmentIndex){

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < porSemanaList.size(); i++) {
            ApiFaturamento.Faturamento.PorMes.PorSemana porSemana = porSemanaList.get(i);
            labels.add("" + porSemana.getSemana());

            if (segmentIndex == 0)
                entries.add(new BarEntry(Float.parseFloat(porSemana.getMedio()), i));
            else {
                entries.add(new BarEntry(Float.parseFloat(porSemana.getAcumulado()), i));
            }
        }

        return new BarData(labels, getBarDataSet(entries, ""));
    }

    private BarData getMonthsBar(List<ApiFaturamento.Faturamento.PorMes> porMesList, int segmentIndex){

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < porMesList.size(); i++) {
            ApiFaturamento.Faturamento.PorMes porMes = porMesList.get(i);
            labels.add("Mês " + porMes.getMes());

            if (segmentIndex == 0)
                entries.add(new BarEntry(Float.parseFloat(porMes.getMedio()), i));
            else {
                entries.add(new BarEntry(Float.parseFloat(porMes.getAcumulado()), i));
            }
        }

        return new BarData(labels, getBarDataSet(entries, ""));
    }


    private LineDataSet getLineDataSet(List<Entry> e1, String label, int indexColor) {

        LineDataSet d1 = new LineDataSet(e1, "");
        d1.setLineWidth(3.0f);
        d1.setHighLightColor(ColorTemplate.COLORFUL_COLORS[indexColor]);
        d1.setColor(ColorTemplate.COLORFUL_COLORS[indexColor]);
        d1.setCircleColor(ColorTemplate.COLORFUL_COLORS[indexColor]);
        d1.setDrawValues(false);

        return d1;
    }

    private BarDataSet getBarDataSet(List<BarEntry> entries, String label) {
        BarDataSet d = new BarDataSet(entries, "");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);
        return d;
    }


    private Callback<ApiFaturamento> connection = new Callback<ApiFaturamento>() {
        @Override
        public void success(ApiFaturamento apiFaturamento, Response response) {

            try {
                pupulateData(apiFaturamento);
                hideProgressAfterSecond();
            }catch (Exception e) {
                failure(null);
            }
        }

        @Override
        public void failure(RetrofitError error) {
//            Toast.makeText(getActivity(), "Erro " + error.getMessage(), Toast.LENGTH_LONG).show();
            setContentEmpty(true);
            setEmptyText("Erro de Conexão, por favor tente novamente!");
            hideProgressAfterSecond();

        }
    };
}
