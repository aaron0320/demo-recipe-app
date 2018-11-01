package com.demo.simplecook.repository;

import com.demo.simplecook.db.AppDataBase;
import com.demo.simplecook.model.Recipe;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;

public class LocalRecipeDataSource {
    private static LocalRecipeDataSource sInstance;

    private AppDataBase mAppDataBase;

    private LocalRecipeDataSource(AppDataBase appDataBase) {
        this.mAppDataBase = appDataBase;
    }

    public static LocalRecipeDataSource getInstance(AppDataBase appDataBase) {
        if (sInstance == null) {
            synchronized (LocalRecipeDataSource.class) {
                if (sInstance == null) {
                    sInstance = new LocalRecipeDataSource(appDataBase);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mAppDataBase.recipeDao().getAllRecipes();
    }

    public LiveData<Recipe> getRecipe(String webUrl) {
        return mAppDataBase.recipeDao().getRecipe(webUrl);
    }

    public Observable<Boolean> saveRecipe(Recipe recipe) {
        return Observable.fromCallable(() -> {
            mAppDataBase.recipeDao().saveRecipe(recipe);
            return true;
        });
    }

    public Observable<Boolean> deleteRecipe(Recipe recipe) {
        return Observable.fromCallable(() -> {
            mAppDataBase.recipeDao().deleteRecipe(recipe);
            return true;
        });
    }

    public Observable<Boolean> dropAllRecipes() {
        return Observable.fromCallable(() -> {
            mAppDataBase.recipeDao().dropAllRecipes();
            return true;
        });
    }
}