package ru.karapetiandav.yamblzproject.business.cities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import ru.karapetiandav.yamblzproject.business.cities.interactor.CitiesInteractor;
import ru.karapetiandav.yamblzproject.business.cities.interactor.CitiesInteractorImpl;
import ru.karapetiandav.yamblzproject.business.cities.mapper.CityMapper;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.data.model.Language;
import ru.karapetiandav.yamblzproject.data.repositories.cities.CitiesRepository;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;
import ru.karapetiandav.yamblzproject.utils.LanguageUtils;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CitiesInteractorTest {

    private CitiesRepository citiesRepository;
    private CityMapper cityMapper;
    private CitiesInteractor interactor;
    private LanguageUtils languageUtils;

    private List<CityDataModel> CITIES = new ArrayList<>();

    @Before
    public void setUp() {
        citiesRepository = mock(CitiesRepository.class);
        cityMapper = mock(CityMapper.class);
        languageUtils = mock(LanguageUtils.class);
        interactor = new CitiesInteractorImpl(citiesRepository, cityMapper, languageUtils);
        CITIES.add(new CityDataModel(12345, "Moscow", "RU"));
    }

    @Test
    public void getCitiesMatches_sendValid() {
        String text = "text";
        when(languageUtils.getSupportedLanguageByText(text)).thenReturn(Language.ENG);
        when(citiesRepository
                .getCitiesMatches(text, languageUtils.getSupportedLanguageByText(text)))
                .thenReturn(Single.fromCallable(() -> CITIES));
        TestObserver<List<CityViewModel>> testObserver = TestObserver.create();
        interactor.getCitiesMatches(text).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void getCitiesMatches_error() {
        String text = "text";
        Throwable throwable = new Throwable();
        when(languageUtils.getSupportedLanguageByText(text)).thenReturn(Language.ENG);
        when(citiesRepository
                .getCitiesMatches(text, languageUtils.getSupportedLanguageByText(text)))
                .thenReturn(Single.error(throwable));
        TestObserver<List<CityViewModel>> testObserver = TestObserver.create();
        interactor.getCitiesMatches(text).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertError(throwable);
        testObserver.assertNotComplete();
    }

    @Test
    public void saveCity_sendValid() {
        CityViewModel cityViewModel = new CityViewModel("Moscow", "12345");
        when(cityMapper.getDataModel(cityViewModel)).thenReturn(any());
        when(citiesRepository.saveCity(cityMapper.getDataModel(cityViewModel)))
                .thenReturn(Completable.complete());
        TestObserver testObserver = TestObserver.create();
        interactor.saveCity(cityViewModel).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }
}
