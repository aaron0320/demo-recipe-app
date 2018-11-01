package com.demo.simplecook.repository;

import com.demo.simplecook.model.Recipe;

import java.util.List;

public class RecipeResultWrapper {
    public static final int INVALID_CODE = -1;
    public static final int INVALID_LAST_INDEX = -1;

    private List<Recipe> recipes;
    private boolean isSucess;
    private int code;
    private String message;
    private int lastIndex;

    public RecipeResultWrapper(List<Recipe> recipes, boolean isSuccess, int code, String message, int lastIndex) {
        this.recipes = recipes;
        this.isSucess = isSuccess;
        this.code = code;
        this.message = message;
        this.lastIndex = lastIndex;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public boolean isSucess() {
        return isSucess;
    }

    public void setSucess(boolean sucess) {
        isSucess = sucess;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }
}
