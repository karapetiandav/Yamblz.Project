package ru.karapetiandav.yamblzproject.data.job;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

import ru.karapetiandav.yamblzproject.data.model.WeatherDataModel;
import ru.karapetiandav.yamblzproject.data.network.NetworkHelper;
import ru.karapetiandav.yamblzproject.data.prefs.PreferenceHelper;


public class SyncWeatherJob extends Job {

    public static final String TAG = "Sync_Weather_Job";

    private PreferenceHelper preferenceHelper;
    private NetworkHelper networkHelper;

    public SyncWeatherJob(PreferenceHelper preferenceHelper, NetworkHelper networkHelper) {
        this.preferenceHelper = preferenceHelper;
        this.networkHelper = networkHelper;
    }

    public static void schedulePeriodicJob(SharedPreferences sharedPreferences) {
        int minutes = Integer.parseInt(sharedPreferences.getString("pref_update_time", "180"));
        int jobId = new JobRequest.Builder(SyncWeatherJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(minutes), TimeUnit.MINUTES.toMillis(10))
                .setUpdateCurrent(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setPersisted(true)
                .build()
                .schedule();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        preferenceHelper.getCity()
                .flatMap(city -> networkHelper.loadWeather(city.getId()))
                .map(WeatherDataModel::valueOf)
                .doOnSuccess(preferenceHelper::saveWeather)
                .subscribe();
        return Result.SUCCESS;
    }
}