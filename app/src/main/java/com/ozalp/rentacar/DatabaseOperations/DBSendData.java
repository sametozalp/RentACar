package com.ozalp.rentacar.DatabaseOperations;

import static com.ozalp.rentacar.Models.User.myUser;

import android.widget.EditText;

import com.ozalp.rentacar.Models.Car;

import java.sql.PreparedStatement;

public class DBSendData extends DBData{

    public static DBSendData getInstance() {
        if (instance == null) {
            connection = connection();
            instance = new DBSendData();
        }
        return instance;
    }

    // randevu isteği gönderir
    public int appointmentRequest(Car car, String formattedStartDate, String formattedEndDate) {

        String query = "INSERT INTO RENTALS (CarId, CustomerId, RentDate, ReturnDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = getInstance().connection.prepareStatement(query)) {
            preparedStatement.setInt(1, car.getCarID());
            preparedStatement.setInt(2, myUser.getUserID());
            preparedStatement.setString(3, formattedStartDate);
            preparedStatement.setString(4, formattedEndDate);

            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return 0;
    }

    public int signUpOperations(String query, EditText nameEditText, EditText surnameEditText, EditText emailEditText, EditText passwordEditText) {

        try (PreparedStatement preparedStatement = getInstance().connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameEditText.getText().toString());
            preparedStatement.setString(2, surnameEditText.getText().toString());
            preparedStatement.setString(3, emailEditText.getText().toString());
            preparedStatement.setString(4, passwordEditText.getText().toString());

            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return 0;
    }

    private static DBSendData instance;
}
