package ru.karapetiandav.yamblzproject.data.repositories.weather;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.data.model.WeatherDataModel;
import ru.karapetiandav.yamblzproject.data.network.NetworkHelper;
import ru.karapetiandav.yamblzproject.data.network.model.Main;
import ru.karapetiandav.yamblzproject.data.network.model.Weather;
import ru.karapetiandav.yamblzproject.data.network.model.WeatherResponse;
import ru.karapetiandav.yamblzproject.data.prefs.PreferenceHelper;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherRepositoryTest {

    private WeatherRepository weatherRepository;
    private NetworkHelper networkHelper;
    private PreferenceHelper preferenceHelper;
    private static final CityDataModel CITY = new CityDataModel(10, "Moscow", "RU");
    private static final WeatherDataModel WEATHER = new WeatherDataModel(11, 1f, 2f, 45, 677L);
    private WeatherResponse weatherResponse;

    @Before
    public void setUp() {
        weatherResponse = initWeatherResponse();
        networkHelper = mock(NetworkHelper.class);
        preferenceHelper = mock(PreferenceHelper.class);
        weatherRepository = new WeatherRepositoryImpl(preferenceHelper, networkHelper);
    }

    @Test
    public void loadWeather_success() {
        when(preferenceHelper.getCity())
                .thenReturn(Single.fromCallable(() -> CITY));
        when(networkHelper.loadWeather(any(Integer.class)))
                .thenReturn(Single.fromCallable(() -> weatherResponse));
        when(preferenceHelper.getWeather()).thenReturn(Single.fromCallable(() -> WEATHER));
        when(preferenceHelper.saveCity(any(CityDataModel.class))).thenReturn(Completable.complete());
        weatherRepository.loadWeather().test().assertNoErrors().assertComplete();
    }

    private WeatherResponse initWeatherResponse() {
        Main main = new Main();
        main.setPressure(1f);
        main.setHumidity(67);
        main.setTemp(6f);
        Weather weather = new Weather();
        weather.setId(123);
        WeatherResponse weatherResponse = new WeatherResponse();
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        weatherResponse.setWeather(weatherList);
        weatherResponse.setMain(main);
        return weatherResponse;
    }
}
