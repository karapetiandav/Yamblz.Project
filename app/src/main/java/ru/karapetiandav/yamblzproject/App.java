package ru.karapetiandav.yamblzproject;

import android.app.Application;
import android.preference.PreferenceManager;

import com.evernote.android.job.JobManager;

import ru.karapetiandav.yamblzproject.data.job.SyncWeatherJob;
import ru.karapetiandav.yamblzproject.data.job.SyncWeatherJobCreator;
import ru.karapetiandav.yamblzproject.di.component.AppComponent;
import ru.karapetiandav.yamblzproject.di.component.DaggerAppComponent;
import ru.karapetiandav.yamblzproject.di.module.AppModule;

public class App extends Application {

    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
        JobManager.create(this).addJobCreator(new SyncWeatherJobCreator());
        SyncWeatherJob.schedulePeriodicJob(PreferenceManager.getDefaultSharedPreferences(this));

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
