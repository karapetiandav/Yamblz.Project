package ru.karapetiandav.yamblzproject.di.module;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.karapetiandav.yamblzproject.business.weather.WeatherInteractor;
import ru.karapetiandav.yamblzproject.business.weather.WeatherInteractorImpl;
import ru.karapetiandav.yamblzproject.business.weather.mapper.WeatherMapper;
import ru.karapetiandav.yamblzproject.data.network.NetworkHelper;
import ru.karapetiandav.yamblzproject.data.prefs.PreferenceHelper;
import ru.karapetiandav.yamblzproject.data.repositories.weather.WeatherRepository;
import ru.karapetiandav.yamblzproject.data.repositories.weather.WeatherRepositoryImpl;
import ru.karapetiandav.yamblzproject.di.scope.WeatherScope;
import ru.karapetiandav.yamblzproject.ui.weather.presenter.WeatherPresenter;
import ru.karapetiandav.yamblzproject.utils.Utils;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulers;

@Module
public class WeatherModule {


    @Provides
    @WeatherScope
    @NonNull
    WeatherMapper provideWeatherMapper(Utils utils) {
        return new WeatherMapper(utils);
    }

    @Provides
    @WeatherScope
    @NonNull
    WeatherInteractor provideWeatherInteractor(WeatherRepository weatherRepository,
                                               WeatherMapper mapper) {
        return new WeatherInteractorImpl(weatherRepository, mapper);
    }

    @Provides
    @WeatherScope
    @NonNull
    WeatherRepository provideWeatherRepository(PreferenceHelper preferenceHelper,
                                               NetworkHelper networkHelper) {
        return new WeatherRepositoryImpl(preferenceHelper, networkHelper);
    }

    @Provides
    @NonNull
    @WeatherScope
    WeatherPresenter provideWeatherPresenter(WeatherInteractor weatherInteractor,
                                             CompositeDisposable compositeDisposable,
                                             RxSchedulers rxSchedulers) {
        return new WeatherPresenter(weatherInteractor, compositeDisposable, rxSchedulers);
    }
}
