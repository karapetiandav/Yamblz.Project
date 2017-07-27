package ru.karapetiandav.yamblzproject.business.cities.interactor;


import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.business.cities.mapper.CityMapper;
import ru.karapetiandav.yamblzproject.data.repositories.cities.CitiesRepository;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;
import ru.karapetiandav.yamblzproject.utils.LanguageUtils;

public class CitiesInteractorImpl implements CitiesInteractor {

    private CitiesRepository citiesRepository;
    private CityMapper cityMapper;
    private LanguageUtils languageUtils;

    public CitiesInteractorImpl(CitiesRepository citiesRepository, CityMapper cityMapper,
                                LanguageUtils languageUtils) {
        this.citiesRepository = citiesRepository;
        this.cityMapper = cityMapper;
        this.languageUtils = languageUtils;
    }

    @NonNull
    @Override
    public Observable<List<CityViewModel>> getCitiesMatches(String city) {
        Log.v("log_tag", Thread.currentThread().toString());
        return citiesRepository
                .getCitiesMatches(city, languageUtils.getSupportedLanguageByText(city))
                .toObservable()
                .map(cityMapper::getCityViewModelFromDataModel);
    }
}
