package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.ozalp.rentacar.Adapter.AppointmentRequestAdapter;
import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.DatabaseOperations.DBGetData;
import com.ozalp.rentacar.Models.Appointment;
import com.ozalp.rentacar.databinding.ActivityAppointmentRequestBinding;

import java.util.ArrayList;

public class AppointmentRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbGetData = DBGetData.getInstance();
        appointmentListingOperations();
    }

    private void appointmentListingOperations() {
        ArrayList<Appointment> appointmentList = new ArrayList<>(dbGetData.getAppointmentData());
        showAppointmentData(appointmentList);
    }

    private void showAppointmentData(ArrayList<Appointment> appointmentList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppointmentRequestAdapter appointmentRequestAdapter = new AppointmentRequestAdapter(appointmentList);
        binding.recyclerView.setAdapter(appointmentRequestAdapter);
        appointmentRequestAdapter.notifyDataSetChanged();
    }

    ActivityAppointmentRequestBinding binding;
    private DBGetData dbGetData;
}