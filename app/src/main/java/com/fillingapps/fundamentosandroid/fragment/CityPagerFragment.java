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

    public static CityPagerFragment newInstance() {
        return new CityPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_citypager, container, false);

        mCities = Cities.getInstance();

        ViewPager pager = (ViewPager) root.findViewById(R.id.view_pager);
        pager.setAdapter(new CityPagerFragmentAdapter(getFragmentManager()));

        // Detectamos el cambio de fragment en el view pager
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        updateCityInfo(pager.getCurrentItem());

        return root;
    }

    protected void updateCityInfo(int position) {
        //Pillamos el Toolbar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // Actualizamos el titulo de la Toolbar
        actionBar.setTitle(mCities.getCities().get(position).getName());
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
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCities.getCities().get(position).getName();
        }
    }
}
