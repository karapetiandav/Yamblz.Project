package ru.karapetiandav.yamblzproject.data.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ru.karapetiandav.yamblzproject.data.db.model.CityDataModel;
import ru.karapetiandav.yamblzproject.data.db.model.Language;
import ru.karapetiandav.yamblzproject.di.qualifiers.DbName;
import ru.karapetiandav.yamblzproject.di.qualifiers.DbVersion;

public class DBHelperImpl extends SQLiteAssetHelper implements DBHelper {

    private static final int ROWS_LIMIT = 20;

    public DBHelperImpl(Context context,@DbName String name,
                        @DbVersion int version) {
        super(context, name, null, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public Single<List<CityDataModel>> getCities(String text, Language language) {
        Log.v("log_tag", Thread.currentThread().toString());
        SQLiteDatabase db = getReadableDatabase();
        String tableName = language.equals(Language.RUS)
                ? CitiesEntry.TABLE_RUS : CitiesEntry.TABLE_ENG;
        text = prepareTextForQuery(text);
        Cursor cursor = db.query(
                tableName,
                null,
                CitiesEntry.CITY_NAME + " LIKE ?",
                new String[]{prepareTextForQuery(text) + "%"},
                null,
                null,
                null,
                String.valueOf(ROWS_LIMIT));
        List<CityDataModel> cities = getCitiesFromCursor(cursor);
        cursor.close();
        db.close();
        return Single.fromCallable(() -> cities);
    }

    private static String prepareTextForQuery(String text) {
        text = text.toLowerCase();
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private List<CityDataModel> getCitiesFromCursor(Cursor cursor) {
        List<CityDataModel> cities = new ArrayList<>();
        while (cursor.moveToNext()) {
            CityDataModel cityDataModel = new CityDataModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            cities.add(cityDataModel);
        }
        return cities;
    }

    private class CitiesEntry {

        public static final String TABLE_ENG = "eng";
        public static final String TABLE_RUS = "rus";
        public static final String CITY_ID = "id";
        public static final String CITY_NAME = "name";
        public static final String COUNTRY = "country";

        private CitiesEntry() {
        }
    }
}
