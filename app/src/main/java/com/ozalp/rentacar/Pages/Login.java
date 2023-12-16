package com.ozalp.rentacar.Pages;

import static com.ozalp.rentacar.MemoryOperations.SharedPreferencesOperations.sharedPreferences;
import static com.ozalp.rentacar.Models.User.myUser;
import static com.ozalp.rentacar.Pages.MainActivity.dbData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ozalp.rentacar.Models.User;
import com.ozalp.rentacar.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }



    public void signUpTextView(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public void loginButton(View view) {
        getEditTextData();
        dbData.loginQuery(emailText, passwordText);
    }

    private void getEditTextData() {
        emailText = emailEditText.getText().toString();
        passwordText = passwordEditText.getText().toString();
    }

    private void init() {
        emailEditText = binding.emailEditText;
        passwordEditText = binding.passwordEditText;
        emailText = "";
        passwordText = "";
        myUser = new User();
    }

    String emailText, passwordText;
    EditText emailEditText, passwordEditText;
    ActivityLoginBinding binding;
}