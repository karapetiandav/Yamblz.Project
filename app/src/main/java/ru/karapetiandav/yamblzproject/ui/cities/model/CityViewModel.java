package ru.karapetiandav.yamblzproject.ui.cities.model;


public final class CityViewModel {

    private final String cityInfo;
    private final String cityId;

    public CityViewModel(String cityInfo, String cityId) {
        this.cityInfo = cityInfo;
        this.cityId = cityId;
    }

    public String getCityInfo() {
        return cityInfo;
    }

    public String getCityId() {
        return cityId;
    }
}
