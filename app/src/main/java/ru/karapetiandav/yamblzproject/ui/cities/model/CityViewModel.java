package ru.karapetiandav.yamblzproject.ui.cities.model;


public final class CityViewModel {

    private final String cityName;

    public CityViewModel(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}
