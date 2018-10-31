package com.demo.simplecook.viewmodel;

import android.app.Application;

import com.demo.simplecook.SimpleCookApp;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.repository.RecipeRepository;
import com.demo.simplecook.repository.RecipeResultWrapper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ExploreViewModel extends AndroidViewModel {
    private RecipeRepository mRecipeRepository;

    public ExploreViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = SimpleCookApp.getRecipeRepository();
        // TODO - Get local data
        // TODO - Get remote data
    }

    public LiveData<RecipeResultWrapper> getRemoteRecipes(String query, String time, String diet) {
        return mRecipeRepository.getRemoteRecipes(query, time, diet);
    }

}