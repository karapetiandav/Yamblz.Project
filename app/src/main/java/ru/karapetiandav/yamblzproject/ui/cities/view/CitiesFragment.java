package ru.karapetiandav.yamblzproject.ui.cities.view;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.karapetiandav.yamblzproject.App;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.di.module.CitiesModule;
import ru.karapetiandav.yamblzproject.ui.cities.adapter.CitiesAdapter;
import ru.karapetiandav.yamblzproject.ui.cities.presenter.CitiesPresenter;

public class CitiesFragment extends Fragment implements CitiesView {

    @Inject CitiesAdapter adapter;
    @Inject CitiesPresenter presenter;

    @BindView(R.id.input_city_edittext) EditText inputCityET;
    @BindView(R.id.cities_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.enter_city_name_textview) TextView helloTV;
    @BindView(R.id.no_results_textview) TextView noResultsTV;
    @BindView(R.id.loading_progressbar) ProgressBar progressBar;

    public static CitiesFragment newInstance() {
        CitiesFragment citiesFragment = new CitiesFragment();
        return citiesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        ButterKnife.bind(this, view);
        App.getAppComponent().plusCitiesComponent(new CitiesModule()).inject(this);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter.setOnItemClickListener(position -> presenter.onCityClick(position));
        recyclerView.setAdapter(adapter);

    }
}