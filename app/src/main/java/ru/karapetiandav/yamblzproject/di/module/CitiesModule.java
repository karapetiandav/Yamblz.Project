package ru.karapetiandav.yamblzproject.di.module;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.karapetiandav.yamblzproject.business.cities.CitiesInteractor;
import ru.karapetiandav.yamblzproject.business.cities.FakeCitiesInteractorImpl;
import ru.karapetiandav.yamblzproject.di.scope.CitiesScope;
import ru.karapetiandav.yamblzproject.ui.cities.adapter.CitiesAdapter;
import ru.karapetiandav.yamblzproject.ui.cities.presenter.CitiesPresenter;
import ru.karapetiandav.yamblzproject.ui.cities.presenter.CitiesPresenterCache;
import ru.karapetiandav.yamblzproject.ui.cities.presenter.CitiesPresenterImpl;
import ru.karapetiandav.yamblzproject.ui.cities.view.CitiesView;

@Module
public class CitiesModule {

    @Provides
    @CitiesScope
    @NonNull
    CitiesPresenter<CitiesView> provideCitiesPresenter(CitiesInteractor interactor,
                                                       CompositeDisposable compositeDisposable,
                                                       CitiesPresenterCache cache) {
        return new CitiesPresenterImpl(interactor, compositeDisposable, cache);
    }

    @Provides
    @CitiesScope
    @NonNull
    CitiesAdapter provideCitiesAdapter() {
        return new CitiesAdapter();
    }

    @Provides
    @CitiesScope
    @NonNull
    CitiesInteractor provideCitiesInteractor() {
        return new FakeCitiesInteractorImpl();
    }

    @Provides
    @CitiesScope
    @NonNull
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @CitiesScope
    @NonNull
    CitiesPresenterCache provideCitiesPresenterCache() {
        return new CitiesPresenterCache();
    }
}
