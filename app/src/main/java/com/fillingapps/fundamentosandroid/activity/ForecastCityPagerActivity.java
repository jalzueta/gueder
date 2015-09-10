package com.fillingapps.fundamentosandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
        // Reemplazamos un fragment por otro y aañadimos el nuevo a la pila "stack"
        fm.beginTransaction().replace(R.id.fragment, CityPagerFragment.newInstance(index)).addToBackStack(null).commit();

        // Ponemos la flecha de "back" para volver atras
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        // Comprobamos si hay elementos en la pila pendientes de desapilar
        if (fm.getBackStackEntryCount() > 0) {
            if (fm.getBackStackEntryCount() == 1 && getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            fm.popBackStack();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            // Reemplazamos el fragment por el de la lista de ciudades
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() == 1 && getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            fm.popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
