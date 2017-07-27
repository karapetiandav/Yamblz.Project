package ru.karapetiandav.yamblzproject.ui.cities.presenter;


import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.ui.base.presenter.Presenter;
import ru.karapetiandav.yamblzproject.ui.cities.model.CityViewModel;

public interface CitiesPresenter<V> extends Presenter<V> {
    void onCityClick(CityViewModel city);
    void observeInputChanges(Observable<CharSequence> inputChanges);
}
