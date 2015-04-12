package com.varejodigital.model;

import com.github.mikephil.charting.data.LineData;

/**
 * Created by thiagocortat on 4/12/15.
 */
public class ChartLineModel {

    private String title;
    private LineData lineData;

    public ChartLineModel(String title, LineData lineData) {
        this.title = title;
        this.lineData = lineData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LineData getLineData() {
        return lineData;
    }

    public void setLineData(LineData lineData) {
        this.lineData = lineData;
    }
}
