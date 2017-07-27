package ru.karapetiandav.yamblzproject.data.model;


public final class CityDataModel {

    private final int id;
    private final String name;
    private final String country;


    public CityDataModel(int cityId, String cityName, String countryCode) {
        this.id = cityId;
        this.name = cityName;
        this.country = countryCode;
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return name;
    }

    public String getCountryCode() {
        return country;
    }
}
