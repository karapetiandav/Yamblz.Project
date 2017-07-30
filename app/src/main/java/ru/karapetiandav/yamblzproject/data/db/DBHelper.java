package ru.karapetiandav.yamblzproject.data.db;


import java.util.List;

import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.data.model.Language;

public interface DBHelper {
    Single<List<CityDataModel>> getCities(String text, Language language);
}
