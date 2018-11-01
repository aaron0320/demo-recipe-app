package com.demo.simplecook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class NutrientList implements Parcelable {
    @Embedded(prefix = "energy")
    @SerializedName("ENERC_KCAL")
    private NutrientInfo energyInfo;
    @Embedded(prefix = "fat")
    @SerializedName("FAT")
    private NutrientInfo fatInfo;
    @Embedded(prefix = "carbo")
    @SerializedName("CHOCDF")
    private NutrientInfo carboInfo;
    @Embedded(prefix = "fibre")
    @SerializedName("FIBTG")
    private NutrientInfo fibreInfo;
    @Embedded(prefix = "sugar")
    @SerializedName("SUGAR")
    private NutrientInfo sugarInfo;
    @Embedded(prefix = "protein")
    @SerializedName("PROCNT")
    private NutrientInfo proteinInfo;
    @Embedded(prefix = "cholesterol")
    @SerializedName("CHOLE")
    private NutrientInfo cholesterolInfo;
    @Embedded(prefix = "sodium")
    @SerializedName("NA")
    private NutrientInfo sodiumInfo;

    public NutrientList(NutrientInfo energyInfo, NutrientInfo fatInfo, NutrientInfo carboInfo, NutrientInfo fibreInfo,
                        NutrientInfo sugarInfo, NutrientInfo proteinInfo, NutrientInfo cholesterolInfo, NutrientInfo sodiumInfo) {
        this.energyInfo = energyInfo;
        this.fatInfo = fatInfo;
        this.carboInfo = carboInfo;
        this.fibreInfo = fibreInfo;
        this.sugarInfo = sugarInfo;
        this.proteinInfo = proteinInfo;
        this.cholesterolInfo = cholesterolInfo;
        this.sodiumInfo = sodiumInfo;
    }

    public NutrientList(Parcel in) {
        this.energyInfo = in.readParcelable(NutrientInfo.class.getClassLoader());
        this.fatInfo = in.readParcelable(NutrientInfo.class.getClassLoader());
        this.carboInfo = in.readParcelable(NutrientInfo.class.getClassLoader());
        this.fibreInfo = in.readParcelable(NutrientInfo.class.getClassLoader());
        this.sugarInfo = in.readParcelable(NutrientInfo.class.getClassLoader());
        this.proteinInfo = in.readParcelable(NutrientInfo.class.getClassLoader());
        this.cholesterolInfo = in.readParcelable(NutrientInfo.class.getClassLoader());
        this.sodiumInfo = in.readParcelable(NutrientInfo.class.getClassLoader());
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(energyInfo, flags);
        dest.writeParcelable(fatInfo, flags);
        dest.writeParcelable(carboInfo, flags);
        dest.writeParcelable(fibreInfo, flags);
        dest.writeParcelable(sugarInfo, flags);
        dest.writeParcelable(proteinInfo, flags);
        dest.writeParcelable(cholesterolInfo, flags);
        dest.writeParcelable(sodiumInfo, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public NutrientList createFromParcel(Parcel in) {
            return new NutrientList(in);
        }

        public NutrientList[] newArray(int size) {
            return new NutrientList[size];
        }
    };

    public NutrientInfo getEnergyInfo() {
        return energyInfo;
    }

    public NutrientInfo getFatInfo() {
        return fatInfo;
    }

    public NutrientInfo getCarboInfo() {
        return carboInfo;
    }

    public NutrientInfo getFibreInfo() {
        return fibreInfo;
    }

    public NutrientInfo getSugarInfo() {
        return sugarInfo;
    }

    public NutrientInfo getProteinInfo() {
        return proteinInfo;
    }

    public NutrientInfo getCholesterolInfo() {
        return cholesterolInfo;
    }

    public NutrientInfo getSodiumInfo() {
        return sodiumInfo;
    }

    public static class NutrientInfo implements Parcelable{
        @SerializedName("label")
        private String label;
        @SerializedName("quantity")
        private float quantity;
        @SerializedName("unit")
        private String unit;

        public NutrientInfo(String label, float quantity, String unit) {
            this.label = label;
            this.quantity = quantity;
            this.unit = unit;
        }

        public NutrientInfo(Parcel in) {
            this.label = in.readString();
            this.quantity = in.readFloat();
            this.unit = in.readString();
        }

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(label);
            dest.writeFloat(quantity);
            dest.writeString(unit);
        }

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public NutrientInfo createFromParcel(Parcel in) {
                return new NutrientInfo(in);
            }

            public NutrientInfo[] newArray(int size) {
                return new NutrientInfo[size];
            }
        };

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public float getQuantity() {
            return quantity;
        }

        public void setQuantity(float quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}