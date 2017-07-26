package ru.karapetiandav.yamblzproject.utils;


import android.content.res.Resources;

public class CityUtils {

    private Resources resources;

    public CityUtils(Resources resources) {
        this.resources = resources;
    }

    public String getCountryNameByCode(String countryCode) {
        return "Россия";
    }
}
