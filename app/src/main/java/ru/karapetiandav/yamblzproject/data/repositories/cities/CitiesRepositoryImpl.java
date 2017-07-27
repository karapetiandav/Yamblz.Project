package ru.karapetiandav.yamblzproject.data.repositories.cities;


import java.util.List;

import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.db.DBHelper;
import ru.karapetiandav.yamblzproject.data.db.model.CityDataModel;

public class CitiesRepositoryImpl implements CitiesRepository {

    private DBHelper dbHelper;

    public CitiesRepositoryImpl(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Single<List<CityDataModel>> getCitiesMatches(String text) {
        return dbHelper.getCities(text);
    }
}
