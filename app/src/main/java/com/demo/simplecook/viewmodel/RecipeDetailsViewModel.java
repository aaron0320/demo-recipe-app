package com.demo.simplecook.viewmodel;

import android.app.Application;

import com.demo.simplecook.SimpleCookApp;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.repository.RecipeRepository;
import com.demo.simplecook.repository.RecipeResultWrapper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecipeDetailsViewModel extends AndroidViewModel {
    private RecipeRepository mRecipeRepository;

    public RecipeDetailsViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = SimpleCookApp.getRecipeRepository();
    }

    public LiveData<Boolean> saveLocalRecipe(Recipe recipe) {
        // TODO - No implementation of local DB yet
        return mRecipeRepository.saveLocalRecipe(recipe);
    }

}