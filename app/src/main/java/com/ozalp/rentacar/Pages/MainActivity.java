package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ozalp.rentacar.Adapter.CarListAdapter;
import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.MemoryOperations.SharedPreferencesOperations;
import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.databinding.ActivityMainBinding;

import java.util.ArrayList;

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
    private void spinnerOperations() {
        Spinner spinner = binding.spinner;
        ArrayList<String> spinnerProperties = new ArrayList<>();
        spinnerProperties.add("Son eklenene göre sırala");
        spinnerProperties.add("İlk eklenene göre sırala");
        spinnerProperties.add("Markaya göre sırala");
        spinnerProperties.add("Modele göre sırala");
        spinnerProperties.add("Vites türüne göre sırala");
        spinnerProperties.add("Yakıt türüne göre sırala");

        // ArrayAdapter oluştur ve Spinner'a bağla
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerProperties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Spinner'daki öğeleri dinlemek için OnItemSelectedListener ekleyin
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0) { // son eklenene göre
                    orderByOrWhereQuery = "ORDER BY CarId DESC";
                } else if(position == 1) { // ilk eklenene göre
                    orderByOrWhereQuery = "ORDER BY CarId ASC";
                } else if(position == 2) { // markaya göre

                }
                carListingOperations(orderByOrWhereQuery);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
    String selectedRadioButton = "";

    @Override
    protected void onStart() {
        super.onStart();
        spinnerOperations();
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
    }

    private void carListingOperations(String orderByOrWhereQuery) {
        carList = new ArrayList<>(dbData.getCarsData(orderByOrWhereQuery));
        showCarList(carList);
        binding.totalListSizeTextView.setText("Toplam " + carList.size() + " sonuç listeleniyor..");
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
    public DBData dbData;
    ArrayList<Car> carList;
    private String orderByOrWhereQuery = "ORDER BY CarId DESC";
}