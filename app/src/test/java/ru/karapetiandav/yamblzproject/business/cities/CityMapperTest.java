package ru.karapetiandav.yamblzproject.business.cities;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.karapetiandav.yamblzproject.business.cities.mapper.CityMapper;
import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;
import ru.karapetiandav.yamblzproject.utils.CityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CityMapperTest {

    private final List<CityDataModel> CITIES = new ArrayList<>();
    private CityMapper mapper;
    private CityUtils cityUtils;

    @Before
    public void setUp() {
        CITIES.add(new CityDataModel(12345, "Moscow", "RU"));
        CITIES.add(new CityDataModel(23456, "Kair", "EG"));
        cityUtils = mock(CityUtils.class);
        mapper = new CityMapper(cityUtils);
    }

    @Test
    public void common() {
        String countryCode = "RU";
        when(cityUtils.getCountryNameByCode(countryCode)).thenReturn(countryCode);
        List<CityViewModel> dataModels = mapper.getViewModelList(CITIES);
        assertThat(dataModels.size()).isEqualTo(CITIES.size());
        CityDataModel dataModel = CITIES.get(0);
        CityDataModel expected = mapper.getDataModel(mapper.getViewModel(dataModel));
        assertThat(dataModel).isEqualTo(expected);
    }
}
