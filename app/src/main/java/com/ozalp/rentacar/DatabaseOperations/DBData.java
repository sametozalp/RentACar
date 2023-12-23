package com.ozalp.rentacar.DatabaseOperations;

import static com.ozalp.rentacar.MemoryOperations.SharedPreferencesOperations.sharedPreferencesForSucessLogin;
import static com.ozalp.rentacar.Models.User.myUser;

import android.widget.EditText;

import com.ozalp.rentacar.Models.Appointment;
import com.ozalp.rentacar.Models.Car;
import com.ozalp.rentacar.Models.User;
import com.ozalp.rentacar.Pages.AppointmentRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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

    // gönderilen randevu isteklerinin bilgisini alır
    public ArrayList<Appointment> getAppointmentData() {
        Appointment appointment;
        ArrayList<Appointment> appointmentList = new ArrayList<>();

        if (connection != null) {
            try {
                String sqlQuery = "SELECT \n" +
                        "    rentals.RentalId, \n" +
                        "    rentals.CarId, \n" +
                        "    rentals.CustomerId, \n" +
                        "    brands.BrandName + ' ' + carmodels.ModelName + ' ' + CAST(cars.ModelYear AS nvarchar(10)) AS CarTitle,\n" +
                        "\tDATEDIFF(DAY, PARSE(RentDate AS datetime USING 'en-US'),PARSE(ReturnDate AS datetime USING 'en-US')) + 1 AS TotalRentDays,\n" +
                        "\tcars.DailyPrice,\n" +
                        "\tcolors.ColorName,\n" +
                        "    rentals.RentDate, \n" +
                        "    rentals.ReturnDate,\n" +
                        "\tstatuses.CarStatus\n" +
                        "FROM \n" +
                        "    rentals rentals\n" +
                        "JOIN \n" +
                        "    cars cars ON rentals.CarId = cars.CarId\n" +
                        "JOIN \n" +
                        "    brands brands ON cars.BrandId = brands.BrandId\n" +
                        "JOIN\n" +
                        "\tcolors colors ON colors.ColorId = cars.ColorId\n" +
                        "JOIN\n" +
                        "\tstatuses statuses ON statuses.StatusId = rentals.CarStatusId\n" +
                        "JOIN\n" +
                        "\tcarmodels carmodels ON carmodels.ModelId = rentals.CarId\n" +
                        "WHERE CustomerId = " +
                        myUser.getUserID() + "\n" +
                        "ORDER BY RentalId DESC";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    appointment = new Appointment(
                            resultSet.getInt("RentalId"),
                            resultSet.getInt("CarId"),
                            resultSet.getInt("CustomerId"),
                            resultSet.getInt("TotalRentDays"),
                            resultSet.getInt("DailyPrice"),
                            resultSet.getString("CarTitle"),
                            resultSet.getString("ColorName"),
                            resultSet.getString("RentDate"),
                            resultSet.getString("ReturnDate"),
                            resultSet.getString("CarStatus")
                    );

                    appointmentList.add(appointment);
                }

                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        return appointmentList;
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

    public ArrayList<Car> getCarsData(String orderByOrWhereQuery) {

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
                        orderByOrWhereQuery;

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