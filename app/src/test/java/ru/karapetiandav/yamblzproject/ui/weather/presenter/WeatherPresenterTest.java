package ru.karapetiandav.yamblzproject.ui.weather.presenter;


import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import ru.karapetiandav.yamblzproject.business.weather.WeatherInteractor;
import ru.karapetiandav.yamblzproject.ui.weather.view.WeatherView;
import ru.karapetiandav.yamblzproject.ui.weather.model.WeatherViewModel;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulers;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulersTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherPresenterTest {

    private WeatherView weatherView;
    private WeatherPresenter weatherPresenter;
    private WeatherInteractor weatherInteractor;
    private RxSchedulers rxSchedulers;
    private CompositeDisposable compositeDisposable;

    private static final WeatherViewModel WEATHER = new WeatherViewModel(
            1, "12", "500", "1000", "23.05"
    );

    @Before
    public void setUp() {
        weatherView = mock(WeatherView.class);
        weatherInteractor = mock(WeatherInteractor.class);
        rxSchedulers = new RxSchedulersTest();
        compositeDisposable = new CompositeDisposable();
        weatherPresenter = new WeatherPresenter(
                weatherInteractor, compositeDisposable, rxSchedulers);
    }

    @Test
    public void onAttach_loadWeather_success() {
        when(weatherInteractor.getWeather()).thenReturn(Single.fromCallable(() -> WEATHER));
        weatherPresenter.onAttach(weatherView);
        verify(weatherView, only()).showWeather(WEATHER);
        verify(weatherView, never()).showError();
    }

    @Test
    public void onAttach_loadWeather_fail() {
        when(weatherInteractor.getWeather()).thenReturn(Single.error(new Throwable()));
        weatherPresenter.onAttach(weatherView);
        verify(weatherView, never()).showWeather(any());
        verify(weatherView, only()).showError();
    }

    @Test
    public void onDetach_test() {
        when(weatherInteractor.getWeather()).thenReturn(Single.fromCallable(() -> WEATHER));
        weatherPresenter.onAttach(weatherView);
        weatherPresenter.onDetach();
        Assertions.assertThat(compositeDisposable.size()).isEqualTo(0);
    }
}
