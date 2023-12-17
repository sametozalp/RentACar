package com.ozalp.rentacar.Models;

public class Car {

    public Car(int carID, String brandName, String colorName, int modelYear, int dailyPrice, String modelName, String fuelType, String gearType, String carImage) {
        this.carID = carID;
        this.brandName = brandName;
        this.colorName = colorName;
        this.modelYear = modelYear;
        this.dailyPrice = dailyPrice;
        this.modelName = modelName;
        this.carImage = carImage;
        this.fuelType = fuelType;
        this.gearType = gearType;
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

    private int carID, modelYear, dailyPrice;
    private String brandName, colorName, modelName, carImage, fuelType, gearType;

}
