package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ozalp.rentacar.Adapter.CarListAdapter;
import com.ozalp.rentacar.DatabaseOperations.DBConnection;
import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.MemoryOperations.SharedPreferencesOperations;
import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.databinding.ActivityMainBinding;

import java.sql.Connection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        loginControl();
        carListingOperations();
    }

    private void carListingOperations() {
        ArrayList<Car> carList = new ArrayList<>(dbData.getCarsData());
        showCarList(carList);
    }

    private void showCarList(ArrayList<Car> carList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CarListAdapter carListAdapter = new CarListAdapter(carList);
        binding.recyclerView.setAdapter(carListAdapter);
        carListAdapter.notifyDataSetChanged();
    }

    private void init() {
        sharedPreferences = SharedPreferencesOperations.getInstance(this);
        dbData = DBData.getInstance();
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

    ActivityMainBinding binding;
    public SharedPreferences sharedPreferences;
    public DBData dbData;
}