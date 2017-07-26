package ru.karapetiandav.yamblzproject.data.repositories.cities;

import java.util.List;

import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;

public interface CitiesRepository {
    Observable<List<CityDataModel>> getCitiesMatches(String city);
}
