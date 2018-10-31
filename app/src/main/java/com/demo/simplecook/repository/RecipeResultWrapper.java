package com.demo.simplecook.repository;

import com.demo.simplecook.model.Recipe;

import java.util.List;

public class RecipeResultWrapper {
    public static final int INVALID_CODE = -1;

    private List<Recipe> recipes;
    private boolean isSucess;
    private int code;
    private String message;

    public RecipeResultWrapper(List<Recipe> recipes, boolean isSucess, int code, String message) {
        this.recipes = recipes;
        this.isSucess = isSucess;
        this.code = code;
        this.message = message;
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
}
