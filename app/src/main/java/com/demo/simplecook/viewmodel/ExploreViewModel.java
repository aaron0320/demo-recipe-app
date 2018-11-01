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
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import timber.log.Timber;

public class ExploreViewModel extends AndroidViewModel {
    private Context mAppContext;
    private RecipeRepository mRecipeRepository;

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> isError = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoadingMore = new MutableLiveData<>();
    public MutableLiveData<Boolean> isErrorLoadingMore = new MutableLiveData<>();
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    private MediatorLiveData<List<Recipe>> mRemoteRecipesLiveData = new MediatorLiveData<>();

    private LiveData<RecipeResultWrapper> mRemoteRecipesSource;

    private int nextPageStartIndex = 0;

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
        nextPageStartIndex = 0;
        isLoading.setValue(true);
        isError.setValue(false);
        isLoadingMore.setValue(false);
        isErrorLoadingMore.setValue(false);

        // Remove old source to avoid memory leak
        if (mRemoteRecipesSource != null) {
            mRemoteRecipesLiveData.removeSource(mRemoteRecipesSource);
        }

        // Set source from repository
        mRemoteRecipesSource = mRecipeRepository.getRemoteRecipes(query, time, diet, nextPageStartIndex);
        // Listen to source and update live data
        mRemoteRecipesLiveData.addSource(mRemoteRecipesSource, (recipeResultWrapper) -> {
            if (recipeResultWrapper.isSucess() && recipeResultWrapper.getRecipes().size() > 0) {
                isLoading.setValue(false);
                mRemoteRecipesLiveData.setValue(recipeResultWrapper.getRecipes());
                nextPageStartIndex = recipeResultWrapper.getLastIndex();
            } else if (recipeResultWrapper.isSucess() && recipeResultWrapper.getRecipes().size() == 0) {
                errorMsg.setValue(mAppContext.getString(R.string.load_recipe_no_result));
                isLoading.setValue(false);
                isError.setValue(true);
            } else {
                if (recipeResultWrapper.getCode() == 401) { // API Limit exceeded
                    errorMsg.setValue(mAppContext.getString(R.string.load_recipe_error_exceed_limit));
                } else {
                    errorMsg.setValue(mAppContext.getString(R.string.load_recipe_error_general));
                }
                isLoading.setValue(false);
                isError.setValue(true);
            }
        });
    }

    public void getNextPageRemoteRecipes(String query, String time, String diet) {
        // If Loading is in progress, disable getting next page
        // If Error is showing, disable getting next page, unless user click retry button
        if (isLoading.getValue() != null && isLoading.getValue() ||
                isLoadingMore.getValue() != null && isLoadingMore.getValue() ||
                isError.getValue() != null && isError.getValue() ||
                isErrorLoadingMore.getValue() != null && isErrorLoadingMore.getValue()) {
            return;
        }

        // Set Loading and Error to correct values
        isLoadingMore.setValue(true);
        isErrorLoadingMore.setValue(false);

        // Remove old source to avoid memory leak
        if (mRemoteRecipesSource != null) {
            mRemoteRecipesLiveData.removeSource(mRemoteRecipesSource);
        }

        // Set source from repository
        mRemoteRecipesSource = mRecipeRepository.getRemoteRecipes(query, time, diet, nextPageStartIndex);
        // Listen to source and update live data
        mRemoteRecipesLiveData.addSource(mRemoteRecipesSource, (recipeResultWrapper) -> {
            if (recipeResultWrapper.isSucess() && recipeResultWrapper.getRecipes().size() > 0) {
                isLoadingMore.setValue(false);

                // If original LiveData is not null, add new data
                if (mRemoteRecipesLiveData.getValue() != null) {
                    recipeResultWrapper.getRecipes().addAll(0, mRemoteRecipesLiveData.getValue());
                }
                mRemoteRecipesLiveData.setValue(recipeResultWrapper.getRecipes());

                // Update next page start index
                nextPageStartIndex = recipeResultWrapper.getLastIndex();
            } else if (recipeResultWrapper.isSucess() && recipeResultWrapper.getRecipes().size() == 0) {
                errorMsg.setValue(mAppContext.getString(R.string.load_recipe_no_result));
                isLoadingMore.setValue(false);
                isErrorLoadingMore.setValue(true);
            } else {
                if (recipeResultWrapper.getCode() == 401) { // API Limit exceeded
                    errorMsg.setValue(mAppContext.getString(R.string.load_recipe_error_exceed_limit));
                } else {
                    errorMsg.setValue(mAppContext.getString(R.string.load_recipe_error_general));
                }
                isLoadingMore.setValue(false);
                isErrorLoadingMore.setValue(true);
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