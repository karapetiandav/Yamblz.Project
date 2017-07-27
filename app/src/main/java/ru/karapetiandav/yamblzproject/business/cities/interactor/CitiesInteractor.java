package ru.karapetiandav.yamblzproject.business.cities.interactor;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

public interface CitiesInteractor {

    Observable<List<CityViewModel>> getCitiesMatches(String text);
    Completable saveCity(CityViewModel city);

}
