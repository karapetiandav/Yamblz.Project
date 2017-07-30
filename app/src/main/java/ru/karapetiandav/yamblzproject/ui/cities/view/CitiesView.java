package ru.karapetiandav.yamblzproject.ui.cities.view;


import java.util.List;

import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

public interface CitiesView {
    void showCities(List<CityViewModel> cities);
    void showNoMatches();
    void showError();
    void close();
}
