package com.ozalp.rentacar.Models;

public class User {

    public User(int userID, String firstName, String lastName, String email, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public static User myUser;

    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}


