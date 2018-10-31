package com.demo.simplecook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes",
        indices = {
            @Index("webUrl")
        }
)
public class Recipe implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int dbId;

    @SerializedName("label")
    private String label;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("source")
    private String source;
    @SerializedName("url")
    private String webUrl;
    @SerializedName("yield")
    private float serveSize;
    @SerializedName("dietLabels")
    private List<String> dietLabels;
    @SerializedName("ingredientLines")
    private List<String> ingredientLines;
    @SerializedName("totalTime")
    private float totalTime;
    @Embedded
    @SerializedName("totalDaily")
    private NutrientList totalDailyNutrients;

    public Recipe(String label, String imageUrl, String source, String webUrl, float serveSize, List<String> dietLabels,
                  List<String> ingredientLines, float totalTime, NutrientList totalDailyNutrients) {
        this.label = label;
        this.imageUrl = imageUrl;
        this.source = source;
        this.webUrl = webUrl;
        this.serveSize = serveSize;
        this.dietLabels = dietLabels;
        this.ingredientLines = ingredientLines;
        this.totalTime = totalTime;
        this.totalDailyNutrients = totalDailyNutrients;
    }

    public Recipe(Parcel in) {
        this.dbId = in.readInt();
        this.label = in.readString();
        this.imageUrl = in.readString();
        this.source = in.readString();
        this.webUrl = in.readString();
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
        dest.writeInt(dbId);
        dest.writeString(label);
        dest.writeString(imageUrl);
        dest.writeString(source);
        dest.writeString(webUrl);
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

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
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
