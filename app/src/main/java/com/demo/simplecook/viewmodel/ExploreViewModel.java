package com.demo.simplecook.viewmodel;

import android.app.Application;
import android.content.Context;

import com.demo.simplecook.R;
import com.demo.simplecook.SimpleCookApp;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.repository.RecipeRepository;
import com.demo.simplecook.repository.RecipeResultWrapper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import timber.log.Timber;

public class ExploreViewModel extends AndroidViewModel {
    private Context mAppContext;
    private RecipeRepository mRecipeRepository;

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> isError = new MutableLiveData<>();
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    private MediatorLiveData<List<Recipe>> mRemoteRecipesLiveData = new MediatorLiveData<>();

    private LiveData<RecipeResultWrapper> mRemoteRecipesSource;

    public ExploreViewModel(@NonNull Application application,
                            @NonNull RecipeRepository recipeRepository,
                            @NonNull String query,
                            @NonNull String time,
                            @NonNull String diet) {
        super(application);
        mAppContext = application.getApplicationContext();
        mRecipeRepository = recipeRepository;

        refreshRemoteRecipes(query, time, diet);
    }

    public void refreshRemoteRecipes(String query, String time, String diet) {
        // Set Loading and Error to correct values
        isLoading.setValue(true);
        isError.setValue(false);

        // Remove old source to avoid memory leak
        if (mRemoteRecipesSource != null) {
            mRemoteRecipesLiveData.removeSource(mRemoteRecipesSource);
        }

        // Set source from repository
        mRemoteRecipesSource = mRecipeRepository.getRemoteRecipes(query, time, diet);
        // Listen to source and update live data
        mRemoteRecipesLiveData.addSource(mRemoteRecipesSource, (recipeResultWrapper) -> {
            if (recipeResultWrapper.isSucess() && recipeResultWrapper.getRecipes().size() > 0) {
                Timber.e("data sucess");
                isLoading.setValue(false);
                mRemoteRecipesLiveData.setValue(recipeResultWrapper.getRecipes());
            } else if (recipeResultWrapper.isSucess() && recipeResultWrapper.getRecipes().size() == 0) {
                Timber.e("data empty");
                isLoading.setValue(false);
                isError.setValue(true);
                errorMsg.setValue(mAppContext.getString(R.string.load_recipe_no_result));
            } else {
                Timber.e("data failed");
                isLoading.setValue(false);
                isError.setValue(true);
                if (recipeResultWrapper.getCode() == 401) { // API Limit exceeded
                    errorMsg.setValue(mAppContext.getString(R.string.load_recipe_error_exceed_limit));
                } else {
                    errorMsg.setValue(mAppContext.getString(R.string.load_recipe_error_general));
                }
            }
        });
    }

    public LiveData<List<Recipe>> getRemoteRecipes() {
        return mRemoteRecipesLiveData;
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;

        private final String mQuery;
        private final String mTime;
        private final String mDiet;

        private final RecipeRepository mRepository;

        public Factory(@NonNull Application application,
                       String query, String time, String diet) {
            mApplication = application;
            mQuery = query;
            mTime = time;
            mDiet = diet;
            mRepository = SimpleCookApp.getRecipeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ExploreViewModel(mApplication, mRepository, mQuery, mTime, mDiet);
        }
    }

}