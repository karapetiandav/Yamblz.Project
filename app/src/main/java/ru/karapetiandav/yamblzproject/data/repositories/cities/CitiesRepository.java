package ru.karapetiandav.yamblzproject.data.repositories.cities;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.model.Language;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;

public interface CitiesRepository {

    Single<List<CityDataModel>> getCitiesMatches(String city, Language language);
    Completable saveCity(CityDataModel city);

}
