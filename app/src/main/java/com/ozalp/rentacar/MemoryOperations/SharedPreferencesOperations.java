package com.ozalp.rentacar.MemoryOperations;

import static com.ozalp.rentacar.Models.User.myUser;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesOperations {

    public static void sharedPreferencesForSucessLogin() {
        sharedPreferences.edit().putInt("userID", myUser.getUserID())
                .putString("firstName", myUser.getFirstName())
                .putString("lastName", myUser.getLastName())
                .putString("email", myUser.getEmail())
                .putString("password", myUser.getPassword()).apply();
    }

    public static SharedPreferences getInstance(Context context) {
        if(sharedPreferences == null) {
            sharedPreferences  = context.getSharedPreferences("com.ozalp.rentacar", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    private static SharedPreferences sharedPreferences;
}
