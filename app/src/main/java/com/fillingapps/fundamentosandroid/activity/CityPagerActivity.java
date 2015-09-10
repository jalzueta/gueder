package com.fillingapps.fundamentosandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
            // Finalizamos la activity y que se muestre lo que haya debajo
            //finish();
            // Esto es lo mismo, pero si hemos definido su activity previa en navegacion en el manifest.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Similar a un finish(), pero con una particularidad:
        // Lo que hace es siempre mostrar su padre definido en el Manifest, independientemente de desde que Activity haya sido lanzada
        NavUtils.navigateUpFromSameTask(this);
    }
}
