package com.ozalp.rentacar.MemoryOperations;

import static com.ozalp.rentacar.Models.User.myUser;

import android.content.SharedPreferences;

public class SharedPreferencesOperations {

    public static void sharedPreferencesForSucessLogin() {
        sharedPreferences.edit().putInt("userID", myUser.getUserID())
                .putString("firstName", myUser.getFirstName())
                .putString("lastName", myUser.getLastName())
                .putString("email", myUser.getEmail())
                .putString("password", myUser.getPassword());
    }

    public static SharedPreferences sharedPreferences;
}
