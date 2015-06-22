package com.varejodigital.activities;

import android.app.Fragment;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.varejodigital.R;
import com.varejodigital.fragments.FaturamentoFragment;
import com.varejodigital.model.ApiFaturamento;
import com.varejodigital.repository.RestClient;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BillingByDateActivity extends BaseActivity implements OnChartValueSelectedListener, DatePickerDialog.OnDateSetListener{


    private String textDate;
//    private LineChart mChart;
    private Button btDate;

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String DATEPICKER_2_TAG = "datepicker2";
//    public static final String TIMEPICKER_TAG = "timepicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_by_date);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.product_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        mChart = (LineChart) findViewById(R.id.chart1);
//        mChart.setOnChartValueSelectedListener(this);
//        // no description text
//        mChart.setDescription("");
//        mChart.setNoDataTextDescription("Você precisa informar um período para o gráfico.");
//        // enable touch gestures
//        mChart.setTouchEnabled(true);
//        mChart.setPinchZoom(true);
//        mChart.getAxisRight().setEnabled(false);
//        Legend l = mChart.getLegend();
//        l.setEnabled(false);


        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        btDate = (Button) findViewById(R.id.dateButton);
        btDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.setCloseOnSingleTapDay(true);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }
        }

//        connectService();
    }



//    private void setData(int count, float range) {
//
//        ArrayList<String> xVals = new ArrayList<String>();
//        for (int i = 0; i < count; i++) {
//            xVals.add((i) + "");
//        }
//
//        ArrayList<Entry> yVals = new ArrayList<Entry>();
//
//        for (int i = 0; i < count; i++) {
//
//            float mult = (range + 1);
//            float val = (float) (Math.random() * mult) + 3;// + (float)
//            // ((mult *
//            // 0.1) / 10);
//            yVals.add(new Entry(val, i));
//        }
//
//        // create a dataset and give it a type
//        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
//
//        // set the line to be drawn like this "- - - - - -"
//        set1.enableDashedLine(10f, 5f, 0f);
//        set1.setColor(Color.BLACK);
//        set1.setCircleColor(Color.BLACK);
//        set1.setLineWidth(1f);
//        set1.setCircleSize(3f);
//        set1.setDrawCircleHole(false);
//        set1.setValueTextSize(9f);
//        set1.setFillAlpha(65);
//        set1.setFillColor(Color.BLACK);
//
//        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
//        dataSets.add(set1); // add the datasets
//
//        // create a data object with the datasets
//        LineData data = new LineData(xVals, dataSets);
//
//        // set data
//        mChart.setData(data);
//    }



    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
//        Log.i("", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        textDate = "De " + day + "/" + month + "/" + year;

        final String dateOne =  year + "-" + month + "-" + day;

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog2 = DatePickerDialog.newInstance(
            new DatePickerDialog.OnDateSetListener() {
                 @Override
                 public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

                     textDate = textDate + " à " + day + "/" + month + "/" + year;
                     btDate.setText(textDate);

                     String dateTwo = year + "-" + month + "-" + day;

                     Fragment f = FaturamentoFragment.newInstance(dateOne, dateTwo);
                     getFragmentManager().beginTransaction().replace(R.id.frameGraph, f).commit();


                     // add data
//                   setData(45, 100);
//                   mChart.animateX(2500);
                 }
             },
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog2.setYearRange(1985, 2028);
        datePickerDialog2.setCloseOnSingleTapDay(true);
        datePickerDialog2.show(getSupportFragmentManager(), DATEPICKER_2_TAG);

    }
//
//    public void connectService(String dateOne, String dateTwo) {
//
//        showProgress(R.id.frameGraph);
//
//        RestClient restClient = new RestClient();
////        restClient.getApiService().obtainFaturamentoByDate("2015-01-01", "2015-02-01", new Callback<ApiFaturamento>() {
//        restClient.getApiService().obtainFaturamentoByDate(dateOne, dateTwo, new Callback<ApiFaturamento>() {
//            @Override
//            public void success(ApiFaturamento apiFaturamento, Response response) {
//                Toast.makeText(getBaseContext(), "Foi", Toast.LENGTH_LONG).show();
//
//                Fragment f = FaturamentoFragment.newInstance();
//                getFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
//
//                hideProgress();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Toast.makeText(getBaseContext(), "Erro " + error.getMessage(), Toast.LENGTH_LONG).show();
//                hideProgress();
//            }
//        });
//    }
}
