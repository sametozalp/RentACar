package com.ozalp.rentacar.DatabaseOperations;

import static com.ozalp.rentacar.Models.User.myUser;

import com.ozalp.rentacar.Models.Appointment;
import com.ozalp.rentacar.Models.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DBGetData extends DBData {
    
    public static DBGetData getInstance() {
        if (instance == null) {
            connection = connection();
            instance = new DBGetData();
        }
        return instance;
    }

    public HashMap<Integer, String> getBrandsData() {

        HashMap<Integer, String> map = new HashMap<>();

        if (connection != null) {
            try {
                String sqlQuery = "Select * From Brands";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    map.put(resultSet.getInt("BrandId"), resultSet.getString("BrandName"));
                }

                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        return map;
    }

    public HashMap<Integer, String> getCarModelsData() {

        HashMap<Integer, String> map = new HashMap<>();

        if (connection != null) {
            try {
                String sqlQuery = "Select * From CarModels";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    map.put(resultSet.getInt("ModelId"), resultSet.getString("ModelName"));
                }

                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        return map;
    }

    public HashMap<Integer, String> getCarGearTypesData() {

        HashMap<Integer, String> map = new HashMap<>();

        if (connection != null) {
            try {
                String sqlQuery = "Select * From GearTypes";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    map.put(resultSet.getInt("GearId"), resultSet.getString("GearTypeName"));
                }

                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        return map;
    }

    public HashMap<Integer, String> getCarFuelTypesData() {

        HashMap<Integer, String> map = new HashMap<>();

        if (connection != null) {
            try {
                String sqlQuery = "Select * From FuelTypes";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    map.put(resultSet.getInt("FuelTypeId"), resultSet.getString("FuelTypeName"));
                }

                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        return map;
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

    private static DBGetData instance;
}
