package ru.karapetiandav.yamblzproject.ui.cities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.ui.cities.view.CitiesFragment;

public class CitiesActivity extends AppCompatActivity {

    private CitiesFragment citiesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        citiesFragment = (CitiesFragment) getFragmentManager()
                .findFragmentByTag(CitiesFragment.TAG);
        if (citiesFragment == null) {
            citiesFragment = CitiesFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, citiesFragment, CitiesFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) destroyFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        destroyFragment();
    }

    private void destroyFragment() {
        getFragmentManager().beginTransaction().remove(citiesFragment).commit();
    }

}
