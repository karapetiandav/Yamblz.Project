package ru.karapetiandav.yamblzproject.ui.weather;

import android.support.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.karapetiandav.yamblzproject.business.weather.WeatherInteractor;
import ru.karapetiandav.yamblzproject.ui.base.IPresenter;
import ru.karapetiandav.yamblzproject.ui.weather.model.WeatherViewModel;

public class WeatherPresenter extends IPresenter<WeatherView> {

    private WeatherInteractor weatherInteractor;
    private CompositeDisposable compositeDisposable;

    public WeatherPresenter(WeatherInteractor weatherInteractor) {
        this.weatherInteractor = weatherInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        loadWeather();
    }

    @Nullable
    @Override
    protected WeatherView getView() {
        return super.getView();
    }

    private void loadWeather() {
        weatherInteractor.getWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleError);

    }

    private void handleSuccess(WeatherViewModel weatherViewModel) {
        getView().showWeather(weatherViewModel);
    }

    private void handleError(Throwable throwable) {
        getView().showError();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.clear();
    }
}
