package com.demo.simplecook.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EdamamRetrofitClient {

    private static Retrofit retrofit;
    private static final String EDAMAM_BASE_URL = "https://api.edamam.com/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            synchronized (EdamamRetrofitClient.class) {
                if (retrofit == null) {
                    retrofit = new retrofit2.Retrofit.Builder()
                            .baseUrl(EDAMAM_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}