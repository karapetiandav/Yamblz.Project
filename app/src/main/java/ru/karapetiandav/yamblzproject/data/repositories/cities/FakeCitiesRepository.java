package ru.karapetiandav.yamblzproject.data.repositories.cities;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;

public class FakeCitiesRepository implements CitiesRepository {

    private static final List<CityDataModel> CITIES = new ArrayList<>();

    static {
        CITIES.add(new CityDataModel(496638, "Sergiyev Posad", "RU"));
        CITIES.add(new CityDataModel(487095, "Strunino", "EN"));
        CITIES.add(new CityDataModel(1261481, "New Delhi", "IN"));
        CITIES.add(new CityDataModel(1266073, "Kosi", "RU"));
        CITIES.add(new CityDataModel(1279259, "Agra","US"));
        CITIES.add(new CityDataModel(1269515, "Salsk", "RU"));
    }

    @Override
    public Observable<List<CityDataModel>> getCitiesMatches(String city) {
        Collections.shuffle(CITIES);
        return Observable.fromCallable(() -> CITIES);
    }
}
