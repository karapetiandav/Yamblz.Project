package ru.karapetiandav.yamblzproject.business.cities.mapper;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;
import ru.karapetiandav.yamblzproject.utils.CityUtils;

public class CityMapper {

    private CityUtils cityUtils;

    public CityMapper(CityUtils cityUtils) {
        this.cityUtils = cityUtils;
    }

    @NonNull
    public List<CityViewModel> getCityViewModelFromDataModel(
            @NonNull List<CityDataModel> dataModels) {
        List<CityViewModel> viewModels = new ArrayList<>();
        if (dataModels.size() == 0) return viewModels;
        for(CityDataModel dataModel : dataModels) {
            String cityId = String.valueOf(dataModel.getCityId());
            String cityInfo = dataModel.getCityName() +
                    " " +
                    cityUtils.getCountryNameByCode(dataModel.getCountryCode());
            viewModels.add(new CityViewModel(cityInfo, cityId));
        }
        return viewModels;
    }

}
