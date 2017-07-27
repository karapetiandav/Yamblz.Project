package ru.karapetiandav.yamblzproject.data.prefs;


import android.content.SharedPreferences;
import android.content.res.Resources;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;

public class PreferenceHelperImpl implements PreferenceHelper {

    private SharedPreferences sharedPrefs;
    private Resources resources;

    public PreferenceHelperImpl(SharedPreferences sharedPrefs, Resources resources) {
        this.sharedPrefs = sharedPrefs;
        this.resources = resources;
    }

    @Override
    public Completable saveCity(CityDataModel city) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(resources.getString(R.string.city_id_key), city.getId());
        editor.putString(resources.getString(R.string.city_name_key), city.getCityName());
        editor.putString(resources.getString(R.string.country_key), city.getCountryCode());
        editor.apply();
        return Completable.complete();
    }

    @Override
    public Single<CityDataModel> getCity() {
        CityDataModel cityDataModel = new CityDataModel(
                sharedPrefs.getInt(resources.getString(R.string.city_id_key), resources.getInteger(R.integer.default_city_id)),
                sharedPrefs.getString(resources.getString(R.string.city_name_key), resources.getString(R.string.default_city_name)),
                sharedPrefs.getString(resources.getString(R.string.country_key), resources.getString(R.string.default_country))
        );
        return Single.fromCallable(() -> cityDataModel);
    }
}
