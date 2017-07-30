package ru.karapetiandav.yamblzproject.data.network.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.karapetiandav.yamblzproject.data.network.model.WeatherResponse;

public interface WeatherApi {
    @GET("/data/2.5/weather")
    Observable<WeatherResponse> getWeatherData(@Query("id") String cityId,
                                               @Query("appid") String apiKey);
}