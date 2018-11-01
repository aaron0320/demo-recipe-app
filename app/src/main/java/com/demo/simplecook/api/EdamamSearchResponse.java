package com.demo.simplecook.api;

import com.demo.simplecook.model.Recipe;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EdamamSearchResponse {
    @SerializedName("hits")
    List<EdamamSearchHit> hits;
    @SerializedName("from")
    int from;
    @SerializedName("to")
    int to;

    public List<EdamamSearchHit> getHits() {
        return hits;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public static class EdamamSearchHit {
        @SerializedName("recipe")
        Recipe edamamRecipe;

        public Recipe getEdamamRecipe() {
            return edamamRecipe;
        }
    }
}
