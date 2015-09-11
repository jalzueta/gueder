package com.fillingapps.fundamentosandroid.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fillingapps.fundamentosandroid.R;
import com.fillingapps.fundamentosandroid.model.Cities;
import com.fillingapps.fundamentosandroid.model.City;

public class CityListFragment extends Fragment {

    private CityListListener mListener;

    public static CityListFragment newInstance(){
        return new CityListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_city_list, container, false);

        Cities cities = Cities.getInstance(getActivity());
        ListView list = (ListView) root.findViewById(android.R.id.list);

        final ArrayAdapter <City> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, cities.getCities());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null){
                    mListener.onCitySelected(adapter.getItem(position), position);
                }
            }
        });

        return root;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Asignamos el listener
        mListener = (CityListListener) activity;
    }

    // Cuando se carga el fragment en pantalla
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Asignamos el listener
        mListener = (CityListListener) getActivity();
    }

    // Cuando desaparece el fragment de la pantalla
    @Override
    public void onDetach() {
        super.onDetach();
        // Anulamos el listener
        mListener = null;
    }

    // Interfaz
    public interface CityListListener {
        void onCitySelected (City city, int index);
    }
}

