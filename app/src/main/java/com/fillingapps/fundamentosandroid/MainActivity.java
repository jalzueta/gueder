package com.fillingapps.fundamentosandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.weather_image);
        Button changeButton1 = (Button) findViewById(R.id.change_system1_button);
        Button changeButton2 = (Button) findViewById(R.id.change_system2_button);

        changeButton1.setOnClickListener(this);
        changeButton2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_system1_button:
                mImageView.setImageResource(R.drawable.offline_weather);
                break;
            case R.id.change_system2_button:
                mImageView.setImageResource(R.drawable.offline_weather2);
                break;
        }
    }
}
