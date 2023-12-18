package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.databinding.ActivityCarDetailsBinding;

public class CarDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Car car = (Car) getIntent().getSerializableExtra("car");

        showCarDetails(car);

    }

    private void showCarDetails(Car car) {
        try {
            binding.carBrandAndModelTextView.setText(car.getBrandName() + " " + car.getModelName() + " " + car.getModelYear());
            binding.carPriceTextView.setText(String.valueOf(car.getDailyPrice()) + " TL");
            binding.gearTypeTextView.setText(car.getGearType());
            binding.fuelTypeTextView.setText(car.getFuelType());
            binding.colorTextView.setText(car.getColorName());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    public void appointmentRequestButton(View view) {

    }

    ActivityCarDetailsBinding binding;
}