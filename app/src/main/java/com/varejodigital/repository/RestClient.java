package com.varejodigital.repository;

import android.util.Base64;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by thiagocortat on 5/24/15.
 */
public class RestClient {

    private ApiService apiService;

    private final static String apiBaseUrl = "http://www.allinshopp.com.br/varejodigital";

    public RestClient() {
        init();
    }

    private void init() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter.Builder builder= new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(apiBaseUrl);
//                .setClient(new OkClient(new OkHttpClient()));

            // concatenate username and password with colon for authentication
            final String credentials = "marcelosrodrigues@globo.com" + ":" + "12345678";
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {

                       String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                    String string;
//                    try {
//                        string = Base64.encodeToString(credentials.getBytes("UTF-8"), Base64.NO_WRAP);
//                    } catch (UnsupportedEncodingException e) {
//                        // Weird, no UTF-8 encoding found?
//                        string = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                    }
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Content-Type", "application/json; charset=UTF-8");
//                    request.addHeader("Authorization", string);
                    request.addHeader("Authorization", string);//"Basic bWFyY2Vsb3Nyb2RyaWd1ZXNAZ2xvYm8uY29tOjJwazAjM3R5Pw==");
                }
            });


        RestAdapter restAdapter = builder.build();

        apiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService()
    {
        return apiService;
    }

}
