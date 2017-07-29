package ru.karapetiandav.yamblzproject.data.repositories.weather;


import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.model.WeatherDataModel;

public interface WeatherRepository {

    Single<WeatherDataModel> loadWeather();

}
