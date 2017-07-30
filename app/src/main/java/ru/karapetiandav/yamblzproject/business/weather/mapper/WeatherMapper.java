package ru.karapetiandav.yamblzproject.business.weather.mapper;


import android.support.annotation.NonNull;

import ru.karapetiandav.yamblzproject.data.model.WeatherDataModel;
import ru.karapetiandav.yamblzproject.ui.weather.model.WeatherViewModel;
import ru.karapetiandav.yamblzproject.utils.Utils;

public class WeatherMapper {

    private Utils utils;

    public WeatherMapper(Utils utils) {
        this.utils = utils;
    }

    @NonNull
    public WeatherViewModel from(@NonNull WeatherDataModel weatherData)
            throws IllegalArgumentException {
        if (weatherData.getTemp() == 0f && weatherData.getPressure() == 0){
            throw new IllegalArgumentException("Converting null weather data");
        }
        int drawableId = utils.getIconResourceForWeatherCondition(weatherData.getWeatherId());
        String temp = utils.formatTemperature(weatherData.getTemp());
        String pressure = utils.formatPressure(weatherData.getPressure());
        String humidity = utils.formatHumidity(weatherData.getHumidity());
        String time = utils.convertUnixTimeToString(weatherData.getTime());
        return new WeatherViewModel(drawableId, temp, pressure, humidity, time);
    }

}
