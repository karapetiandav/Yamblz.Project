package ru.karapetiandav.yamblzproject.ui.cities.presenter;


import android.support.annotation.NonNull;

import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.ui.base.presenter.Presenter;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

public interface CitiesPresenter<V> extends Presenter<V> {
    void onCityClick(@NonNull CityViewModel city);
    void observeInputChanges(Observable<CharSequence> inputChanges);
}
