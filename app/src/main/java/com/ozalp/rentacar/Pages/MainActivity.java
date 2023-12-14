package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ozalp.rentacar.DatabaseOperations.DBConnection;
import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.R;
import com.ozalp.rentacar.databinding.ActivityMainBinding;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       dbData = new DBData(connection());
       dbData.getData();

       loginControl();
    }

    private void loginControl() {
        sharedPreferences  = getSharedPreferences("com.ozalp.rentacar", MODE_PRIVATE);

        if(sharedPreferences.getInt("userID", -1) == -1) {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            finish();
        }
    }

    private Connection connection(){
        DBConnection dbConnection = new DBConnection();
        return dbConnection.connect();
    }

    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;
    public static DBData dbData;
}