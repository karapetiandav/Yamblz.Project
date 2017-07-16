package ru.karapetiandav.yamblzproject.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.karapetiandav.yamblzproject.di.module.AppModule;
import ru.karapetiandav.yamblzproject.di.module.NetworkModule;
import ru.karapetiandav.yamblzproject.ui.fragments.WeatherFragment;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {
    void inject(WeatherFragment weatherFragment);
}
