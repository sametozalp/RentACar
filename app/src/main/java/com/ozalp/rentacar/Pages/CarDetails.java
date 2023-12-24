package com.ozalp.rentacar.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.databinding.ActivityCarDetailsBinding;

import net.sourceforge.jtds.jdbc.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CarDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbData = DBData.getInstance();
        car = (Car) getIntent().getSerializableExtra("car");
        buttonVisibility(car.getCarStatus());
        showCarDetails(car);

    }

    private void showCarDetails(Car car) {
        try {
            binding.carBrandAndModelTextView.setText(car.getBrandName() + " " + car.getModelName() + " " + car.getModelYear());
            binding.carPriceTextView.setText(String.valueOf(car.getDailyPrice()) + " TL");
            binding.gearTypeTextView.setText(car.getGearType());
            binding.fuelTypeTextView.setText(car.getFuelType());
            binding.colorTextView.setText(car.getColorName());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void appointmentRequestButton(View view) {
        showDateRangePicker();
    }

    private void showDateRangePicker() {
        MaterialDatePicker<androidx.core.util.Pair<Long, Long>> dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Tarih Aralığını Seçin")
                .setPositiveButtonText("Onayla!")
                .build();

        dateRangePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                if (selection instanceof Pair) {
                    Pair<Long, Long> dateRange = (Pair<Long, Long>) selection;
                    long startDate = dateRange.first;
                    long endDate = dateRange.second;


                    String formattedStartDate = formatDate(startDate);
                    String formattedEndDate = formatDate(endDate);

                    dbData.appointmentRequest(car, formattedStartDate, formattedEndDate);

                    Toast.makeText(getApplicationContext(), "Randevu isteğiniz gönderilmiştir.", Toast.LENGTH_LONG).show();
                    binding.appointmentRequestButton.setEnabled(false);

                }
            }
        });

        dateRangePicker.show(getSupportFragmentManager(), dateRangePicker.toString());
    }
    private String formatDate(long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        return sdf.format(new Date(dateInMillis));
    }

    private void buttonVisibility(Boolean carStatus) {
        if (carStatus == true) {
            binding.appointmentRequestButton.setText("Araç şu anda kiralamaya uygun değil");
            binding.appointmentRequestButton.setEnabled(false);
        }
    }

    ActivityCarDetailsBinding binding;
    Car car;
    DBData dbData;

}