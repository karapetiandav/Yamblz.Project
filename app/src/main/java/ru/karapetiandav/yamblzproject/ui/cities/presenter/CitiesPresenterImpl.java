package ru.karapetiandav.yamblzproject.ui.cities.presenter;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.karapetiandav.yamblzproject.business.cities.interactor.CitiesInteractor;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;
import ru.karapetiandav.yamblzproject.ui.cities.view.CitiesView;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulers;

public class CitiesPresenterImpl implements CitiesPresenter<CitiesView> {

    private CitiesInteractor citiesInteractor;
    private CompositeDisposable compositeDisposable;
    private CitiesPresenterCache cache;
    private RxSchedulers schedulers;

    private CitiesView view;

    public CitiesPresenterImpl(CitiesInteractor citiesInteractor,
                               CompositeDisposable compositeDisposable,
                               CitiesPresenterCache cache,
                               RxSchedulers schedulers) {
        this.citiesInteractor = citiesInteractor;
        this.compositeDisposable = compositeDisposable;
        this.cache = cache;
        this.schedulers = schedulers;
    }

    @Override
    public void onAttach(CitiesView view) {
        this.view = view;
        if (cache.isCacheExist()) {
            List<CityViewModel> cities = cache.getCities();
            if (cities.size() == 0) {
                view.showNoMatches();
            } else {
                view.showCities(cache.getCities());
            }
        }
    }

    @Override
    public void observeInputChanges(Observable<CharSequence> inputChanges) {
        Disposable disposable = inputChanges
                .map(CharSequence::toString)
                .observeOn(schedulers.getMainThreadScheduler())
                .doOnNext(this::handleText)
                .filter(s -> !s.isEmpty())
                .observeOn(schedulers.getIOScheduler())
                .flatMap(citiesInteractor::getCitiesMatches)
                .subscribeOn(schedulers.getIOScheduler())
                .observeOn(schedulers.getMainThreadScheduler())
                .subscribe(this::handleNext, this::handleError);
        compositeDisposable.add(disposable);
    }

    private void handleText(String text) {
        if (text.equals("")) {
            view.showCities(new ArrayList<>());
            return;
        }
        cache.setLastText(text);
    }

    private void handleNext(@NonNull List<CityViewModel> cities) {
        if (!cities.isEmpty() && !cache.getLastText().equals("")) {
            view.showCities(cities);
        } else {
            view.showNoMatches();
        }
        cache.updateData(cities);
    }

    private void handleError(Throwable throwable) {
        view.showError();
    }

    @Override
    public void onCityClick(@NonNull CityViewModel city) {
        citiesInteractor.saveCity(city).subscribe(view::close);
    }

    @Override
    public void onDetach() {
        this.view = null;
        compositeDisposable.clear();
    }
}
