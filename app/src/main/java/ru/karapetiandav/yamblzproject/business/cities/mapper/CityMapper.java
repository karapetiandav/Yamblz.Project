package ru.karapetiandav.yamblzproject.business.cities.mapper;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.karapetiandav.yamblzproject.data.model.CityDataModel;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

public class CityMapper {


    @NonNull
    public List<CityViewModel> getViewModelList(@NonNull List<CityDataModel> dataModels) {
        List<CityViewModel> viewModels = new ArrayList<>();
        if (dataModels.size() == 0) return viewModels;
        for(CityDataModel dataModel : dataModels) {
            viewModels.add(getViewModel(dataModel));
        }
        return viewModels;
    }

    @NonNull
    public CityViewModel getViewModel(CityDataModel dataModel) {
        String cityId = String.valueOf(dataModel.getId());
        String cityInfo = dataModel.getCityName() +
                ", " +
                dataModel.getCountryCode();
        return new CityViewModel(cityInfo, cityId);
    }

    @NonNull
    public CityDataModel getDataModel(CityViewModel viewModel) {
        int cityId = Integer.valueOf(viewModel.getCityId());
        String[] args = viewModel.getCityInfo().split(", ");
        return new CityDataModel(cityId, args[0], args[1]);
    }

}
