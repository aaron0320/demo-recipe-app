package com.demo.simplecook.viewmodel;

import android.app.Application;

import com.demo.simplecook.SimpleCookApp;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.repository.RecipeRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecipeDetailsViewModel extends AndroidViewModel {
    private RecipeRepository mRecipeRepository;

    public RecipeDetailsViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = ((SimpleCookApp) application).getRecipeRepository();
    }

    public LiveData<Recipe> getLocalRecipe(String webUrl) {
        return mRecipeRepository.getLocalRecipe(webUrl);
    }

    public LiveData<Boolean> saveLocalRecipe(Recipe recipe) {
        return mRecipeRepository.saveLocalRecipe(recipe);
    }

    public LiveData<Boolean> deleteLocalRecipe(Recipe recipe) {
        return mRecipeRepository.deleteLocalRecipe(recipe);
    }
}