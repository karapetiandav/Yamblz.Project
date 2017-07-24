package ru.karapetiandav.yamblzproject.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.karapetiandav.yamblzproject.di.module.AppModule;
import ru.karapetiandav.yamblzproject.di.module.CitiesModule;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    CitiesComponent plusCitiesComponent(CitiesModule module);

}
