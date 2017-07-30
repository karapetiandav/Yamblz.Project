package ru.karapetiandav.yamblzproject.data.repositories.weather;


import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.model.WeatherDataModel;
import ru.karapetiandav.yamblzproject.data.network.NetworkHelper;
import ru.karapetiandav.yamblzproject.data.prefs.PreferenceHelper;

public class WeatherRepositoryImpl implements WeatherRepository {

    private PreferenceHelper preferenceHelper;
    private NetworkHelper networkHelper;

    public WeatherRepositoryImpl(PreferenceHelper preferenceHelper, NetworkHelper networkHelper) {
        this.preferenceHelper = preferenceHelper;
        this.networkHelper = networkHelper;
    }

    @Override
    public Single<WeatherDataModel> loadWeather() {
        return loadFromNetwork().onErrorResumeNext(loadCachedWeather());
    }

    private Single<WeatherDataModel> loadFromNetwork() {
        return preferenceHelper.getCity()
                .flatMap(city -> networkHelper.loadWeather(city.getId()))
                .map(WeatherDataModel::valueOf)
                .doOnSuccess(dataModel -> preferenceHelper.saveWeather(dataModel).subscribe());
    }

    private Single<WeatherDataModel> loadCachedWeather() {
        return preferenceHelper.getWeather();
    }
}
