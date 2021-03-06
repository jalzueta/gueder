package com.fillingapps.fundamentosandroid.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.model.Cities;

public class CityPagerFragment extends Fragment{

    private CityBroadcastReceiver mBroadcastReceiver;

    // Clave del diccionario de SharedPreferences
    public static final String PREF_LAST_CITY = "com.fillingapps.fundamentosandroid.fragment.CityPagerFragment.PREF_LAST_CITY";

    // Esta es la clave del argumento que nos pasan a este fragment
    private static final String ARG_CITY_INDEX = "cityIndex";

    private Cities mCities;
    private ViewPager mPager;
    private int mInitialIndex; //Indice de la ciudad inicial a mostrar

    public static CityPagerFragment newInstance(int initialCityIndex) {
        //Creamos el fragment
        CityPagerFragment fragment = new CityPagerFragment();

        // Nos creamos los argumentos y los empaquetamos
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_CITY_INDEX, initialCityIndex);

        //Asignamos los argumentos al fragment
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Con esto habilitamos el menu dentro del fragment. La Activity lo incorporará
        setHasOptionsMenu(true);

        // Vemos si se reciben argumentos
        if (getArguments() != null){
            mInitialIndex = getArguments().getInt(ARG_CITY_INDEX);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_citypager, container, false);

        mCities = Cities.getInstance(getActivity());

        mPager = (ViewPager) root.findViewById(R.id.view_pager);
        mPager.setAdapter(new CityPagerFragmentAdapter(getFragmentManager()));

        // Detectamos el cambio de fragment en el view pager
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateCityInfo(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Cargamos el titulo de Toolbar al inicio del fragment
        goToCity(mInitialIndex);

        mBroadcastReceiver = new CityBroadcastReceiver(mPager.getAdapter());
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(Cities.CITY_LIST_CHANGED_ACTION));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Me desuscribo de todas las notificaciones broadcast
        getActivity().unregisterReceiver(mBroadcastReceiver);
        mBroadcastReceiver = null;
    }

    // Metodos de navegacion por las cities

    protected void updateCityInfo() {
        updateCityInfo(mPager.getCurrentItem());
    }

    protected void updateCityInfo(int position) {
        //Pillamos el Toolbar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // Actualizamos el titulo de la Toolbar
        if (actionBar != null && mCities.getCities().size() > 0){
            actionBar.setTitle(mCities.getCities().get(position).getName());
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.edit().putInt(PREF_LAST_CITY, position).apply();
    }

    public void goToCity(int index) {
        mPager.setCurrentItem(index);
        updateCityInfo(index);
    }

    // Metodos de menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Le decimos que layout corresponde al menu
        inflater.inflate(R.menu.citypager, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.next) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            return true;
        }
        else if (item.getItemId() == R.id.previous){
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            return true;
        }

        updateCityInfo();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // Aqui activamos/desactivamos las opciones de menu
        if (mPager != null) {
            // Obtenemos la referencia a los botones
            MenuItem menuPrevious = menu.findItem(R.id.previous);
            MenuItem menuNext = menu.findItem(R.id.next);

            menuPrevious.setEnabled(mPager.getCurrentItem() > 0);
            menuNext.setEnabled(mPager.getCurrentItem() < mCities.getCities().size() - 1);
        }
    }

    protected class CityPagerFragmentAdapter extends FragmentPagerAdapter {

        private Cities mCities;

        public CityPagerFragmentAdapter(FragmentManager fm) {

            super(fm);
            mCities = Cities.getInstance(getActivity());
        }

        @Override
        public Fragment getItem(int i) {
            return ForecastFragment.newInstance(mCities.getCities().get(i));
        }

        @Override
        public int getCount() {
            return mCities.getCities().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCities.getCities().get(position).getName();
        }
    }

    // Broadcast: esta clase se va a enterar de cuándo ha cambiado el modelo Cities
    private class CityBroadcastReceiver extends BroadcastReceiver {
        private PagerAdapter mAdapter;

        public CityBroadcastReceiver (PagerAdapter adapter) {
            super();
            mAdapter = adapter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
