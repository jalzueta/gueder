package com.fillingapps.fundamentosandroid.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.activity.SettingsActivity;
import com.fillingapps.fundamentosandroid.model.City;
import com.fillingapps.fundamentosandroid.model.Forecast;

/**
 * Created by javi on 8/9/15.
 */
public class ForecastFragment extends Fragment {

    private static final String ARG_CITY = "city";

    private Forecast mForecast;

    private TextView mCity;
    private ImageView mIcon;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;

    private int mCurrentMetrics;

    protected static float toFarenheit(float celsius){
        return (celsius * 1.8f) + 32;
    }
    public static Fragment newInstance(City city) {
        ForecastFragment fragment = new ForecastFragment();

        // Le pasamos un parametro al fragment
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_CITY, city);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Con esto habilitamos el menu dentro del fragment. La Activity lo incorporará
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        mCity = (TextView) root.findViewById(R.id.city);
        mMaxTemp = (TextView) root.findViewById(R.id.max_temp);
        mMinTemp = (TextView) root.findViewById(R.id.min_temp);
        mHumidity = (TextView) root.findViewById(R.id.humidity);
        mDescription = (TextView) root.findViewById(R.id.forecast_description);
        mIcon = (ImageView) root.findViewById(R.id.forecast_image);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String stringMetrics = pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS));

        mCurrentMetrics = Integer.valueOf(stringMetrics);

        City city = (City) getArguments().getSerializable(ARG_CITY);
        setForecast(city.getForecast());
        mCity.setText(city.getName());

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_forecast, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Comprobamos que la opción de menú pulsada es la de ajustes
        if(item.getItemId() == R.id.menu_settings){
            // Si se pulsa el botón de ajustes del menú, lanzamos la pantalla de ajustes
            // Lanzamos la pantalla de ajustes sin esperar resultado
            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();


        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String stringMetrics = pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS));

        int metrics = Integer.valueOf(stringMetrics);

        if (metrics != mCurrentMetrics){
            final int previousMetrics = mCurrentMetrics;
            mCurrentMetrics = metrics;
            setForecast(mForecast);

            // getView(): metodo para acceder a la raiz de mis vistas
            Snackbar.make(getView().findViewById(android.R.id.content), R.string.updated_preferences, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pref.edit().putString(getString(R.string.metric_selection), String.valueOf(previousMetrics)).apply();
                            mCurrentMetrics = previousMetrics;
                            setForecast(mForecast);
                        }
                    })
                    .show();
        }
    }

    public void setForecast(Forecast forecast){
        mForecast = forecast;

        float maxTemp = mCurrentMetrics == SettingsActivity.PREF_CELSIUS ? forecast.getMaxTemp() : toFarenheit(forecast.getMaxTemp());
        float minTemp = mCurrentMetrics == SettingsActivity.PREF_CELSIUS ? forecast.getMinTemp() : toFarenheit(forecast.getMinTemp());

        String metricString;
        if (mCurrentMetrics == SettingsActivity.PREF_CELSIUS){
            metricString = "ºC";
        }
        else{
            metricString = "ºF";
        }

        mMaxTemp.setText(String.format(getString(R.string.max_temp_parameter), maxTemp, metricString));
        mMinTemp.setText(String.format(getString(R.string.min_temp_parameter), minTemp, metricString));
        mHumidity.setText(String.format(getString(R.string.humidity_parameter), forecast.getHumidity()));
        mDescription.setText(forecast.getDescription());
    }

}
