package com.demo.simplecook.db;

import android.content.Context;

import com.demo.simplecook.db.dao.RecipeDao;
import com.demo.simplecook.model.Recipe;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Recipe.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase sInstance;

    public static final String DATABASE_NAME = "simple_cook_db";

    public abstract RecipeDao recipeDao();

    public static AppDataBase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDataBase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        return sInstance;
    }
}