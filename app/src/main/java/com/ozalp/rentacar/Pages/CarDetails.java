package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ozalp.rentacar.databinding.ActivityCarDetailsBinding;

public class CarDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    ActivityCarDetailsBinding binding;
}