package com.ozalp.rentacar.DatabaseOperations;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public Connection connect() {
        connection = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return connection;
    }

    String ip = "192.168.137.217";
    //String ip = "172.16.31.111";
    private String driver = "net.sourceforge.jtds.jdbc.Driver";
    String username = "sa";
    String password = "Rentacar43";
    private String databaseUrl = "jdbc:jtds:sqlserver://" + ip + ":1433;databaseName=RentACar;user="+username+";password="+password+";";
    Connection connection;
}
