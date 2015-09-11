package com.fillingapps.fundamentosandroid.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Cities {
    public static final String PREF_CITIES = "com.fillingapps.fundamentosandroid.model.Cities.PREF_CITIES";
    // Para el Broadcast Intent (Notificacion)
    public static final String CITY_LIST_CHANGED_ACTION = "com.fillingapps.fundamentosandroid.model.Cities.CITY_LIST_CHANGED_ACTION";

    private static Cities ourInstance;

    private List<City> mCities;
    // Esto es una referencia debil. Asi evitamos ciclos Cities-MainActivity
    private WeakReference<Context> mContext;

    public static Cities getInstance(Context context) {
        // Comprobamos si ya tenemos la instancia
        // Debemos comprobar si no hemos perdido la referencia al contexto
        if (ourInstance == null || ourInstance.mContext.get() == null){
            // Proteccion contra multi-hilo: lo que hay aqui dentro solo lo puede ejecutar un hilo a la vez
            // "PREF_CITIES" es el cerrojo: mejor un objeto estático
            synchronized (PREF_CITIES){
                if (ourInstance == null){
                    // Creamos la instancia a partir de las preferencias
                    ourInstance = new Cities(context);
                } else if (ourInstance.mContext.get() == null){
                    ourInstance.mContext = new WeakReference<Context>(context);
                }
            }
        }
        return ourInstance;
    }

    private Cities(Context context) {
        mCities = new LinkedList<>();

        // Guardamos el contexto para futuros usos
        mContext = new WeakReference<Context>(context);

        // Sacamos las preferencias, para eso hemos creado el contexto antes
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        // Sacamos el conjunto de ciudades como un conjunto de cadenas (default: un conjunto vacio)
        Set<String> cities = prefs.getStringSet(PREF_CITIES, new HashSet<String>());

        // Añadimos las ciudades a la instancia
        for (String city : cities){
            mCities.add(new City(city, new Forecast(30, 15, 20, "Sol con algunas nubes", "ico01")));
        }

//        mCities.add(new City("Pamplona", new Forecast(32, 19, 51, "Sol y nubes", "")));
//        mCities.add(new City("Irún", new Forecast(24, 12, 54, "Solazo", "")));
//        mCities.add(new City("Barcelona", new Forecast(31, 21, 65, "Muchas nubes", "")));
//        mCities.add(new City("Bilbao", new Forecast(23, 13, 76, "Lluvia", "")));
//        mCities.add(new City("Lleida", new Forecast(22, 12, 45, "Sol y lluvia", "")));
//        mCities.add(new City("Huesca", new Forecast(25, 14, 34, "Sol y nubes", "")));
//        mCities.add(new City("Mutilva", new Forecast(27, 24, 54, "Nieve", "")));
//        mCities.add(new City("Ibiza", new Forecast(31, 18, 53, "Totalmente despejado", "")));
//        mCities.add(new City("León", new Forecast(33, 19, 43, "Parcialmente nuboso", "")));
//        mCities.add(new City("Madrid", new Forecast(32, 26, 42, "Musho caló", "")));
    }

    public void save() {
        // Proteccion contra multi-hilo: lo que hay aqui dentro solo lo puede ejecutar un hilo a la vez
        synchronized (PREF_CITIES){
            // Obtenemos la referencia "strong" al weakContext
            Context strongContext = mContext.get();
            if (strongContext != null){
                // Sacamos las preferencias
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(strongContext);

                // Creamos un conjunto de ciudades
                Set<String> citiesSet = new HashSet<>();
                for (City city : mCities){
                    citiesSet.add(city.getName());
                }

                // Las guardamos
                prefs.edit().putStringSet(PREF_CITIES, citiesSet).apply();
            }
        }
    }

    public void addCity(String cityName){
        mCities.add(new City(cityName));
        // Guardamos en SharedPreferences
        save();

        // Lanzamos un Broadcast Intent (Notificacion). El que este suscrito lo recibira
        if (mContext.get() != null){
            Intent broadcast = new Intent(CITY_LIST_CHANGED_ACTION);
            mContext.get().sendBroadcast(broadcast);
        }
    }

    public List<City> getCities() {
        return mCities;
    }
}
