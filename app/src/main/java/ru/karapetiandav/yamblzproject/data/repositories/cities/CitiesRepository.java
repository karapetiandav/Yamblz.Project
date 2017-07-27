package ru.karapetiandav.yamblzproject.data.repositories.cities;

import java.util.List;

import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.db.model.Language;
import ru.karapetiandav.yamblzproject.data.db.model.CityDataModel;

public interface CitiesRepository {
    Single<List<CityDataModel>> getCitiesMatches(String city, Language language);
}
