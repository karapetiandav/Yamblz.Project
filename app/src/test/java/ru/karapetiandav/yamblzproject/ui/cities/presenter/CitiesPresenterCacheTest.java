package ru.karapetiandav.yamblzproject.ui.cities.presenter;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

import static org.assertj.core.api.Assertions.assertThat;

public class CitiesPresenterCacheTest {

    private final List<CityViewModel> CITIES = new ArrayList<>();


    @Before
    public void setUp() {
        CITIES.add(new CityViewModel("Москва", "524901"));
        CITIES.add(new CityViewModel("Катманду", "524901"));
        CITIES.add(new CityViewModel("Дели", "1273294"));
        CITIES.add(new CityViewModel("Киев", "703448"));
        CITIES.add(new CityViewModel("Покхара", "1282898"));
        CITIES.add(new CityViewModel("Мерида", "3632308"));
    }
    @Test
    public void testCommon() {
        CitiesPresenterCache cache = new CitiesPresenterCache();
        assertThat(cache.isCacheExist()).isFalse();
        cache.updateData(CITIES);
        assertThat(cache.isCacheExist()).isTrue();
        assertThat(cache.getCities()).isEqualTo(CITIES);
        String lastText = "text";
        cache.setLastText(lastText);
        assertThat(cache.getLastText()).isEqualTo(lastText);
    }
}
