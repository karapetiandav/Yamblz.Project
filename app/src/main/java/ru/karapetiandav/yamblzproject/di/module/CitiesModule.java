package ru.karapetiandav.yamblzproject.di.module;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.karapetiandav.yamblzproject.business.cities.interactor.CitiesInteractor;
import ru.karapetiandav.yamblzproject.business.cities.interactor.CitiesInteractorImpl;
import ru.karapetiandav.yamblzproject.business.cities.mapper.CityMapper;
import ru.karapetiandav.yamblzproject.data.db.DBHelper;
import ru.karapetiandav.yamblzproject.data.prefs.PreferenceHelper;
import ru.karapetiandav.yamblzproject.data.repositories.cities.CitiesRepository;
import ru.karapetiandav.yamblzproject.data.repositories.cities.CitiesRepositoryImpl;
import ru.karapetiandav.yamblzproject.di.scope.CitiesScope;
import ru.karapetiandav.yamblzproject.ui.cities.adapter.CitiesAdapter;
import ru.karapetiandav.yamblzproject.ui.cities.presenter.CitiesPresenter;
import ru.karapetiandav.yamblzproject.ui.cities.presenter.CitiesPresenterCache;
import ru.karapetiandav.yamblzproject.ui.cities.presenter.CitiesPresenterImpl;
import ru.karapetiandav.yamblzproject.ui.cities.view.CitiesView;
import ru.karapetiandav.yamblzproject.utils.LanguageUtils;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulers;

@Module
public class CitiesModule {

    @Provides
    @CitiesScope
    @NonNull
    CitiesPresenter<CitiesView> provideCitiesPresenter(CitiesInteractor interactor,
                                                       CompositeDisposable compositeDisposable,
                                                       CitiesPresenterCache cache,
                                                       RxSchedulers schedulers) {
        return new CitiesPresenterImpl(interactor, compositeDisposable, cache, schedulers);
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
    CitiesInteractor provideCitiesInteractor(CitiesRepository repository, CityMapper mapper,
                                             LanguageUtils languageUtils) {
        return new CitiesInteractorImpl(repository, mapper, languageUtils);
    }

    @Provides
    @CitiesScope
    @NonNull
    CitiesPresenterCache provideCitiesPresenterCache() {
        return new CitiesPresenterCache();
    }

    @Provides
    @CitiesScope
    @NonNull
    CitiesRepository provideCitiesRepository(DBHelper dbHelper, PreferenceHelper preferenceHelper) {
        return new CitiesRepositoryImpl(dbHelper, preferenceHelper);
    }

    @Provides
    @CitiesScope
    @NonNull
    CityMapper provideCityMapper() {
        return new CityMapper();
    }
}
