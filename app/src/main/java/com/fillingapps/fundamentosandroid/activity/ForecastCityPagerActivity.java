package com.fillingapps.fundamentosandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.fragment.CityListFragment;
import com.fillingapps.fundamentosandroid.fragment.CityPagerFragment;
import com.fillingapps.fundamentosandroid.model.City;

public class ForecastCityPagerActivity extends AppCompatActivity implements CityListFragment.CityListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast_city_pager);

        // Asignamos la Toolbar como "ActionBar"
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragment) == null){
            fm.beginTransaction().add(R.id.fragment, CityListFragment.newInstance()).commit();
        }
    }

    @Override
    public void onCitySelected(City city, int index) {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fragment, CityPagerFragment.newInstance()).commit();
        Toast.makeText(this, "Toast", Toast.LENGTH_LONG);
    }
}
