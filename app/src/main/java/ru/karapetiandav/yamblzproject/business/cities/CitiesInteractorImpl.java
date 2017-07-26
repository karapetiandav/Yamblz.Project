package ru.karapetiandav.yamblzproject.business.cities;


import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.business.cities.mapper.CityMapper;
import ru.karapetiandav.yamblzproject.data.repositories.cities.CitiesRepository;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

public class CitiesInteractorImpl implements CitiesInteractor {

    private CitiesRepository citiesRepository;
    private CityMapper cityMapper;

    public CitiesInteractorImpl(CitiesRepository citiesRepository, CityMapper cityMapper) {
        this.citiesRepository = citiesRepository;
        this.cityMapper = cityMapper;
    }

    @NonNull
    @Override
    public Observable<List<CityViewModel>> getCitiesMatches(String city) {
        return citiesRepository.getCitiesMatches(city)
                .map(cityMapper::getCityViewModelFromDataModel);
    }
}
