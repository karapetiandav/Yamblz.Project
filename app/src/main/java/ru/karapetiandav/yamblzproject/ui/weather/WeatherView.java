package ru.karapetiandav.yamblzproject.ui.weather;


import ru.karapetiandav.yamblzproject.ui.base.IView;
import ru.karapetiandav.yamblzproject.ui.weather.model.WeatherViewModel;

public interface WeatherView extends IView {
    void showWeather(WeatherViewModel weatherViewModel);
    void showError();
}
