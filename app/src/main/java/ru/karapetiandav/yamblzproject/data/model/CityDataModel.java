package ru.karapetiandav.yamblzproject.data.model;


public final class CityDataModel {

    private final int cityId;
    private final String cityName;
    private final String countryCode;


    public CityDataModel(int cityId, String cityName, String countryCode) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryCode = countryCode;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
