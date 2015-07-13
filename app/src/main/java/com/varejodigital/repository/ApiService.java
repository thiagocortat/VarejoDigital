package com.varejodigital.repository;

import com.varejodigital.model.ApiChannels;
import com.varejodigital.model.ApiFaturamento;
import com.varejodigital.model.ApiProduto;
import com.varejodigital.model.ApiProdutos;
import com.varejodigital.model.ApiUpdateProduto;
import com.varejodigital.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by thiagocortat on 5/24/15.
 */
public interface ApiService {

    @GET("/perfis.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void login(Callback<User> callback);

    @GET("/produtos.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainProdutos(Callback<ApiProdutos> callback);

    @GET("/auditoria/produtos.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainProdutosToAuditate(Callback<ApiProdutos> callback);

    @GET("/{id}/produto.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainProduto(@Path("id") int groupId, Callback<ApiProduto> callback);

    @GET("/produto/{codBarra}.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainProdutoByCode(@Path("codBarra") String barcode, Callback<ApiProduto> callback);

    @POST("/produto/update.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void updateProduto(@Body ApiUpdateProduto apiUpdateProduto, Callback<HashMap> callback);


    @GET("/financeiro/faturamento.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainFaturamento(Callback<ApiFaturamento> callback);

    //Sample: financeiro/2015-01-01/2015-02-01/faturamento.json
    @GET("/financeiro/{dt-begin}/{dt-end}/faturamento.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainFaturamentoByDate(@Path("dt-begin") String dtBegin, @Path("dt-end") String dtEnd, Callback<ApiFaturamento> callback);

    @GET("/canais-comunicacao.json")
    @Headers({ "Content-type: application/json; charset=UTF-8" })
    void obtainCanais(Callback<ApiChannels> callback);

}
