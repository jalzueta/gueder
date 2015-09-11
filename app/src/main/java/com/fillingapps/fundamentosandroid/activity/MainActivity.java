package com.fillingapps.fundamentosandroid.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.fragment.CityListFragment;
import com.fillingapps.fundamentosandroid.fragment.CityPagerFragment;
import com.fillingapps.fundamentosandroid.model.Cities;
import com.fillingapps.fundamentosandroid.model.City;

public class MainActivity extends AppCompatActivity implements CityListFragment.CityListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Codigo para ver caracteristicas del dispositivo
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float density = metrics.density;
        int dpWidth = (int) (width / density);
        int dpHeight = (int) (height / density);

        // Asignamos la Toolbar como "ActionBar"
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();
        // Preguntamos si hay hueco para los fragments
        if (findViewById(R.id.city_list) != null){
            // Hay hueco, preguntamos si el fragment city_list ya lo teniamos o hay que crearlo
            if (fm.findFragmentById(R.id.city_list) == null){
                fm.beginTransaction().add(R.id.city_list, CityListFragment.newInstance()).commit();
            }
        }
        // Preguntamos si hay hueco para los fragments
        if (findViewById(R.id.city_pager) != null){
            // Hay hueco, preguntamos si el fragment city_list ya lo teniamos o hay que crearlo
            if (fm.findFragmentById(R.id.city_pager) == null){
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                int lastCityInde = prefs.getInt(CityPagerFragment.PREF_LAST_CITY, 0);
                fm.beginTransaction().add(R.id.city_pager, CityPagerFragment.newInstance(lastCityInde)).commit();
            }
        }

        FloatingActionButton addCityButton = (FloatingActionButton) findViewById(R.id.add_city_button);
        // Puede que no exista en todas las interfaces (tablet, movil...)
        if (addCityButton != null){
            addCityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cities cities = Cities.getInstance(MainActivity.this);
                    cities.addCity(String.format("Ciudad %d", cities.getCities().size() + 1));
                }
            });
        }
    }

    @Override
    public void onCitySelected(City city, int index) {

        // Comprobamos si hay un CityPager en pantalla: miro a ver si existe el hueco
        if (findViewById(R.id.city_pager) != null){
            // Hay hueco para el cityPager
            FragmentManager fm = getFragmentManager();
            CityPagerFragment cityPagerFragment = (CityPagerFragment) fm.findFragmentById(R.id.city_pager);

            // Cambiamos el sub-fragment del cityPager
            cityPagerFragment.goToCity(index);

        }else{
            // lanzamos la activity del viewPager
            Intent cityPagerIntent = new Intent(this, CityPagerActivity.class);
            cityPagerIntent.putExtra(CityPagerActivity.EXTRA_CITY_INDEX, index);
            startActivity(cityPagerIntent);
        }
    }
}
