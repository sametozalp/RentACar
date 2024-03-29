package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.ozalp.rentacar.Adapter.CarListAdapter;
import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.DatabaseOperations.DBGetData;
import com.ozalp.rentacar.MemoryOperations.SharedPreferencesOperations;
import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.R;
import com.ozalp.rentacar.Spinner.SpinnerOperations;
import com.ozalp.rentacar.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        appbar();
        loginControl();
    }

    @Override
    protected void onStart() {
        super.onStart();
        carListingOperations(orderByOrWhereQuery);
    }

    public void floatButton(View view) {
        Intent intent = new Intent(getApplicationContext(), AppointmentRequest.class);
        startActivity(intent);
    }

    private void appbar() {
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("Rent A Car");
        toolbar.setTitleTextColor(Color.WHITE);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.gradient_background);
        getSupportActionBar().setBackgroundDrawable(drawable);
    }

    public void carListingOperations(String orderByOrWhereQuery) {
        carList = new ArrayList<>(dbGetData.getCarsData(orderByOrWhereQuery));
        showCarList(carList);
        binding.totalListSizeTextView.setText("Toplam " + carList.size() + " sonuç listeleniyor..");
    }

    private void showCarList(ArrayList<Car> carList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CarListAdapter carListAdapter = new CarListAdapter(carList);
        binding.recyclerView.setAdapter(carListAdapter);
        carListAdapter.notifyDataSetChanged();
    }

    public void logoutTextView(View view) {
        sharedPreferences.edit().putString("email", "").apply();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private void init() {
        sharedPreferences = SharedPreferencesOperations.getInstance(this);
        SpinnerOperations.spinnerOperations(binding, MainActivity.this, this);
        dbData = DBData.getInstance();
        dbGetData = DBGetData.getInstance();
        dbData.getData();
    }

    private void loginControl() {
        String tempEmail = sharedPreferences.getString("email", "");
        String tempPassword = sharedPreferences.getString("password", "");
        if (tempEmail.equals("")) {
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
    private DBData dbData;
    private DBGetData dbGetData;
    ArrayList<Car> carList;
    private String orderByOrWhereQuery = "ORDER BY CarId DESC";
}