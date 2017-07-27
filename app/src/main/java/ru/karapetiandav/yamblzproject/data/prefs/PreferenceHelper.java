package ru.karapetiandav.yamblzproject.data.prefs;


import io.reactivex.Completable;
import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;

public interface PreferenceHelper {

    Completable saveCity(CityDataModel city);
    Single<CityDataModel> getCity();

}
