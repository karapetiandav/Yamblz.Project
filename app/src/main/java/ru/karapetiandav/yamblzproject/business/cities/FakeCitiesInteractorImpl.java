package ru.karapetiandav.yamblzproject.business.cities;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

public class FakeCitiesInteractorImpl implements CitiesInteractor {

    private static final List<CityViewModel> CITIES = new ArrayList<>();

    static {
        CITIES.add(new CityViewModel("Москва", "524901"));
        CITIES.add(new CityViewModel("Катманду", "524901"));
        CITIES.add(new CityViewModel("Дели", "1273294"));
        CITIES.add(new CityViewModel("Киев", "703448"));
        CITIES.add(new CityViewModel("Покхара", "1282898"));
        CITIES.add(new CityViewModel("Мерида", "3632308"));
    }

    @Override
    @NonNull
    public Observable<List<CityViewModel>> getCitiesMatches(@NonNull String text) {
        Collections.shuffle(CITIES);
        return Observable.timer(1, TimeUnit.SECONDS).flatMap(ignore -> Observable.just(CITIES));
    }
}
