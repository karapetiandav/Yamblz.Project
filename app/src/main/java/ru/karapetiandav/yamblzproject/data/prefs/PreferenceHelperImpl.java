package ru.karapetiandav.yamblzproject.data.prefs;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.data.exceptions.NoCachedWeatherException;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.data.model.WeatherDataModel;

public class PreferenceHelperImpl implements PreferenceHelper {

    private static final String TIME_KEY = "date";
    private static final String TEMP_KEY = "temp";
    private static final String HUMIDITY_KEY = "humidity";
    private static final String PRESSURE_KEY = "pressure";
    private static final String WEATHER_ID_KEY = "weather_id";

    private SharedPreferences sharedPrefs;
    private Resources resources;

    public PreferenceHelperImpl(SharedPreferences sharedPrefs, Resources resources) {
        this.sharedPrefs = sharedPrefs;
        this.resources = resources;
    }

    @Override
    public Completable saveCity(CityDataModel city) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(resources.getString(R.string.city_id_key), city.getId());
        editor.putString(resources.getString(R.string.city_name_key), city.getCityName());
        editor.putString(resources.getString(R.string.country_key), city.getCountryCode());
        editor.apply();
        return Completable.complete();
    }

    @Override
    public Single<CityDataModel> getCity() {
        CityDataModel cityDataModel = new CityDataModel(
                sharedPrefs.getInt(resources.getString(R.string.city_id_key), resources.getInteger(R.integer.default_city_id)),
                sharedPrefs.getString(resources.getString(R.string.city_name_key), resources.getString(R.string.default_city_name)),
                sharedPrefs.getString(resources.getString(R.string.country_key), resources.getString(R.string.default_country))
        );
        return Single.fromCallable(() -> cityDataModel);
    }

    @Override
    public Single<WeatherDataModel> getWeather() {
        if (sharedPrefs.getInt(WEATHER_ID_KEY, 0) == 0) {
            return Single.error(new NoCachedWeatherException());
        }
        int id = sharedPrefs.getInt(WEATHER_ID_KEY, 0);
        float temp = sharedPrefs.getFloat(TEMP_KEY, 0f);
        float pressure = sharedPrefs.getFloat(PRESSURE_KEY, 0f);
        int humidity = sharedPrefs.getInt(HUMIDITY_KEY, 0);
        long time = sharedPrefs.getLong(TIME_KEY, 0L);
        WeatherDataModel weatherDataModel = new WeatherDataModel(id, temp, pressure, humidity, time);
        Log.v("log_tag", "Getting from preferences" + weatherDataModel.toString());
        return Single.fromCallable(() -> weatherDataModel);
    }

    @Override
    public Completable saveWeather(WeatherDataModel dataModel) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(TIME_KEY, dataModel.getTime());
        editor.putFloat(TEMP_KEY, dataModel.getTemp());
        editor.putInt(HUMIDITY_KEY, dataModel.getHumidity());
        editor.putFloat(PRESSURE_KEY, dataModel.getPressure());
        editor.putInt(WEATHER_ID_KEY, dataModel.getWeatherId());
        editor.apply();
        return Completable.complete();
    }

    @Override
    public Completable clearWeatherCache() {
        return saveWeather(WeatherDataModel.getEmpty());
    }
}
