package ru.karapetiandav.yamblzproject.data.prefs;


import android.content.SharedPreferences;
import android.content.res.Resources;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;

public class PreferenceHelperImpl implements PreferenceHelper {

    private static final String CITY_ID_KEY = "city_id_key";
    private static final String CITY_NAME_KEY = "city_name_key";
    private static final String COUNTRY_KEY = "country_key";

    private SharedPreferences sharedPrefs;
    private Resources resources;

    public PreferenceHelperImpl(SharedPreferences sharedPrefs, Resources resources) {
        this.sharedPrefs = sharedPrefs;
        this.resources = resources;
    }

    @Override
    public Completable saveCity(CityDataModel city) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(CITY_ID_KEY, city.getId());
        editor.putString(CITY_NAME_KEY, city.getCityName());
        editor.putString(COUNTRY_KEY, city.getCountryCode());
        editor.apply();
        return Completable.complete();
    }

    @Override
    public Single<CityDataModel> getCity() {
        CityDataModel cityDataModel = new CityDataModel(
                sharedPrefs.getInt(CITY_ID_KEY, resources.getInteger(R.integer.default_city_id)),
                sharedPrefs.getString(CITY_NAME_KEY, resources.getString(R.string.default_city_name)),
                sharedPrefs.getString(COUNTRY_KEY, resources.getString(R.string.default_country))
        );
        return Single.fromCallable(() -> cityDataModel);
    }
}
