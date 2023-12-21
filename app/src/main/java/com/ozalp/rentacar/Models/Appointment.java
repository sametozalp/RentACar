package com.ozalp.rentacar.Models;

public class Appointment {
    public Appointment(int rentalID, int carID, int customerID, String rentDate, String returnDate, Boolean carStatus) {
        this.rentalID = rentalID;
        this.carID = carID;
        this.customerID = customerID;
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

    public String getRentDate() {
        return rentDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public Boolean getCarStatus() {
        return carStatus;
    }

    private int rentalID, carID, customerID;
    private String rentDate, returnDate;
    private Boolean carStatus;
}
