package ru.karapetiandav.yamblzproject;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.evernote.android.job.JobManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.karapetiandav.yamblzproject.di.component.DaggerNetworkComponent;
import ru.karapetiandav.yamblzproject.di.component.NetworkComponent;
import ru.karapetiandav.yamblzproject.di.module.AppModule;
import ru.karapetiandav.yamblzproject.di.module.NetworkModule;
import ru.karapetiandav.yamblzproject.job.SyncWeatherJobCreator;
import ru.karapetiandav.yamblzproject.retrofit.WeatherApi;

public class App extends Application {

    public static final String CITY = "Moscow";
    // TODO: Поменяйте перед использованием на свой API ключ
    public static final String API_KEY = "1fd56ebafdb3bec85d4b1ac5ae8529eb";
    private static WeatherApi weatherApi;
    private static SharedPreferences sharedPreferences;
    private NetworkComponent networkComponent;
    private String baseUrl;
    private Retrofit retrofit;

    public static WeatherApi getWeatherApi() {
        return weatherApi;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        baseUrl = "http://api.openweathermap.org/";

        networkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(baseUrl))
                .build();

        JobManager.create(this).addJobCreator(new SyncWeatherJobCreator());

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
