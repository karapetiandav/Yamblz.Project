package ru.karapetiandav.yamblzproject.business.weather;


import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.business.weather.mapper.WeatherMapper;
import ru.karapetiandav.yamblzproject.data.repositories.weather.WeatherRepository;
import ru.karapetiandav.yamblzproject.ui.weather.model.WeatherViewModel;

public class WeatherInteractorImpl implements WeatherInteractor {

    private WeatherRepository weatherRepository;
    private WeatherMapper mapper;

    public WeatherInteractorImpl(WeatherRepository weatherRepository, WeatherMapper mapper) {
        this.weatherRepository = weatherRepository;
        this.mapper = mapper;
    }

    @Override
    public Single<WeatherViewModel> getWeather() {
        return weatherRepository.loadWeather().map(mapper::from);
    }

}
