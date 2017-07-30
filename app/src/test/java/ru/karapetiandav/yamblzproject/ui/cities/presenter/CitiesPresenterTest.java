package ru.karapetiandav.yamblzproject.ui.cities.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import ru.karapetiandav.yamblzproject.business.cities.interactor.CitiesInteractor;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;
import ru.karapetiandav.yamblzproject.ui.cities.view.CitiesView;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulers;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulersTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CitiesPresenterTest {

    private CitiesPresenter<CitiesView> citiesPresenter;
    private CitiesInteractor citiesInteractor;
    private CitiesView citiesView;
    private CitiesPresenterCache citiesPresenterCache;
    private CompositeDisposable compositeDisposable;
    private RxSchedulers rxSchedulers;
    private PublishSubject<CharSequence> inputCityTextListener;

    private final List<CityViewModel> CITIES = new ArrayList<>();
    private final List<CityViewModel> CITIES_EMPTY = new ArrayList<>();

    @Before
    public void setUp() {
        compositeDisposable = new CompositeDisposable();
        citiesInteractor = mock(CitiesInteractor.class);
        citiesView = mock(CitiesView.class);
        citiesPresenterCache = mock(CitiesPresenterCache.class);
        rxSchedulers = new RxSchedulersTest();
        citiesPresenter = new CitiesPresenterImpl(citiesInteractor, compositeDisposable,
                citiesPresenterCache, rxSchedulers);
        inputCityTextListener  = PublishSubject.create();;

        CITIES.add(new CityViewModel("Москва", "524901"));
        CITIES.add(new CityViewModel("Катманду", "524901"));
        CITIES.add(new CityViewModel("Дели", "1273294"));
        CITIES.add(new CityViewModel("Киев", "703448"));
        CITIES.add(new CityViewModel("Покхара", "1282898"));
        CITIES.add(new CityViewModel("Мерида", "3632308"));

    }

    @Test
    public void onAttach_cacheExists_showList() {
        when(citiesPresenterCache.isCacheExist()).thenReturn(true);
        when(citiesPresenterCache.getCities()).thenReturn(CITIES);
        citiesPresenter.onAttach(citiesView);
        verify(citiesView, only()).showCities(CITIES);
        verify(citiesView, never()).showError();
        verify(citiesView, never()).showNoMatches();
        verify(citiesView, never()).close();
    }

    @Test
    public void onAttach_cacheNotExists_doNothing() {
        when(citiesPresenterCache.isCacheExist()).thenReturn(false);
        citiesPresenter.onAttach(citiesView);
        verify(citiesView, never()).showCities(anyList());
        verify(citiesView, never()).showError();
        verify(citiesView, never()).showNoMatches();
        verify(citiesView, never()).close();
    }

    @Test
    public void onAttach_cacheExistsButHaveNoElements_showNoMatches() {
        when(citiesPresenterCache.isCacheExist()).thenReturn(true);
        when(citiesPresenterCache.getCities()).thenReturn(CITIES_EMPTY);
        citiesPresenter.onAttach(citiesView);
        verify(citiesView, never()).showCities(anyList());
        verify(citiesView, never()).showError();
        verify(citiesView, only()).showNoMatches();
        verify(citiesView, never()).close();
    }

    @Test
    public void observeChanges_sendValidText_showList() {
        String text = "Mos";
        when(citiesInteractor.getCitiesMatches(text))
                .thenReturn(Observable.fromCallable(() -> CITIES));
        when(citiesPresenterCache.getLastText())
                .thenReturn(text);
        citiesPresenter.onAttach(citiesView);
        citiesPresenter.observeInputChanges(inputCityTextListener);
        inputCityTextListener.onNext(text);
        verify(citiesView, only()).showCities(CITIES);
        verify(citiesView, never()).showError();
        verify(citiesView, never()).showNoMatches();
        verify(citiesView, never()).close();
    }

    @Test
    public void observeChanges_sendEmptyText_showEmptyList() {
        String text = "";
        when(citiesPresenterCache.getLastText())
                .thenReturn(text);
        citiesPresenter.onAttach(citiesView);
        citiesPresenter.observeInputChanges(inputCityTextListener);
        inputCityTextListener.onNext(text);
        verify(citiesInteractor, never()).getCitiesMatches(Mockito.any());
        verify(citiesView, only()).showCities(CITIES_EMPTY);
        verify(citiesView, never()).showError();
        verify(citiesView, never()).showNoMatches();
        verify(citiesView, never()).close();
    }

    @Test
    public void observeChanges_sendInvalidCityName_showNoMatches() {
        String text = "qwscxfg";
        when(citiesPresenterCache.getLastText()).thenReturn(text);
        when(citiesInteractor.getCitiesMatches(text))
                .thenReturn(Observable.fromCallable(() -> CITIES_EMPTY));
        citiesPresenter.onAttach(citiesView);
        citiesPresenter.observeInputChanges(inputCityTextListener);
        inputCityTextListener.onNext(text);
        verify(citiesView, never()).showCities(anyList());
        verify(citiesView, never()).showError();
        verify(citiesView, only()).showNoMatches();
        verify(citiesView, never()).close();
    }

    @Test
    public void observeChanged_error_showError() {
        citiesPresenter.onAttach(citiesView);
        citiesPresenter.observeInputChanges(inputCityTextListener);
        inputCityTextListener.onError(new Throwable());
        verify(citiesInteractor, never()).getCitiesMatches(Mockito.any());
        verify(citiesView, never()).showCities(CITIES_EMPTY);
        verify(citiesView, only()).showError();
        verify(citiesView, never()).showNoMatches();
        verify(citiesView, never()).close();
    }

    @Test
    public void onCityClick_sendValidCity_finish() {
        CityViewModel city = CITIES.get(0);
        when(citiesInteractor.saveCity(city)).thenReturn(Completable.complete());
        citiesPresenter.onAttach(citiesView);
        citiesPresenter.onCityClick(city);
        verify(citiesView, never()).showCities(anyList());
        verify(citiesView, never()).showError();
        verify(citiesView, never()).showNoMatches();
        verify(citiesView, only()).close();
    }

    @Test
    public void onDetach_success() {
        citiesPresenter.onAttach(citiesView);
        citiesPresenter.observeInputChanges(inputCityTextListener);
        citiesPresenter.onDetach();
        assertThat(compositeDisposable.size()).isEqualTo(0);
    }
}
