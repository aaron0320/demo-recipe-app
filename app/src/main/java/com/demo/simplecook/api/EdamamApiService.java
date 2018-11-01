package com.demo.simplecook.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EdamamApiService {
    @GET("/search")
    Call<EdamamSearchResponse> getRecipes(
            @Query("app_id") String appId,
            @Query("app_key") String appKey,
            @Query("q") String query,
            @Query("time") String time,
            @Query("diet") String diet,
            @Query("from") int nextPageStartIndex
    );
}