package com.ozalp.rentacar.MemoryOperations;

import static com.ozalp.rentacar.Models.User.myUser;
import static com.ozalp.rentacar.Pages.MainActivity.sharedPreferences;

public class SharedPreferencesOperations {

    public static void sharedPreferencesForSucessLogin() {
        sharedPreferences.edit().putInt("userID", myUser.getUserID())
                .putString("firstName", myUser.getFirstName())
                .putString("lastName", myUser.getLastName())
                .putString("email", myUser.getEmail())
                .putString("password", myUser.getPassword()).apply();
    }
}
