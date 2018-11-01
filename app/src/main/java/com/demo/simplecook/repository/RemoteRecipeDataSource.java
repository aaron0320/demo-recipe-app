package com.demo.simplecook.repository;

import com.demo.simplecook.api.EdamamApiService;
import com.demo.simplecook.api.EdamamSearchResponse;
import com.demo.simplecook.config.Config;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import retrofit2.Response;
import timber.log.Timber;

public class RemoteRecipeDataSource {
    private static RemoteRecipeDataSource sInstance;

    private EdamamApiService mEdamamApiService;

    private RemoteRecipeDataSource(EdamamApiService edamamApiService) {
        this.mEdamamApiService = edamamApiService;
    }

    public static RemoteRecipeDataSource getInstance(EdamamApiService edamamApiService) {
        if (sInstance == null) {
            synchronized (RemoteRecipeDataSource.class) {
                if (sInstance == null) {
                    sInstance = new RemoteRecipeDataSource(edamamApiService);
                }
            }
        }
        return sInstance;
    }

    public Observable<Response<EdamamSearchResponse>> getRecipes(String query, String time, String diet, int nextPageStartIndex) {
        return Observable.fromCallable(() ->
                mEdamamApiService.getRecipes(
                        Config.edamamAppId,
                        Config.edamamAppKey,
                        query,
                        time,
                        diet,
                        nextPageStartIndex)
                        .execute());
    }
}
