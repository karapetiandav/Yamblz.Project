package ru.karapetiandav.yamblzproject.business.weather;


import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.ui.weather.model.WeatherViewModel;

public interface WeatherInteractor {

    Single<WeatherViewModel> getWeather();

}
