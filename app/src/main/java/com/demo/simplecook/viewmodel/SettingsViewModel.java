package com.demo.simplecook.viewmodel;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.demo.simplecook.SimpleCookApp;
import com.demo.simplecook.helper.ImageHelper;
import com.demo.simplecook.model.Recipe;
import com.demo.simplecook.repository.RecipeRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingsViewModel extends AndroidViewModel {
    private RecipeRepository mRecipeRepository;
    private ImageHelper mImageHelper;

    private Application mApplication;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mRecipeRepository = ((SimpleCookApp) application).getRecipeRepository();
        mImageHelper = ((SimpleCookApp) application).getImageHelper();
    }

    public LiveData<Boolean> dropAllLocalRecipes() {
        return mRecipeRepository.dropAllLocalRecipes();
    }

    public LiveData<Boolean> clearDiskCache() {
        return mImageHelper.removeGlideDiskCache(mApplication.getApplicationContext());
    }
}