package com.ozalp.rentacar.Models;

public class Appointment {

    public Appointment(int rentalID, int carID, int customerID, int dailyPrice, String carTitle, String colorName, String rentDate, String returnDate, String carStatus) {
        this.rentalID = rentalID;
        this.carID = carID;
        this.customerID = customerID;
        this.dailyPrice = dailyPrice;
        this.carTitle = carTitle;
        this.colorName = colorName;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.carStatus = carStatus;
    }

    public int getRentalID() {
        return rentalID;
    }

    public int getCarID() {
        return carID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getDailyPrice() {
        return dailyPrice;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public String getColorName() {
        return colorName;
    }

    public String getRentDate() {
        return rentDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getCarStatus() {
        return carStatus;
    }

    private int rentalID, carID, customerID, dailyPrice;
    private String carTitle,colorName, rentDate, returnDate, carStatus;
}
