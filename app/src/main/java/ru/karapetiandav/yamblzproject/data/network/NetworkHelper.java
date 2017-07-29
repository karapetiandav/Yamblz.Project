package ru.karapetiandav.yamblzproject.data.network;


import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.network.model.WeatherResponse;

public interface NetworkHelper {

    Single<WeatherResponse> loadWeather(int cityId);
}
