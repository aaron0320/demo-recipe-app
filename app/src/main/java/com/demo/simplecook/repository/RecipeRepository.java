package com.demo.simplecook.repository;

import com.demo.simplecook.api.EdamamSearchResponse;
import com.demo.simplecook.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;

import static com.demo.simplecook.repository.RecipeResultWrapper.INVALID_CODE;

public class RecipeRepository {
    private static RecipeRepository sInstance;

    private LocalRecipeDataSource mLocalRecipeDataSource;
    private RemoteRecipeDataSource mRemoteRecipeDataSource;


    private RecipeRepository(@NonNull LocalRecipeDataSource localRecipeDataSource,
                             @NonNull RemoteRecipeDataSource remoteRecipeDataSource) {
        this.mLocalRecipeDataSource = localRecipeDataSource;
        this.mRemoteRecipeDataSource = remoteRecipeDataSource;
    }

    public static RecipeRepository getInstance(@NonNull LocalRecipeDataSource localRecipeDataSource,
                                               @NonNull RemoteRecipeDataSource remoteRecipeDataSource) {
        if (sInstance == null) {
            synchronized (RecipeRepository.class) {
                if (sInstance == null) {
                    sInstance = new RecipeRepository(localRecipeDataSource,
                            remoteRecipeDataSource);
                }
            }
        }
        return sInstance;
    }

    public LiveData<RecipeResultWrapper> getRemoteRecipes(String query, String time, String diet) {
        MutableLiveData<RecipeResultWrapper> remoteRecipes = new MutableLiveData<>();
        mRemoteRecipeDataSource.getRecipes(query, time, diet)
            .subscribeOn(Schedulers.io())
            .subscribe((response) -> {
                if (response.isSuccessful() && response.body().getHits() != null) {
                    // XXX - Write Custom Gson mapper to handle this
                    List<Recipe> recipes = new ArrayList<>();
                    if (response.body().getHits() != null){
                        for (EdamamSearchResponse.EdamamSearchHit hit : response.body().getHits()) {
                            recipes.add(hit.getEdamamRecipe());
                        }
                    }
                    remoteRecipes.postValue(new RecipeResultWrapper(recipes, true, response.code(), null));
                } else {
                    remoteRecipes.postValue(new RecipeResultWrapper(null, false, response.code(), response.message()));
                }
            },(throwable) -> {
                remoteRecipes.postValue(new RecipeResultWrapper(null, false, INVALID_CODE, throwable.getMessage()));
        });
        return remoteRecipes;
    }

    public LiveData<Boolean> saveLocalRecipe(Recipe recipe) {
        // TODO - Save recipe into mLocalRecipeDataSource
        // TODO - Convert observable into live data and return to viewmodel
        return null;
    }

    public LiveData<List<Recipe>> getLocalRecipes() {
        return mLocalRecipeDataSource.getRecipes();
    }
}
