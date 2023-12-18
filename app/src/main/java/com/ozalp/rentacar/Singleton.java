package com.ozalp.rentacar;

import com.ozalp.rentacar.DatabaseOperations.DBData;
import com.ozalp.rentacar.Models.Car;

public class Singleton {
    private DBData dbData;
    private static Singleton singleton;

    private Singleton() {

    }

    public DBData getDbData() {
        return dbData;
    }

    public static Singleton getInstance() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }



}
