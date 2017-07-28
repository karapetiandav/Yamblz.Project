package ru.karapetiandav.yamblzproject.ui.cities.presenter;


import java.util.List;

import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

public class CitiesPresenterCache {

    private List<CityViewModel> cities;
    private String lastText;

    void updateData(List<CityViewModel> cities) {
        this.cities = cities;
    }

    List<CityViewModel> getCities() {
        return cities;
    }

    boolean isCacheExist() {
        return cities != null;
    }

    String getLastText() {
        return lastText;
    }

    void setLastText(String lastText) {
        this.lastText = lastText;
    }
}
