package com.varejodigital.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thiagocortat on 6/14/15.
 */
public class ApiChannels implements Serializable {

    @SerializedName("list")
    private List<String> list;

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }
}
