package ru.karapetiandav.yamblzproject.data.repositories.cities;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.db.DBHelper;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.data.model.Language;
import ru.karapetiandav.yamblzproject.data.prefs.PreferenceHelper;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulers;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulersTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CitiesRepositoryTest {

    private CitiesRepository citiesRepository;
    private DBHelper dbHelper;
    private PreferenceHelper preferenceHelper;
    private List<CityDataModel> CITIES = new ArrayList<>();
    private RxSchedulers rxSchedulers;

    @Before
    public void setUp() {
        CITIES.add(new CityDataModel(12345, "Moscow", "RU"));
        dbHelper = mock(DBHelper.class);
        preferenceHelper = mock(PreferenceHelper.class);
        citiesRepository = new CitiesRepositoryImpl(dbHelper, preferenceHelper);
        rxSchedulers = new RxSchedulersTest();
    }

    @Test
    public void getCitiesMatches_success() {
        when(dbHelper.getCities(any(String.class), any(Language.class)))
                .thenReturn(Single.fromCallable(() -> CITIES));
        citiesRepository.getCitiesMatches("city", Language.ENG)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(dataModels -> dataModels.equals(CITIES));
    }

    @Test
    public void getCitiesMatches_noMathes() {
        when(dbHelper.getCities(any(String.class), any(Language.class)))
                .thenReturn(Single.fromCallable(ArrayList::new));
        citiesRepository.getCitiesMatches("city", Language.ENG)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(dataModels -> dataModels.size() == 0);
    }

    @Test
    public void saveCity_test() {
        CityDataModel cityDataModel = CITIES.get(0);
        when(preferenceHelper.saveCity(cityDataModel))
                .thenReturn(Completable.create(CompletableEmitter::onComplete));
        when(preferenceHelper.clearWeatherCache()).thenReturn(Completable.complete());
        citiesRepository.saveCity(cityDataModel)
                .subscribeOn(rxSchedulers.getIOScheduler())
                .observeOn(rxSchedulers.getMainThreadScheduler())
                .test()
                .assertNoErrors()
                .assertComplete();
    }
}
