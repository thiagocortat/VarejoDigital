package com.varejodigital.repository;

import com.varejodigital.model.ApiProduto;
import com.varejodigital.model.ApiProdutos;
import retrofit.Callback;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by thiagocortat on 5/24/15.
 */
public interface ApiService {

    @GET("/produtos.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainProdutos(Callback<ApiProdutos> callback);

    @GET("/{id}/produto.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainProduto(@Path("id") int groupId, Callback<ApiProduto> callback);

    @GET("/financeiro/faturamento.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainFaturamento(Callback<String> callback);


//    @GET("/estados.json")
//    @Headers({ "Content-type: application/json; charset=UTF-8" })
//    void obtainEstados(@Header("Authorization") String authorization, Callback<String> callback);
}
