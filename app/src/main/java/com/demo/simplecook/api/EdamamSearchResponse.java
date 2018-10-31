package com.demo.simplecook.api;

import com.demo.simplecook.model.Recipe;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EdamamSearchResponse {
    @SerializedName("hits")
    List<EdamamSearchHit> hits;

    public List<EdamamSearchHit> getHits() {
        return hits;
    }

    public static class EdamamSearchHit {
        @SerializedName("recipe")
        Recipe edamamRecipe;

        public Recipe getEdamamRecipe() {
            return edamamRecipe;
        }
    }
}
