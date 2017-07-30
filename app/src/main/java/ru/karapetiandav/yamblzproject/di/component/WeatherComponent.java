package ru.karapetiandav.yamblzproject.di.component;

import dagger.Subcomponent;
import ru.karapetiandav.yamblzproject.di.module.WeatherModule;
import ru.karapetiandav.yamblzproject.di.scope.WeatherScope;
import ru.karapetiandav.yamblzproject.ui.weather.view.WeatherFragment;

@WeatherScope
@Subcomponent(modules = WeatherModule.class)
public interface WeatherComponent {
    void inject(WeatherFragment weatherFragment);
}
