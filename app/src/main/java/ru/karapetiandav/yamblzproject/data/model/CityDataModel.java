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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityDataModel dataModel = (CityDataModel) o;

        if (id != dataModel.id) return false;
        if (name != null ? !name.equals(dataModel.name) : dataModel.name != null) return false;
        return country != null ? country.equals(dataModel.country) : dataModel.country == null;

    }
}
