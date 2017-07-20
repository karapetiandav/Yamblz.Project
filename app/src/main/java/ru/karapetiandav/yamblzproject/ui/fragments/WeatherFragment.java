package ru.karapetiandav.yamblzproject.ui.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.model.WeatherData;
import ru.karapetiandav.yamblzproject.mvp.HolderFragment;
import ru.karapetiandav.yamblzproject.mvp.WeatherPresenter;
import ru.karapetiandav.yamblzproject.utils.Utils;

public class WeatherFragment extends Fragment implements WeatherPresenter.WeatherView {

    private static final String HOLDER_TAG = "weather_presenter";

    public WeatherPresenter weatherPresenter;

    @BindView(R.id.temp_degree)
    TextView tempDegreeTextView;

    @BindView(R.id.today_date)
    TextView todayDateTextView;

    @BindView(R.id.pressure)
    TextView pressureTextView;

    @BindView(R.id.humidity)
    TextView humidityTextView;

    @BindView(R.id.weather_status_image)
    ImageView weatherStatusImage;

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HolderFragment<WeatherPresenter> holderFragment =
                (HolderFragment<WeatherPresenter>) getFragmentManager().findFragmentByTag(HOLDER_TAG);
        if (holderFragment == null) {
            holderFragment = new HolderFragment<>();
            getFragmentManager()
                    .beginTransaction()
                    .add(new HolderFragment<WeatherPresenter>(), HOLDER_TAG)
                    .commit();
            weatherPresenter = holderFragment.getPresenter();
            if (weatherPresenter == null) {
                weatherPresenter = new WeatherPresenter();
                holderFragment.setPresenter(weatherPresenter);
            }
            weatherPresenter.onAttach(this);
        }

        weatherPresenter.makeRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void showWeather(WeatherData data) {
        String date = Utils.convertUnixTimeToString(data.getDt(), getActivity());
        String temp = Utils.formatTemperature(getActivity(), data.getMain().getTemp());
        String humidity = Utils.formatHumidity(getActivity(), data.getMain().getHumidity());
        String pressure = Utils.formatPressure(getActivity(), data.getMain().getPressure());
        int weatherId = data.getWeather().get(0).getId();

        todayDateTextView.setText(date);
        tempDegreeTextView.setText(temp);
        pressureTextView.setText(pressure);
        humidityTextView.setText(humidity);

        weatherStatusImage.setImageResource(Utils.getIconResourceForWeatherCondition(weatherId));
    }

    @Override
    public void showWeather(String date, String temp, String humidity, String pressure, int weatherId) {
        todayDateTextView.setText(date);
        tempDegreeTextView.setText(temp);
        pressureTextView.setText(pressure);
        humidityTextView.setText(humidity);

        weatherStatusImage.setImageResource(Utils.getIconResourceForWeatherCondition(weatherId));
    }

    @Override
    public void showDataFromMemoryToast() {
        Toast.makeText(getActivity(), "Загружены данные из памяти", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        weatherPresenter.onDetach();
    }
}