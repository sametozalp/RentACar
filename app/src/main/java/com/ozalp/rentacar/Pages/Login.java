package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ozalp.rentacar.R;
import com.ozalp.rentacar.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    ActivityLoginBinding binding;
}