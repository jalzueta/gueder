package com.fillingapps.fundamentosandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.fillingapps.fundamentosandroid.R;

public class ForecastActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mImageView = (ImageView) findViewById(R.id.weather_image);
        final ToggleButton changeButton1 = (ToggleButton) findViewById(R.id.change_system_button);

        changeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setImageResource(changeButton1.isChecked()?R.drawable.offline_weather : R.drawable.offline_weather2);
            }
        });

    }

    
}
