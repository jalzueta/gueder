package com.fillingapps.fundamentosandroid.model;

import java.io.Serializable;

public class City implements Serializable{

    private String mName;
    private Forecast mForecast;

    public City(String name, Forecast forecast) {
        mName = name;
        mForecast = forecast;
    }

    public City(String name) {
        this(name, new Forecast(30, 15, 20, "Sol con algunas nubes", "ico01"));
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Forecast getForecast() {
        return mForecast;
    }

    public void setForecast(Forecast forecast) {
        mForecast = forecast;
    }

    @Override
    public String toString() {
        return getName();
    }
}
