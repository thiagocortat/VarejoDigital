package com.varejodigital.model;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by thiagocortat on 4/12/15.
 */
public class ChartBarModel {

    private String title;
    private BarData barData;

    public ChartBarModel(String title, BarData barData) {
        this.title = title;
        this.barData = barData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BarData getBarData() {
        return barData;
    }

    public void setBarData(BarData barData) {
        this.barData = barData;
    }
}
