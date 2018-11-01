package com.demo.simplecook.db.dao;


import com.demo.simplecook.model.Recipe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE webUrl = :webUrl")
    LiveData<Recipe> getRecipe(String webUrl);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveRecipes(List<Recipe> recipes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Query("DELETE FROM recipes")
    void dropAllRecipes();
}
