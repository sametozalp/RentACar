package com.ozalp.rentacar.DatabaseOperations;

import static com.ozalp.rentacar.MemoryOperations.SharedPreferencesOperations.sharedPreferencesForSucessLogin;
import static com.ozalp.rentacar.Models.User.myUser;

import android.widget.EditText;

import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBData {
    private DBData() {
    }

    public static DBData getInstance() {
        if (instance == null) {
            connection = connection();
            instance = new DBData();
        }
        return instance;
    }

    private static Connection connection() {
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
/*
    public int appointmentRequest(Car car, String formattedStartDate, String formattedEndDate) {

        String query = "INSERT INTO RENTALS (CarId, CustomerId, RentDate, ReturnDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = getInstance().connection.prepareStatement(query)) {
            preparedStatement.setInt(1, car.getCarID());
            preparedStatement.setInt(2, myUser.getUserID());
            preparedStatement.setDate(3, formattedStartDate);
            preparedStatement.setDate(4, formattedEndDate);

            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }



        return 0;
    }
 */

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

    public ArrayList<Car> getCarsData() {

        Car car;
        ArrayList<Car> carList = new ArrayList<>();

        if (connection != null) {
            try {
                String sqlQuery = "SELECT CarId, BrandName, ColorName, ModelYear, DailyPrice, ModelName, CarImage, FuelTypeName, GearTypeName, CarStatus FROM Cars \n" +
                        "JOIN BRANDS ON Cars.BrandId = BRANDS.BrandId\n" +
                        "JOIN Colors ON Cars.ColorId = COLORS.ColorId\n" +
                        "JOIN CarModels ON Cars.ModelId = CarModels.ModelId\n" +
                        "JOIN FuelTypes ON Cars.FuelTypeId = FuelTypes.FuelTypeId\n" +
                        "JOIN GearTypes ON Cars.GearTypeId = GearTypes.GearId\n" +
                        "ORDER BY CarId DESC";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    car = new Car(resultSet.getInt("CarId"),
                            resultSet.getString("BrandName"),
                            resultSet.getString("ColorName"),
                            resultSet.getInt("ModelYear"),
                            resultSet.getInt("DailyPrice"),
                            resultSet.getString("ModelName"),
                            resultSet.getString("FuelTypeName"),
                            resultSet.getString("GearTypeName"),
                            resultSet.getString("CarImage"),
                            resultSet.getBoolean("CarStatus")
                    );
                    carList.add(car);
                }

                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        return carList;
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

    private static Connection connection;
    private static DBData instance;
}
//statement.close();
//connection.close();