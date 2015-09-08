package com.fillingapps.fundamentosandroid.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javi on 9/9/15.
 */
public class Cities {
    private static Cities ourInstance = new Cities();

    private List<City> mCities;

    public static Cities getInstance() {
        return ourInstance;
    }

    private Cities() {
        mCities = new LinkedList<>();
        mCities.add(new City("Pamplona", new Forecast(32, 19, 51, "Sol y nubes", "")));
        mCities.add(new City("Irún", new Forecast(24, 12, 54, "Solazo", "")));
        mCities.add(new City("Barcelona", new Forecast(31, 21, 65, "Muchas nubes", "")));
        mCities.add(new City("Bilbao", new Forecast(23, 13, 76, "Lluvia", "")));
        mCities.add(new City("Lleida", new Forecast(22, 12, 45, "Sol y lluvia", "")));
        mCities.add(new City("Huesca", new Forecast(25, 14, 34, "Sol y nubes", "")));
        mCities.add(new City("Mutilva", new Forecast(27, 24, 54, "Nieve", "")));
        mCities.add(new City("Ibiza", new Forecast(31, 18, 53, "Totalmente despejado", "")));
        mCities.add(new City("León", new Forecast(33, 19, 43, "Parcialmente nuboso", "")));
        mCities.add(new City("Madrid", new Forecast(32, 26, 42, "Musho caló", "")));
    }

    public List<City> getCities() {
        return mCities;
    }
}
