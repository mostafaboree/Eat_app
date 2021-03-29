package com.mostafabor3e.eat_app.Remot;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrioftClient {
    public static final String UPDATE ="update";
    public static final String DELETE ="delete";
    //public static final String baseUrl="https://fcm.googleapis.com";

    private static Retrofit retrofit=null;
    public static   Retrofit getClient(String baseUrl){
        if (retrofit == null){
            retrofit=new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
