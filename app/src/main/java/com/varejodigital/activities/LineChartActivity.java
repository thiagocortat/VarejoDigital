
package com.varejodigital.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.varejodigital.R;

import java.util.ArrayList;
import java.util.Calendar;

public class LineChartActivity extends BaseActivity implements OnChartValueSelectedListener, DatePickerDialog.OnDateSetListener{

    private String textDate;
    private LineChart mChart;
    private Button btDate;
//    private SeekBar mSeekBarX, mSeekBarY;
//    private TextView tvX, tvY;

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String DATEPICKER_2_TAG = "datepicker2";
//    public static final String TIMEPICKER_TAG = "timepicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.product_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("Você precisa informar um período para o gráfico.");

//        // enable value highlighting
//        mChart.setHighlightEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);

//        // enable scaling and dragging
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);


//        LimitLine ll1 = new LimitLine(130f, "Upper Limit");
//        ll1.setLineWidth(4f);
//        ll1.enableDashedLine(10f, 10f, 0f);
//        ll1.setLabelPosition(LimitLabelPosition.POS_RIGHT);
//        ll1.setTextSize(10f);
//
//        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//        ll2.setLineWidth(4f);
//        ll2.enableDashedLine(10f, 10f, 0f);
//        ll2.setLabelPosition(LimitLabelPosition.POS_RIGHT);
//        ll2.setTextSize(10f);

//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
//        leftAxis.setAxisMaxValue(220f);
//        leftAxis.setAxisMinValue(-50f);
//        leftAxis.setStartAtZero(false);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//
//        // limit lines are drawn behind data (and not on top)
//        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);




        
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
//        l.setForm(LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mChart.invalidate();


        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

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

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.line, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.actionToggleValues: {
//                for (DataSet<?> set : mChart.getData().getDataSets())
//                    set.setDrawValues(!set.isDrawValuesEnabled());
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleHighlight: {
//                if (mChart.isHighlightEnabled())
//                    mChart.setHighlightEnabled(false);
//                else
//                    mChart.setHighlightEnabled(true);
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleFilled: {
//
//                ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData()
//                        .getDataSets();
//
//                for (LineDataSet set : sets) {
//                    if (set.isDrawFilledEnabled())
//                        set.setDrawFilled(false);
//                    else
//                        set.setDrawFilled(true);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleCircles: {
//                ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData()
//                        .getDataSets();
//
//                for (LineDataSet set : sets) {
//                    if (set.isDrawCirclesEnabled())
//                        set.setDrawCircles(false);
//                    else
//                        set.setDrawCircles(true);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleCubic: {
//                ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData()
//                        .getDataSets();
//
//                for (LineDataSet set : sets) {
//                    if (set.isDrawCubicEnabled())
//                        set.setDrawCubic(false);
//                    else
//                        set.setDrawCubic(true);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleStartzero: {
//                mChart.getAxisLeft().setStartAtZero(!mChart.getAxisLeft().isStartAtZeroEnabled());
//                mChart.getAxisRight().setStartAtZero(!mChart.getAxisRight().isStartAtZeroEnabled());
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionTogglePinch: {
//                if (mChart.isPinchZoomEnabled())
//                    mChart.setPinchZoom(false);
//                else
//                    mChart.setPinchZoom(true);
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.animateX: {
//                mChart.animateX(3000);
//                break;
//            }
//            case R.id.animateY: {
//                mChart.animateY(3000, Easing.EasingOption.EaseInCubic);
//                break;
//            }
//            case R.id.animateXY: {
//                mChart.animateXY(3000, 3000);
//                break;
//            }
//            case R.id.actionToggleAdjustXLegend: {
//                XAxis xLabels = mChart.getXAxis();
//
//                if (xLabels.isAdjustXLabelsEnabled())
//                    xLabels.setAdjustXLabels(false);
//                else
//                    xLabels.setAdjustXLabels(true);
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleFilter: {
//
//                // the angle of filtering is 35°
//                Approximator a = new Approximator(ApproximatorType.DOUGLAS_PEUCKER, 35);
//
//                if (!mChart.isFilteringEnabled()) {
//                    mChart.enableFiltering(a);
//                } else {
//                    mChart.disableFiltering();
//                }
//                mChart.invalidate();
//
//                //
//                // for(int i = 0; i < 10; i++) {
//                // mChart.addEntry(new Entry((float) (Math.random() * 100),
//                // i+2), 0);
//                // mChart.invalidate();
//                // }
//                //
//                // Toast.makeText(getApplicationContext(), "valcount: " +
//                // mChart.getDataOriginal().getYValCount() + ", valsum: " +
//                // mChart.getDataOriginal().getYValueSum(),
//                // Toast.LENGTH_SHORT).show();
//                //
//                break;
//            }
//            case R.id.actionSave: {
//                if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
//                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
//                            Toast.LENGTH_SHORT).show();
//                } else
//                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
//                            .show();
//
//                // mChart.saveToGallery("title"+System.currentTimeMillis())
//                break;
//            }
//        }
//        return true;
//    }



    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
                                                           // ((mult *
                                                           // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
    }


    
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        //Toast.makeText(this, "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
        textDate = "De " + day + "/" + month + "/" + year;

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog2 =
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
//                        Toast.makeText(LineChartActivity.this, "new date2:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
                        textDate = textDate + " à " + day + "/" + month + "/" + year;
                        btDate.setText(textDate);


//                        mChart.invalidate();
                        // add data
                        setData(45, 100);
                        mChart.animateX(2500);
                    }
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog2.setYearRange(1985, 2028);
        datePickerDialog2.setCloseOnSingleTapDay(true);
        datePickerDialog2.show(getSupportFragmentManager(), DATEPICKER_2_TAG);

    }


}
