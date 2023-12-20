package com.ozalp.rentacar.Models;

import java.io.Serializable;

public class Car implements Serializable {

    public Car(int carID, String brandName, String colorName, int modelYear, int dailyPrice, String modelName, String fuelType, String gearType, String carImage, Boolean carStatus) {
        this.carID = carID;
        this.brandName = brandName;
        this.colorName = colorName;
        this.modelYear = modelYear;
        this.dailyPrice = dailyPrice;
        this.modelName = modelName;
        this.carImage = carImage;
        this.fuelType = fuelType;
        this.gearType = gearType;
        this.carStatus = carStatus;
    }

    public Boolean getCarStatus() {
        return carStatus;
    }

    public int getCarID() {
        return carID;
    }

    public int getModelYear() {
        return modelYear;
    }

    public int getDailyPrice() {
        return dailyPrice;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getColorName() {
        return colorName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getCarImage() {
        return carImage;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getGearType() {
        return gearType;
    }

    private Boolean carStatus;
    private int carID, modelYear, dailyPrice;
    private String brandName, colorName, modelName, carImage, fuelType, gearType;

}
