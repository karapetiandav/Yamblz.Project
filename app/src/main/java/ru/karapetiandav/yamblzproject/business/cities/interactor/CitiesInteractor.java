package ru.karapetiandav.yamblzproject.business.cities.interactor;


import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

public interface CitiesInteractor {
    @NonNull
    Observable<List<CityViewModel>> getCitiesMatches(String text);
}
