package com.fillingapps.fundamentosandroid.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.model.Cities;

/**
 * Created by javi on 8/9/15.
 */
public class CityPagerFragment extends Fragment{

    private Cities mCities;
    private ViewPager mPager;

    public static CityPagerFragment newInstance() {
        return new CityPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Con esto habilitamos el menu dentro del fragment. La Activity lo incorporará
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_citypager, container, false);

        mCities = Cities.getInstance();

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
        updateCityInfo(mPager.getCurrentItem());

        return root;
    }

    protected void updateCityInfo() {
        updateCityInfo(mPager.getCurrentItem());
    }

    protected void updateCityInfo(int position) {
        //Pillamos el Toolbar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // Actualizamos el titulo de la Toolbar
        if (actionBar != null){
            actionBar.setTitle(mCities.getCities().get(position).getName());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Le decimos que layout corresponde al menu
        inflater.inflate(R.menu.citypager, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.next) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }
        else{
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
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
            mCities = Cities.getInstance();
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
}
