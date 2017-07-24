package ru.karapetiandav.yamblzproject.ui.cities.presenter;


import io.reactivex.Observable;
import ru.karapetiandav.yamblzproject.ui.base.presenter.Presenter;

public interface CitiesPresenter<V> extends Presenter<V> {
    void onCityClick(int position);
    void observeInputChanges(Observable<CharSequence> inputChanges);
}
