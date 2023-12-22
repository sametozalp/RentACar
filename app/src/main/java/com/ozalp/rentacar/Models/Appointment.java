package com.ozalp.rentacar.Models;

public class Appointment {
    public Appointment(int rentalID, int carID, int customerID, String rentDate, String returnDate, int carStatusID) {
        this.rentalID = rentalID;
        this.carID = carID;
        this.customerID = customerID;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.carStatusID = carStatusID;
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

    public int getCarStatusID() {
        return carStatusID;
    }

    private int rentalID, carID, customerID, carStatusID;
    private String rentDate, returnDate;
}
