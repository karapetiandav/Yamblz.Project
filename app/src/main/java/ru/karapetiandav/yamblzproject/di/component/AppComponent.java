package ru.karapetiandav.yamblzproject.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.karapetiandav.yamblzproject.di.module.AppModule;
import ru.karapetiandav.yamblzproject.di.module.CitiesModule;
import ru.karapetiandav.yamblzproject.di.module.DBModule;

@Singleton
@Component(modules = {AppModule.class, DBModule.class})
public interface AppComponent {

    CitiesComponent plusCitiesComponent(CitiesModule module);

}
