package ru.karapetiandav.yamblzproject.ui.cities.model;


public final class CityViewModel {

    private final String cityName;
    private final String cityId;

    public CityViewModel(String cityName, String cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityId() {
        return cityId;
    }
}
