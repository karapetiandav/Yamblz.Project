package ru.karapetiandav.yamblzproject.data.db;


import java.util.List;

import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.db.model.CityDataModel;
import ru.karapetiandav.yamblzproject.data.db.model.Language;

public interface DBHelper {
    Single<List<CityDataModel>> getCities(String text, Language language);
}
