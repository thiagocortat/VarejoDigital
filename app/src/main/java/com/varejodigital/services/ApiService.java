package com.varejodigital.services;

import com.varejodigital.model.Funcionario;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by thiagocortat on 1/14/15.
 */
public interface ApiService {

    @GET("/timelines/popular")
    public Funcionario[] getPopular();

    @GET("/timelines/popular")
    public void getPopular(Callback<Funcionario[]> callback);
}
