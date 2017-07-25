package ru.karapetiandav.yamblzproject.ui.cities.presenter;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.karapetiandav.yamblzproject.business.cities.CitiesInteractor;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;
import ru.karapetiandav.yamblzproject.ui.cities.view.CitiesView;

public class CitiesPresenterImpl implements CitiesPresenter<CitiesView> {

    private CitiesInteractor citiesInteractor;
    private CompositeDisposable compositeDisposable;
    private CitiesPresenterCache cache;
    private CitiesView view;

    private static final int DEBOUNCE_BEFORE_QUERING_DATA = 500;

    public CitiesPresenterImpl(CitiesInteractor citiesInteractor,
                               CompositeDisposable compositeDisposable,
                               CitiesPresenterCache cache) {
        this.citiesInteractor = citiesInteractor;
        this.compositeDisposable = compositeDisposable;
        this.cache = cache;
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
        Disposable disposable = inputChanges.observeOn(AndroidSchedulers.mainThread())
                .doOnNext(ignore -> view.showProgress())
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .debounce(DEBOUNCE_BEFORE_QUERING_DATA, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .flatMap(s -> citiesInteractor.getCitiesMatches(s))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterNext(ignore -> view.hideProgress())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleNext, this::handleError);
        compositeDisposable.add(disposable);
    }

    private void handleNext(@NonNull List<CityViewModel> cities) {
        if (!cities.isEmpty()) {
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
    public void onCityClick(int position) {

    }

    @Override
    public void onDetach() {
        this.view = null;
        compositeDisposable.clear();
    }
}
