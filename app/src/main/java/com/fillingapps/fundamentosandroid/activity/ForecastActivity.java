package com.fillingapps.fundamentosandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mMaxTemp = (TextView) findViewById(R.id.max_temp);
        mMinTemp = (TextView) findViewById(R.id.min_temp);
        mHumidity = (TextView) findViewById(R.id.humidity);
        mDescription = (TextView) findViewById(R.id.forecast_description);
        mIcon = (ImageView) findViewById(R.id.forecast_image);

        setForecast(new Forecast(30,20,65,"Totalmente despejado",""));

    }

    public void setForecast(Forecast forecast){
        mForecast = forecast;

        mMaxTemp.setText(String.format(getString(R.string.max_temp_parameter), forecast.getMaxTemp()));
        mMinTemp.setText(String.format(getString(R.string.min_temp_parameter), forecast.getMinTemp()));
        mHumidity.setText(String.format(getString(R.string.humidity_parameter), forecast.getHumidity()));
        mDescription.setText(String.format(forecast.getDescription()));
    }


}
