package ru.karapetiandav.yamblzproject.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.karapetiandav.yamblzproject.model.WeatherData;

public interface WeatherApi {
    @GET("/data/2.5/weather")
    Call<WeatherData> getWeatherData(@Query("q") String city, @Query("appid") String apiKey);
}