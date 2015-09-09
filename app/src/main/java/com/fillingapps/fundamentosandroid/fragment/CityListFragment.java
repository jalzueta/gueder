package com.fillingapps.fundamentosandroid.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.model.Cities;
import com.fillingapps.fundamentosandroid.model.City;


/**
 * Created by javi on 9/9/15.
 */
public class CityListFragment extends Fragment {

    public static CityListFragment newInstance(){
        return new CityListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_city_list, container, false);

        Cities cities = Cities.getInstance();
        ListView list = (ListView) root.findViewById(android.R.id.list);

        ArrayAdapter <City> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, cities.getCities());
        list.setAdapter(adapter);

        return root;
    }
}

