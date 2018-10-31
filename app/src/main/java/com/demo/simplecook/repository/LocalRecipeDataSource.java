package com.demo.simplecook.repository;

import com.demo.simplecook.model.Recipe;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;

public class LocalRecipeDataSource {
    private static LocalRecipeDataSource sInstance;

    private LocalRecipeDataSource() {
    }

    public static LocalRecipeDataSource getInstance() {
        if (sInstance == null) {
            synchronized (RemoteRecipeDataSource.class) {
                if (sInstance == null) {
                    sInstance = new LocalRecipeDataSource();
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Recipe>> getRecipes() {
        // TODO
        return null;
    }

    public Observable<Boolean> saveRecipe(Recipe recipe) {
        // TODO
        return null;
    }
}