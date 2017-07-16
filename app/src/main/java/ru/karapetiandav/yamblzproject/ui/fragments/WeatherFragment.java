package ru.karapetiandav.yamblzproject.ui.fragments;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.karapetiandav.yamblzproject.App;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.job.SyncWeatherJob;
import ru.karapetiandav.yamblzproject.model.WeatherData;
import ru.karapetiandav.yamblzproject.retrofit.WeatherApi;
import ru.karapetiandav.yamblzproject.utils.Utils;

public class WeatherFragment extends Fragment {

    public static final String DATE = "date";
    public static final String TEMP = "temp";
    public static final String HUMIDITY = "humidity";
    public static final String PRESSURE = "pressure";
    public static final String WEATHER_ID = "weather_id";
    private static final String TAG = WeatherFragment.class.getSimpleName();
    @Inject
    Retrofit retrofit;

    @Inject
    SharedPreferences sharedPreferences;

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

        ((App) getActivity().getApplication()).getNetworkComponent().inject(this);
        SyncWeatherJob.schedulePeriodicJob();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        retrofit.create(WeatherApi.class).getWeatherData("Moscow", App.API_KEY).enqueue(
                new Callback<WeatherData>() {
                    @Override
                    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                        WeatherData data = response.body();
                        String date = Utils.convertUnixTimeToString(data.getDt(), getContext());
                        String temp = String.valueOf((int) Math.floor(data.getMain().getTemp() - 273)) + "°";
                        String humidity = String.valueOf(data.getMain().getHumidity()) + "%";
                        String pressure = String.valueOf(data.getMain().getPressure() * 0.750);
                        int weatherId = data.getWeather().get(0).getId();

                        todayDateTextView.setText(date);
                        tempDegreeTextView.setText(temp);
                        pressureTextView.setText(pressure);
                        humidityTextView.setText(humidity);

                        weatherStatusImage.setImageResource(Utils.getIconResourceForWeatherCondition(weatherId));

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(DATE, date);
                        editor.putString(TEMP, temp);
                        editor.putString(HUMIDITY, humidity);
                        editor.putString(PRESSURE, pressure);
                        editor.putInt(WEATHER_ID, weatherId);
                        editor.apply();
                    }

                    @Override
                    public void onFailure(Call<WeatherData> call, Throwable t) {
                        Toast.makeText(getActivity(), "Загружены данные из памяти", Toast.LENGTH_SHORT).show();

                        if (sharedPreferences.contains(DATE)) {
                            String date = sharedPreferences.getString(DATE, "");
                            String temp = sharedPreferences.getString(TEMP, "");
                            String humidity = sharedPreferences.getString(HUMIDITY, "");
                            String pressure = sharedPreferences.getString(PRESSURE, "");
                            int weatherId = sharedPreferences.getInt(WEATHER_ID, 0);

                            todayDateTextView.setText(date);
                            tempDegreeTextView.setText(temp);
                            pressureTextView.setText(pressure);
                            humidityTextView.setText(humidity);

                            weatherStatusImage.setImageResource(Utils.getIconResourceForWeatherCondition(weatherId));
                        }
                    }
                }
        );
    }
}
