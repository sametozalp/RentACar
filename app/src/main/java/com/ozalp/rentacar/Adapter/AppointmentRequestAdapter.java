package com.ozalp.rentacar.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ozalp.rentacar.Models.Appointment;
import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.Pages.MainActivity;
import com.ozalp.rentacar.databinding.ActivityAppointmentRequestBinding;
import com.ozalp.rentacar.databinding.AppointmentRequestRowBinding;

import java.util.ArrayList;

public class AppointmentRequestAdapter extends RecyclerView.Adapter<AppointmentRequestAdapter.AppointmentHolder> {
    ArrayList<Appointment> appointmentList;

    public AppointmentRequestAdapter(ArrayList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppointmentRequestRowBinding appointmentRequestRowBinding = AppointmentRequestRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AppointmentHolder(appointmentRequestRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    class AppointmentHolder extends RecyclerView.ViewHolder {
        AppointmentRequestRowBinding appointmentRequestRowBinding;

        public AppointmentHolder(AppointmentRequestRowBinding appointmentRequestRowBinding) {
            super(appointmentRequestRowBinding.getRoot());
            this.appointmentRequestRowBinding = appointmentRequestRowBinding;
        }
    }


}
