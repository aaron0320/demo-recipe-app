package com.demo.simplecook.viewmodel;

import android.app.Application;
import android.content.Context;

import com.demo.simplecook.R;
import com.demo.simplecook.SimpleCookApp;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.repository.RecipeRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SavedViewModel extends AndroidViewModel {
    private Context mAppContext;
    private RecipeRepository mRecipeRepository;

    public MutableLiveData<Boolean> isGetLocalRecipesLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> isGetLocalRecipesError = new MutableLiveData<>();
    public MutableLiveData<String> getLocalRecipesErrorMsg = new MutableLiveData<>();

    private MediatorLiveData<List<Recipe>> mLocalRecipes = new MediatorLiveData<>();

    public SavedViewModel(@NonNull Application application,
                          @NonNull RecipeRepository recipeRepository) {
        super(application);
        mAppContext = application.getApplicationContext();
        mRecipeRepository = recipeRepository;

        isGetLocalRecipesLoading.setValue(true);
        mLocalRecipes.addSource(mRecipeRepository.getLocalRecipes(), (recipes) -> {
            isGetLocalRecipesLoading.setValue(false);
            if (recipes != null && recipes.size() > 0) {
                isGetLocalRecipesError.setValue(false);
                mLocalRecipes.postValue(recipes);
            } else {
                getLocalRecipesErrorMsg.setValue( mAppContext.getString(R.string.load_local_recipe_no_result));
                isGetLocalRecipesError.setValue(true);
            }
        });
    }

    public LiveData<List<Recipe>> getLocalRecipes() {
        return mLocalRecipes;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;

        private final RecipeRepository mRepository;

        public Factory(@NonNull Application application) {
            mApplication = application;
            mRepository = ((SimpleCookApp) application).getRecipeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SavedViewModel(mApplication, mRepository);
        }
    }

}