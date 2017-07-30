package ru.karapetiandav.yamblzproject.ui.weather.view;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.karapetiandav.yamblzproject.App;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.di.module.WeatherModule;
import ru.karapetiandav.yamblzproject.ui.weather.model.WeatherViewModel;
import ru.karapetiandav.yamblzproject.ui.weather.presenter.WeatherPresenter;

import static ru.karapetiandav.yamblzproject.R.id.humidity;
import static ru.karapetiandav.yamblzproject.R.id.pressure;

public class WeatherFragment extends Fragment implements WeatherView {

    private static final String HOLDER_TAG = "weather_presenter";

    @Inject
    WeatherPresenter weatherPresenter;

    @BindView(R.id.temp_degree) TextView tempDegreeTextView;
    @BindView(R.id.today_date) TextView todayDateTextView;
    @BindView(pressure) TextView pressureTextView;
    @BindView(humidity) TextView humidityTextView;
    @BindView(R.id.weather_status_image) ImageView weatherStatusImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        App.getAppComponent().plusWeatherComponent(new WeatherModule()).inject(this);
        weatherPresenter.onAttach(this);
        return view;
    }

    @Override
    public void showWeather(WeatherViewModel weatherViewModel) {
        todayDateTextView.setText(weatherViewModel.getDate());
        tempDegreeTextView.setText(weatherViewModel.getTemp());
        pressureTextView.setText(weatherViewModel.getPressure());
        humidityTextView.setText(weatherViewModel.getHumidity());
        weatherStatusImage.setImageResource(weatherViewModel.getImageResourceId());
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), getString(R.string.error_no_data), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        weatherPresenter.onDetach();
    }
}