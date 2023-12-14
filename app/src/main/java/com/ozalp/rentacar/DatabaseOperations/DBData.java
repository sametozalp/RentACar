package com.ozalp.rentacar.DatabaseOperations;

import static com.ozalp.rentacar.Pages.MainActivity.dbData;

import android.content.Intent;
import android.widget.EditText;

import com.ozalp.rentacar.Models.User;
import com.ozalp.rentacar.Pages.Login;
import com.ozalp.rentacar.Pages.MainActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBData {
    public DBData() {
    }

    public DBData(Connection connection) {
        this.connection = connection;
    }

    public void getData() {

        if (connection != null) {
            try {
                String sqlQuery = "SELECT * FROM Users";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {

                    System.out.println(resultSet.getString("UserId"));
                    System.out.println(resultSet.getString("FirstName"));
                    System.out.println(resultSet.getString("LastName"));
                    System.out.println(resultSet.getString("Email"));
                    System.out.println(resultSet.getString("Password"));
                }

                resultSet.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void signUpOperations(String query,
                                 EditText nameEditText,
                                 EditText surnameEditText,
                                 EditText emailEditText,
                                 EditText passwordEditText) {

        try (PreparedStatement preparedStatement = dbData.connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameEditText.getText().toString());
            preparedStatement.setString(2, surnameEditText.getText().toString());
            preparedStatement.setString(3, emailEditText.getText().toString());
            preparedStatement.setString(4, passwordEditText.getText().toString());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Kayıt eklendi!");
            } else {
                System.out.println("Kayıt eklenemedi!");
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public Connection connection;
}
//statement.close();
//connection.close();