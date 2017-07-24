package ru.karapetiandav.yamblzproject.di.module;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.karapetiandav.yamblzproject.di.scope.CitiesScope;
import ru.karapetiandav.yamblzproject.ui.cities.adapter.CitiesAdapter;
import ru.karapetiandav.yamblzproject.ui.cities.presenter.CitiesPresenter;
import ru.karapetiandav.yamblzproject.ui.cities.presenter.CitiesPresenterImpl;

@Module
public class CitiesModule {

    @Provides
    @CitiesScope
    @NonNull
    CitiesPresenter provideCitiesPresenter() {
        return new CitiesPresenterImpl();
    }

    @Provides
    @CitiesScope
    @NonNull
    CitiesAdapter provideCitiesAdapter() {
        return new CitiesAdapter();
    }
}
