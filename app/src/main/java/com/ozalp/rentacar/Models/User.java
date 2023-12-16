package com.ozalp.rentacar.Models;

public class User {

    public User(){}

    public User(int userID, String firstName, String lastName, String email, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public static User myUser;

    public static User getMyUser() {
        return myUser;
    }

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}


