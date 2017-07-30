package ru.karapetiandav.yamblzproject.ui.weather.presenter;

import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import ru.karapetiandav.yamblzproject.business.weather.WeatherInteractor;
import ru.karapetiandav.yamblzproject.ui.base.IPresenter;
import ru.karapetiandav.yamblzproject.ui.weather.view.WeatherView;
import ru.karapetiandav.yamblzproject.ui.weather.model.WeatherViewModel;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulers;

public class WeatherPresenter extends IPresenter<WeatherView> {

    private WeatherInteractor weatherInteractor;
    private CompositeDisposable compositeDisposable;
    private RxSchedulers rxSchedulers;

    public WeatherPresenter(WeatherInteractor weatherInteractor,
                            CompositeDisposable compositeDisposable,
                            RxSchedulers rxSchedulers) {
        this.weatherInteractor = weatherInteractor;
        this.compositeDisposable = compositeDisposable;
        this.rxSchedulers = rxSchedulers;
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
        compositeDisposable.add(weatherInteractor.getWeather()
                .subscribeOn(rxSchedulers.getIOScheduler())
                .observeOn(rxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleSuccess, this::handleError));
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
