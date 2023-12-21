package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.Models.Appointment;
import com.ozalp.rentacar.R;
import com.ozalp.rentacar.databinding.ActivityAppointmentRequestBinding;

public class AppointmentRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbData = DBData.getInstance();
        getAppointmentData();
    }

    private void getAppointmentData() {
        for(Appointment appointment: dbData.getAppointmentData()) {
            System.out.println(appointment.getCarID() + appointment.getCustomerID() + appointment.getRentalID() + appointment.getCarStatus().toString() + appointment.getRentDate() + appointment.getReturnDate());
        }
    }

    ActivityAppointmentRequestBinding binding;
    private DBData dbData;
}