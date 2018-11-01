package com.demo.simplecook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.demo.simplecook.db.converter.StringListConverter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "recipes")
public class Recipe implements Parcelable {
    @PrimaryKey()
    @SerializedName("url")
    @NonNull
    private String webUrl;
    @SerializedName("label")
    private String label;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("source")
    private String source;
    @SerializedName("yield")
    private float serveSize;
    @TypeConverters(StringListConverter.class)
    @SerializedName("dietLabels")
    private List<String> dietLabels;
    @TypeConverters(StringListConverter.class)
    @SerializedName("ingredientLines")
    private List<String> ingredientLines;
    @SerializedName("totalTime")
    private float totalTime;
    @Embedded
    @SerializedName("totalDaily")
    private NutrientList totalDailyNutrients;

    public Recipe() { this.webUrl = ""; }

    @Ignore
    public Recipe(String webUrl, String label, String imageUrl, String source, float serveSize, List<String> dietLabels,
                  List<String> ingredientLines, float totalTime, NutrientList totalDailyNutrients) {
        this.webUrl = webUrl;
        this.label = label;
        this.imageUrl = imageUrl;
        this.source = source;
        this.serveSize = serveSize;
        this.dietLabels = dietLabels;
        this.ingredientLines = ingredientLines;
        this.totalTime = totalTime;
        this.totalDailyNutrients = totalDailyNutrients;
    }

    @Ignore
    public Recipe(Parcel in) {
        this.webUrl = in.readString();
        this.label = in.readString();
        this.imageUrl = in.readString();
        this.source = in.readString();
        this.serveSize = in.readFloat();
        this.dietLabels = new ArrayList<>();
        in.readStringList(this.dietLabels);
        this.ingredientLines = new ArrayList<>();
        in.readStringList(this.ingredientLines);
        this.totalTime = in.readFloat();
        this.totalDailyNutrients = in.readParcelable(NutrientList.class.getClassLoader());
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(webUrl);
        dest.writeString(label);
        dest.writeString(imageUrl);
        dest.writeString(source);
        dest.writeFloat(serveSize);
        dest.writeStringList(dietLabels);
        dest.writeStringList(ingredientLines);
        dest.writeFloat(totalTime);
        dest.writeParcelable(totalDailyNutrients, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getServeSize() {
        return serveSize;
    }

    public void setServeSize(float serveSize) {
        this.serveSize = serveSize;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    public NutrientList getTotalDailyNutrients() {
        return totalDailyNutrients;
    }

    public void setTotalDailyNutrients(NutrientList totalDailyNutrients) {
        this.totalDailyNutrients = totalDailyNutrients;
    }
}
