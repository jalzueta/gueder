package com.fillingapps.fundamentosandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.fragment.CityPagerFragment;

public class CityPagerActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_INDEX = "com.fillingapp.fundamentosandroid.CityPagerActivity.EXTRA_CITY_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Le damos la interfaz
        setContentView(R.layout.activity_city_pager);

        // Asignamos la Toolbar como "ActionBar"
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Esta pantalla siempre tiene boton de volver
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragment) == null) {
            // Saco la info que me pasan con el intent: ciudad pulsada
            int initialCityIndex = getIntent().getIntExtra(EXTRA_CITY_INDEX, 0);
            // Creo un fragmet pasandole el indice de la ciudad que se ha pulsado en la lista de ciudades
            fm.beginTransaction().add(R.id.fragment, CityPagerFragment.newInstance(initialCityIndex)).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Comprobamos si la opcion es la de la flecha de "back"
        if (item.getItemId() == android.R.id.home){
            // Finalizamos la activity
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
