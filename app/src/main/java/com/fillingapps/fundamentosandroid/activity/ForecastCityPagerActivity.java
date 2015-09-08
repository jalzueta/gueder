package com.fillingapps.fundamentosandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.fragment.CityPagerFragment;

/**
 * Created by javi on 8/9/15.
 */
public class ForecastCityPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast_city_pager);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragment_citypager) == null){
            fm.beginTransaction().add(R.id.fragment_citypager, CityPagerFragment.newInstance()).commit();
        }
    }
}
