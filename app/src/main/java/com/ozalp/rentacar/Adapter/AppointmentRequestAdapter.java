package com.ozalp.rentacar.Adapter;

import android.graphics.Color;
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
        if(appointmentList.get(position).getCarStatus().equals("Onaylandı")) {
            holder.appointmentRequestRowBinding.statusTextView.setText("Onay Durumu: " + appointmentList.get(position).getCarStatus());
            holder.appointmentRequestRowBinding.statusTextView.setTextColor(Color.parseColor("#09471A"));
        } else if(appointmentList.get(position).getCarStatus().equals("Reddedildi")) {
            holder.appointmentRequestRowBinding.statusTextView.setText("Onay Durumu: " + appointmentList.get(position).getCarStatus());
            holder.appointmentRequestRowBinding.statusTextView.setTextColor(Color.parseColor("#670606"));
        } else {
            holder.appointmentRequestRowBinding.statusTextView.setText("Onay Durumu: " + appointmentList.get(position).getCarStatus());
            holder.appointmentRequestRowBinding.statusTextView.setTextColor(Color.GRAY);
        }
        holder.appointmentRequestRowBinding.rentalIDTextView.setText("Rental ID: " + appointmentList.get(position).getRentalID());
        holder.appointmentRequestRowBinding.carIDTextView.setText("Car ID: " + appointmentList.get(position).getCarID());
        holder.appointmentRequestRowBinding.customerIDTextView.setText("Customer ID: " + appointmentList.get(position).getCustomerID());
        holder.appointmentRequestRowBinding.carBrandAndModelTextView.setText(appointmentList.get(position).getCarTitle());
        holder.appointmentRequestRowBinding.dailyPriceTextView.setText("Günlük: " + appointmentList.get(position).getDailyPrice() + " TL");
        holder.appointmentRequestRowBinding.colorTextView.setText(appointmentList.get(position).getColorName());
        holder.appointmentRequestRowBinding.rentDateTextView.setText("Teslim Alınacak: "+ appointmentList.get(position).getRentDate());
        holder.appointmentRequestRowBinding.returnDateTextView.setText("Teslim Edilecek: " + appointmentList.get(position).getReturnDate());
        holder.appointmentRequestRowBinding.totalRentDaysTextView.setText(appointmentList.get(position).getTotalRentDays() + " gün");
        holder.appointmentRequestRowBinding.totalPriceTextView.setText("Toplam Fiyat: " + appointmentList.get(position).getDailyPrice() * appointmentList.get(position).getTotalRentDays() + " TL");
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
