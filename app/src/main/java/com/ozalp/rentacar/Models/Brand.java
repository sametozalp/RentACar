package com.ozalp.rentacar.Models;

public class Brand {
    public Brand(int brandID, String brandName) {
        this.brandID = brandID;
        this.brandName = brandName;
    }

    public int getBrandID() {
        return brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    @Override
    public String toString() {
        return getBrandName();
    }

    private int brandID;
    private String brandName;
}
