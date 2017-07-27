package ru.karapetiandav.yamblzproject.mvp;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.karapetiandav.yamblzproject.App;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.data.prefs.PreferenceHelper;
import ru.karapetiandav.yamblzproject.job.SyncWeatherJob;
import ru.karapetiandav.yamblzproject.model.WeatherData;

public class WeatherPresenter extends IPresenter<WeatherPresenter.WeatherView> {

    public static final String CITY = "Moscow";
    public static final String DATE = "date";
    public static final String TEMP = "temp";
    public static final String HUMIDITY = "humidity";
    public static final String PRESSURE = "pressure";
    public static final String WEATHER_ID = "weather_id";
    private static final String TAG = WeatherPresenter.class.getSimpleName();
    private SharedPreferences sharedPreferences;

    private PreferenceHelper preferenceHelper;

    private Call<WeatherData> call;

    public WeatherPresenter(PreferenceHelper preferenceHelper) {
        sharedPreferences = App.getSharedPreferences();
        SyncWeatherJob.schedulePeriodicJob(sharedPreferences);
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        Log.d(TAG, "onAttach: ");
        makeRequest();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRequest();
    }

    private boolean stopRequest() {
        call.cancel();

        return call.isCanceled();
    }

    @Nullable
    @Override
    protected WeatherView getView() {
        return super.getView();
    }

    public void makeRequest() {
        preferenceHelper.getCity().subscribe(this::handleSuccess);

    }

    private void handleSuccess(CityDataModel city) {
        call = App.getWeatherApi().getWeatherData(String.valueOf(city.getId()), App.API_KEY);
        call.enqueue(
                new Callback<WeatherData>() {
                    @Override
                    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                        WeatherData data = response.body();

                        getView().showWeather(data);
                    }

                    @Override
                    public void onFailure(Call<WeatherData> call, Throwable t) {
                        getView().showDataFromMemoryToast();

                        if (sharedPreferences.contains(DATE)) {
                            String date = sharedPreferences.getString(DATE, "");
                            String temp = sharedPreferences.getString(TEMP, "");
                            String humidity = sharedPreferences.getString(HUMIDITY, "");
                            String pressure = sharedPreferences.getString(PRESSURE, "");
                            int weatherId = sharedPreferences.getInt(WEATHER_ID, 0);

                            getView().showWeather(date, temp, humidity, pressure, weatherId);
                        }
                    }
                }
        );
    }

    public interface WeatherView extends IView {
        void showWeather(WeatherData weatherData);

        void showWeather(String date, String temp, String humidity, String pressure, int weatherId);

        void showDataFromMemoryToast();
    }
}
