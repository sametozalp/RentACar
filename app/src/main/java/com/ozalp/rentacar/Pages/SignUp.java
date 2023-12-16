package com.ozalp.rentacar.Pages;

import static com.ozalp.rentacar.Pages.MainActivity.dbData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ozalp.rentacar.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        nameEditText = binding.nameEditText;
        surnameEditText = binding.surnameEditText;
        emailEditText = binding.emailEditText;
        passwordEditText = binding.passwordEditText;
    }

    public void signUpButton(View view) {
        if (!editTextEmptyControl(nameEditText)
                && !editTextEmptyControl(surnameEditText)
                && !editTextEmptyControl(emailEditText)
                && !editTextEmptyControl(passwordEditText))
        {
            signUpOperations();
        }
        else {
            Toast.makeText(getApplicationContext(), "Kutucukları doldurmak zorundasınız..", Toast.LENGTH_LONG).show();
        }
    }

    private void signUpOperations() {
        String query = "INSERT INTO USERS (FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?)";
        int affectedRows = dbData.signUpOperations(query, nameEditText, surnameEditText, emailEditText, passwordEditText);
        //Singleton

        if (affectedRows > 0) {
            System.out.println("Kayıt eklendi!");
            goToLoginPage();
        } else {
            System.out.println("Kayıt eklenemedi!");
        }
    }

    private void goToLoginPage() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    private boolean editTextEmptyControl(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    ActivitySignUpBinding binding;
    EditText nameEditText, surnameEditText, emailEditText, passwordEditText;
}