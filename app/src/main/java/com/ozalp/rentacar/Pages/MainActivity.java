package com.ozalp.rentacar.Pages;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ozalp.rentacar.DatabaseOperations.DBConnection;
import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.databinding.ActivityMainBinding;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        loginControl();
        dbData.getCarsData();
    }

    /*
    SELECT CarId, BrandName, ColorName, ModelYear, DailyPrice, ModelName, CarImage FROM Cars
JOIN BRANDS ON Cars.BrandId = BRANDS.BrandId
JOIN Colors ON Cars.ColorId = COLORS.ColorId
JOIN CarModels ON Cars.ModelId = CarModels.ModelId
     */

    private void init() {
        sharedPreferences  = getSharedPreferences("com.ozalp.rentacar", MODE_PRIVATE);
        dbData = new DBData(connection());
        dbData.getData();
    }

    private void loginControl() {
        String tempEmail = sharedPreferences.getString("email", "");
        String tempPassword = sharedPreferences.getString("password", "");
        if(tempEmail.equals("")) {
            goToLoginPage();
            finish();
        } else {
            dbData.loginQuery(tempEmail, tempPassword);
        }
    }

    private void goToLoginPage() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    private Connection connection(){
        DBConnection dbConnection = new DBConnection();
        return dbConnection.connect();
    }

    ActivityMainBinding binding;
    public static SharedPreferences sharedPreferences;
    public static DBData dbData;
}