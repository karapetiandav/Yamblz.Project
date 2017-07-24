package ru.karapetiandav.yamblzproject;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.evernote.android.job.JobManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.karapetiandav.yamblzproject.di.component.AppComponent;
import ru.karapetiandav.yamblzproject.di.component.DaggerAppComponent;
import ru.karapetiandav.yamblzproject.di.module.AppModule;
import ru.karapetiandav.yamblzproject.job.SyncWeatherJobCreator;
import ru.karapetiandav.yamblzproject.retrofit.WeatherApi;

public class App extends Application {

    // TODO: Поменяйте перед использованием на свой API ключ
    public static final String API_KEY = "1fd56ebafdb3bec85d4b1ac5ae8529eb";
    private static WeatherApi weatherApi;
    private static SharedPreferences sharedPreferences;
    private String baseUrl;
    private Retrofit retrofit;

    public static AppComponent appComponent;

    public static WeatherApi getWeatherApi() {
        return weatherApi;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = buildComponent();

        baseUrl = "http://api.openweathermap.org/";

        JobManager.create(this).addJobCreator(new SyncWeatherJobCreator());

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
