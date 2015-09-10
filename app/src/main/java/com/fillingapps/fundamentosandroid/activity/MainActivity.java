package com.fillingapps.fundamentosandroid.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.fragment.CityListFragment;
import com.fillingapps.fundamentosandroid.model.City;

public class MainActivity extends AppCompatActivity implements CityListFragment.CityListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
        // lanzamos la activity del viewPager
        Intent cityPagerIntent = new Intent(this, CityPagerActivity.class);
        cityPagerIntent.putExtra(CityPagerActivity.EXTRA_CITY_INDEX, index);
        startActivity(cityPagerIntent);
    }
}
