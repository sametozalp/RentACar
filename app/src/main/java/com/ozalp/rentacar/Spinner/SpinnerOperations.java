package com.ozalp.rentacar.Spinner;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.Pages.MainActivity;
import com.ozalp.rentacar.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class SpinnerOperations {

    public static void spinnerOperations(ActivityMainBinding binding, Context context, MainActivity mainActivity) {
        orderByOrWhereQuery = null;
        Spinner spinner = binding.spinner;
        ArrayList<String> spinnerProperties = new ArrayList<>();
        spinnerProperties.add("Son eklenene göre sırala");
        spinnerProperties.add("İlk eklenene göre sırala");
        spinnerProperties.add("Markaya göre sırala");
        spinnerProperties.add("Modele göre sırala");
        spinnerProperties.add("Vites türüne göre sırala");
        spinnerProperties.add("Yakıt türüne göre sırala");
        spinnerProperties.add("Fiyata göre sırala (Yüksekten düşüğe)");
        spinnerProperties.add("Fiyata göre sırala (Düşükten yükseğe)");
        spinnerProperties.add("Modele göre sırala (Yüksekten düşüğe)");
        spinnerProperties.add("Modele göre sırala (Düşükten yükseğe)");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerProperties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) { // son eklenene göre
                    orderByOrWhereQuery = "ORDER BY CarId DESC";
                } else if (position == 1) { // ilk eklenene göre
                    orderByOrWhereQuery = "ORDER BY CarId ASC";
                } else if (position == 2) { // markaya göre
                    alertDialog(context, mainActivity, dbData.getBrandsData(), position);
                } else if (position == 3) { // modele göre
                    alertDialog(context, mainActivity, dbData.getCarModelsData(), position);
                } else if (position == 4) { // vites türüne göre
                    alertDialog(context, mainActivity, dbData.getCarGearTypesData(), position);
                } else if (position == 5) { // yakıt türüne göre
                    alertDialog(context, mainActivity, dbData.getCarFuelTypesData(), position);
                } else if (position == 6) { // fiyata göre (yüksekten düşüğe)
                    orderByOrWhereQuery = "ORDER BY DailyPrice DESC";
                } else if (position == 7) { // fiyata göre (düşükten yükseğe)
                    orderByOrWhereQuery = "ORDER BY DailyPrice ASC";
                } else if (position == 8) { // modele göre (yüksekten düşüğe)
                    orderByOrWhereQuery = "ORDER BY ModelYear DESC";
                } else if (position == 9) { // modele göre (düşükten yükseğe)
                    orderByOrWhereQuery = "ORDER BY ModelYear ASC";
                }

                if (orderByOrWhereQuery != null)
                    mainActivity.carListingOperations(orderByOrWhereQuery); // verileri getir.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }


    private static void alertDialog(Context context, MainActivity mainActivity, HashMap<Integer, String> optionList, int position) {
        selectedRadioOption = null;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Seçiniz: ");

            RadioGroup radioGroup = new RadioGroup(context);
            radioGroup.setOrientation(RadioGroup.VERTICAL);


            for (Object option : optionList.values()) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(option.toString());
                radioGroup.addView(radioButton);
            }

            builder.setView(radioGroup);

            builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if (selectedId != -1) {
                        RadioButton selectedRadioButton = radioGroup.findViewById(selectedId);
                        selectedRadioOption = selectedRadioButton.getText().toString();
                        System.out.println(selectedRadioOption);

                        if (selectedRadioOption != null) {
                            if (position == 2) {
                                orderByOrWhereQuery = "WHERE BrandName = '" + selectedRadioOption + "'";
                            } else if (position == 3) {
                                orderByOrWhereQuery = "WHERE ModelName = '" + selectedRadioOption + "'";
                            } else if (position == 4) {
                                orderByOrWhereQuery = "WHERE GearTypeName = '" + selectedRadioOption + "'";
                            } else if (position == 5) {
                                orderByOrWhereQuery = "WHERE FuelTypeName = '" + selectedRadioOption + "'";
                            }
                        }

                        if (orderByOrWhereQuery != null)
                            mainActivity.carListingOperations(orderByOrWhereQuery);
                    }
                }
            });

            builder.show();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static String selectedRadioOption = null;
    private static String orderByOrWhereQuery = null;
    private static DBData dbData = DBData.getInstance();
}
