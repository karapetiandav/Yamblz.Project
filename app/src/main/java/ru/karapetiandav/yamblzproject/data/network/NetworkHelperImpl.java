package ru.karapetiandav.yamblzproject.data.network;


import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.network.model.WeatherResponse;
import ru.karapetiandav.yamblzproject.data.network.api.WeatherApi;
import ru.karapetiandav.yamblzproject.di.qualifiers.ApiKey;

public class NetworkHelperImpl implements NetworkHelper {

    private WeatherApi weatherApi;
    private String apiKey;

    public NetworkHelperImpl(WeatherApi weatherApi, @ApiKey String apiKey) {
        this.weatherApi = weatherApi;
        this.apiKey = apiKey;
    }

    @Override
    public Single<WeatherResponse> loadWeather(int cityId) {
        return weatherApi.getWeatherData(String.valueOf(cityId), apiKey).singleOrError();
    }
}
