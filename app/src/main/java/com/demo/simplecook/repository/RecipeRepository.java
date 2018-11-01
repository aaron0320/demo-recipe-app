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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;

import static com.demo.simplecook.repository.RecipeResultWrapper.INVALID_CODE;
import static com.demo.simplecook.repository.RecipeResultWrapper.INVALID_LAST_INDEX;

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

    public LiveData<RecipeResultWrapper> getRemoteRecipes(String query, String time, String diet, int nextPageStartIndex) {
        MutableLiveData<RecipeResultWrapper> remoteRecipes = new MutableLiveData<>();
        // XXX - Consider returning the disposable to ViewModel
        // In case when the MediatorLiveData remove this from the source, we can dispose this
        Disposable disposable = mRemoteRecipeDataSource.getRecipes(query, time, diet, nextPageStartIndex)
            .subscribeOn(Schedulers.io())
            .subscribe(response -> {
                if (response.isSuccessful() && response.body().getHits() != null) {
                    // XXX - Write Custom Gson mapper to handle this
                    List<Recipe> recipes = new ArrayList<>();
                    if (response.body().getHits() != null){
                        for (EdamamSearchResponse.EdamamSearchHit hit : response.body().getHits()) {
                            recipes.add(hit.getEdamamRecipe());
                        }
                    }
                    remoteRecipes.postValue(new RecipeResultWrapper(recipes, true, response.code(), null, response.body().getTo()));
                } else {
                    remoteRecipes.postValue(new RecipeResultWrapper(null, false, response.code(), response.message(), INVALID_LAST_INDEX));
                }
            },(throwable) -> {
                remoteRecipes.postValue(new RecipeResultWrapper(null, false, INVALID_CODE, throwable.getMessage(), INVALID_LAST_INDEX));
        });
        return remoteRecipes;
    }

    public LiveData<List<Recipe>> getLocalRecipes() {
        return mLocalRecipeDataSource.getRecipes();
    }

    public LiveData<Recipe> getLocalRecipe(String webUrl) {
        return mLocalRecipeDataSource.getRecipe(webUrl);
    }

    public LiveData<Boolean> saveLocalRecipe(Recipe recipe) {
        MutableLiveData<Boolean> saveResult = new MutableLiveData<>();
        Disposable disposable = mLocalRecipeDataSource.saveRecipe(recipe)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> saveResult.postValue(response),
                        throwable -> saveResult.postValue(false)
                );
        return saveResult;
    }

    public LiveData<Boolean> deleteLocalRecipe(Recipe recipe) {
        MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();
        Disposable disposable = mLocalRecipeDataSource.deleteRecipe(recipe)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> deleteResult.postValue(response),
                        throwable -> deleteResult.postValue(false)
                );
        return deleteResult;
    }

}
