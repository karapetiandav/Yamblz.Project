package ru.karapetiandav.yamblzproject.business.weather;


import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import ru.karapetiandav.yamblzproject.business.weather.mapper.WeatherMapper;
import ru.karapetiandav.yamblzproject.data.model.WeatherDataModel;
import ru.karapetiandav.yamblzproject.data.repositories.weather.WeatherRepository;
import ru.karapetiandav.yamblzproject.ui.weather.model.WeatherViewModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherInteractorTest {

    private WeatherInteractor weatherInteractor;
    private WeatherRepository weatherRepository;
    private WeatherMapper weatherMapper;

    private WeatherDataModel WEATHER_DATA = new WeatherDataModel(1, 5f, 5f, 5, 100101L);
    private static final WeatherViewModel WEATHER_VIEW = new WeatherViewModel(
            1, "12", "500", "1000", "23.05"
    );

    @Before
    public void setUp() {
        weatherRepository = mock(WeatherRepository.class);
        weatherMapper = mock(WeatherMapper.class);
        weatherInteractor = new WeatherInteractorImpl(weatherRepository, weatherMapper);
    }

    @Test
    public void getWeather_success() {
        when(weatherRepository.loadWeather()).thenReturn(Single.fromCallable(() -> WEATHER_DATA));
        when(weatherMapper.from(WEATHER_DATA)).thenReturn(WEATHER_VIEW);
        TestObserver<WeatherViewModel> observer = TestObserver.create();
        weatherInteractor.getWeather().subscribe(observer);
        observer.awaitTerminalEvent();
        observer.assertNoErrors();
        observer.assertComplete();
    }

    @Test
    public void getWeather_error() {
        Throwable throwable = new Throwable();
        when(weatherRepository.loadWeather()).thenReturn(Single.error(throwable));
        TestObserver<WeatherViewModel> observer = TestObserver.create();
        weatherInteractor.getWeather().subscribe(observer);
        observer.awaitTerminalEvent();
        observer.assertError(throwable);
        observer.assertNotComplete();
    }
}
