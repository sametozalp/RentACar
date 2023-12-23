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
import android.widget.Toast;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerProperties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) { // son eklenene göre
                    orderByOrWhereQuery = "ORDER BY CarId DESC";
                } else if (position == 1) { // ilk eklenene göre
                    orderByOrWhereQuery = "ORDER BY CarId ASC";
                } else if (position == 2) { // markaya göre
                    alertDialog();
                }
                carListingOperations(orderByOrWhereQuery);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void alertDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Seçenekleri Seçin");

            // RadioButtonları içerecek bir RadioGroup oluştur
            RadioGroup radioGroup = new RadioGroup(MainActivity.this);
            radioGroup.setOrientation(RadioGroup.VERTICAL);

            ArrayList<String> optionList = new ArrayList();
            optionList.add("selam");
            optionList.add("bebek");
            // RadioButtonları for döngüsü ile ekleyin
            for (String option : optionList) {
                RadioButton radioButton = new RadioButton(MainActivity.this);
                radioButton.setText(option);
                radioGroup.addView(radioButton);
            }

            // RadioGroup'u AlertDialog'a ekle
            builder.setView(radioGroup);

            // Pozitif buton (Seçildiğinde yapılacak işlemler)
            builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Seçilen RadioButton'ın değerini al
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if (selectedId != -1) {
                        RadioButton selectedRadioButton = radioGroup.findViewById(selectedId);
                        selectedRadioOption = selectedRadioButton.getText().toString();
                        System.out.println(selectedRadioButton);
                    }
                }
            });

            // AlertDialog'u göster
            builder.show();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    String selectedRadioOption = "";

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