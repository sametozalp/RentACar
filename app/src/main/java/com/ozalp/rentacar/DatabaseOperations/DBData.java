package com.ozalp.rentacar.DatabaseOperations;

import static com.ozalp.rentacar.MemoryOperations.SharedPreferencesOperations.sharedPreferencesForSucessLogin;
import static com.ozalp.rentacar.Models.User.myUser;

import android.widget.EditText;

import com.ozalp.rentacar.Models.Appointment;
import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DBData {
    protected DBData() {
    }

    public static DBData getInstance() {
        if (instance == null) {
            connection = connection();
            instance = new DBData();
        }
        return instance;
    }

    protected static Connection connection() {
        DBConnection dbConnection = new DBConnection();
        return dbConnection.connect();
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

    public boolean loginQuery(String emailText, String passwordText) {

        if (connection != null) {
            try {
                String sqlQuery = "SELECT * FROM USERS WHERE Email = '" + emailText + "'";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    if (passwordText.equals(resultSet.getString("Password"))) {
                        declareMyUser(resultSet);
                        sharedPreferencesForSucessLogin();
                        resultSet.close();
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void declareMyUser(ResultSet resultSet) {
        try {
            myUser = new User(resultSet.getInt("UserId"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password")
            );
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    protected static Connection connection;
    protected static DBData instance;
}
//statement.close();
//connection.close();