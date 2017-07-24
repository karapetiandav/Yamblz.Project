package ru.karapetiandav.yamblzproject.ui.cities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.ui.cities.view.CitiesFragment;

public class CitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, CitiesFragment.newInstance())
                .commit();
    }
}
