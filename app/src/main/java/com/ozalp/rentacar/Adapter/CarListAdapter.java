package com.ozalp.rentacar.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ozalp.rentacar.Pages.CarDetails;
import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.databinding.CarCardViewBinding;

import java.io.Serializable;
import java.util.ArrayList;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarHolder> {

    ArrayList<Car> carList;

    public CarListAdapter(ArrayList<Car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CarCardViewBinding carCardViewBinding = CarCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CarHolder(carCardViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {
        try {
            holder.carCardViewBinding.carPriceTextView.setText(String.valueOf(carList.get(position).getDailyPrice()) + " TL");
            holder.carCardViewBinding.carBrandAndModelTextView.setText(carList.get(position).getBrandName() + " " + carList.get(position).getModelName() + " " + carList.get(position).getModelYear());
            holder.carCardViewBinding.GearTypeTextView.setText(carList.get(position).getGearType());
            holder.carCardViewBinding.FuelTypeTextView.setText(carList.get(position).getFuelType());
            holder.carCardViewBinding.ColorTextView.setText(carList.get(position).getColorName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(holder.itemView.getContext(), CarDetails.class);
                        intent.putExtra("car", (Serializable) carList.get(position));
                        holder.itemView.getContext().startActivity(intent);
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                }
            });

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    class CarHolder extends RecyclerView.ViewHolder {
        CarCardViewBinding carCardViewBinding;

        public CarHolder(CarCardViewBinding carCardViewBinding) {
            super(carCardViewBinding.getRoot());
            this.carCardViewBinding = carCardViewBinding;
        }
    }
}