package ru.karapetiandav.yamblzproject;

import android.app.Application;

import ru.karapetiandav.yamblzproject.di.component.DaggerNetworkComponent;
import ru.karapetiandav.yamblzproject.di.component.NetworkComponent;
import ru.karapetiandav.yamblzproject.di.module.AppModule;
import ru.karapetiandav.yamblzproject.di.module.NetworkModule;

public class App extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule("http://api.openweathermap.org/"))
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
