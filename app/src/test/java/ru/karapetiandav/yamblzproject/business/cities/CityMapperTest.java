package ru.karapetiandav.yamblzproject.business.cities;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.karapetiandav.yamblzproject.business.cities.mapper.CityMapper;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

import static org.assertj.core.api.Assertions.assertThat;

public class CityMapperTest {

    private final List<CityDataModel> CITIES = new ArrayList<>();
    private CityMapper mapper;

    @Before
    public void setUp() {
        CITIES.add(new CityDataModel(12345, "Moscow", "RU"));
        CITIES.add(new CityDataModel(23456, "Kair", "EG"));
        mapper = new CityMapper();
    }

    @Test
    public void common() {
        String countryCode = "RU";
        List<CityViewModel> dataModels = mapper.getViewModelList(CITIES);
        assertThat(dataModels.size()).isEqualTo(CITIES.size());
        CityDataModel dataModel = CITIES.get(0);
        CityDataModel expected = mapper.getDataModel(mapper.getViewModel(dataModel));
        assertThat(dataModel).isEqualTo(expected);
    }
}
