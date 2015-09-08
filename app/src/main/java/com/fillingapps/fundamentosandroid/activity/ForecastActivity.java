package com.fillingapps.fundamentosandroid.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.model.Forecast;

public class ForecastActivity extends AppCompatActivity {

    private Forecast mForecast;

    private ImageView mIcon;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;

    private int mCurrentMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mMaxTemp = (TextView) findViewById(R.id.max_temp);
        mMinTemp = (TextView) findViewById(R.id.min_temp);
        mHumidity = (TextView) findViewById(R.id.humidity);
        mDescription = (TextView) findViewById(R.id.forecast_description);
        mIcon = (ImageView) findViewById(R.id.forecast_image);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String stringMetrics = pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS));

        mCurrentMetrics = Integer.valueOf(stringMetrics);

        setForecast(new Forecast(30, 20, 65, "Totalmente despejado", ""));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Creamos menú a partir del fichero xml forecast
        getMenuInflater().inflate(R.menu.menu_forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Comprobamos que la opción de menú pulsada es la de ajustes
        if(item.getItemId() == R.id.menu_settings){
            // Si se pulsa el botón de ajustes del menú, lanzamos la pantalla de ajustes
            // Lanzamos la pantalla de ajustes sin esperar resultado
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String stringMetrics = pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS));

        int metrics = Integer.valueOf(stringMetrics);
        if (metrics != mCurrentMetrics){
            mCurrentMetrics = metrics;
            setForecast(mForecast);
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
        mDescription.setText(String.format(forecast.getDescription()));
    }

    protected static float toFarenheit(float celsius){
        return (celsius * 1.8f) + 32;
    }
}
