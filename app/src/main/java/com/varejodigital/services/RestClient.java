package com.varejodigital.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by thiagocortat on 1/14/15.
 */
public class RestClient
{
    private static final String BASE_URL = "https://api.vineapp.com";
    private static ApiService apiService;
    private static RestAdapter restAdapter;


    private static  RestAdapter getRestAdapter(){
        if(restAdapter==null){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();

            restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setConverter(new GsonConverter(gson))
                    .build();
        }
        return restAdapter;
    }

    public static ApiService getApiService()
    {
        if (apiService == null)
            apiService = getRestAdapter().create(ApiService.class);

        return apiService;
    }
}