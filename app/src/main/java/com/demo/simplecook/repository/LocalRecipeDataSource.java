package com.demo.simplecook.repository;

import com.demo.simplecook.model.Recipe;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;

public class LocalRecipeDataSource {
    private static LocalRecipeDataSource sInstance;

    private LocalRecipeDataSource() {
        // TODO - Add dependency of AppDatabase object
    }

    public static LocalRecipeDataSource getInstance() {
        if (sInstance == null) {
            synchronized (LocalRecipeDataSource.class) {
                if (sInstance == null) {
                    sInstance = new LocalRecipeDataSource();
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Recipe>> getRecipes() {
        // TODO - Get Recipes from Database
        return null;
    }

    public Observable<Boolean> saveRecipe(Recipe recipe) {
        // TODO - Save Recipe to Database
        return null;
    }
}