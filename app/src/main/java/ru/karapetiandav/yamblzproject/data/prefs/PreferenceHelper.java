package ru.karapetiandav.yamblzproject.data.prefs;


import io.reactivex.Completable;
import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.data.model.WeatherDataModel;

public interface PreferenceHelper {

    Completable saveCity(CityDataModel city);
    Single<CityDataModel> getCity();
    Single<WeatherDataModel> getWeather();
    Completable saveWeather(WeatherDataModel dataModel);
    Completable clearWeatherCache();

}
