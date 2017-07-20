package ru.karapetiandav.yamblzproject.job;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.karapetiandav.yamblzproject.App;
import ru.karapetiandav.yamblzproject.model.WeatherData;
import ru.karapetiandav.yamblzproject.utils.Utils;

import static ru.karapetiandav.yamblzproject.App.API_KEY;
import static ru.karapetiandav.yamblzproject.App.CITY;
import static ru.karapetiandav.yamblzproject.ui.fragments.WeatherFragment.DATE;
import static ru.karapetiandav.yamblzproject.ui.fragments.WeatherFragment.HUMIDITY;
import static ru.karapetiandav.yamblzproject.ui.fragments.WeatherFragment.PRESSURE;
import static ru.karapetiandav.yamblzproject.ui.fragments.WeatherFragment.TEMP;
import static ru.karapetiandav.yamblzproject.ui.fragments.WeatherFragment.WEATHER_ID;


public class SyncWeatherJob extends Job {

    public static final String TAG = "Sync_Weather_Job";

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
        App.getWeatherApi().getWeatherData(CITY, API_KEY).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherData data = response.body();
                String date = Utils.convertUnixTimeToString(data.getDt(), getContext());
                String temp = Utils.formatTemperature(getContext(), data.getMain().getTemp());
                String humidity = Utils.formatHumidity(getContext(), data.getMain().getHumidity());
                String pressure = Utils.formatPressure(getContext(), data.getMain().getPressure());
                int weatherId = data.getWeather().get(0).getId();

                SharedPreferences.Editor editor = App.getSharedPreferences().edit();
                editor.putString(DATE, date);
                editor.putString(TEMP, temp);
                editor.putString(HUMIDITY, humidity);
                editor.putString(PRESSURE, pressure);
                editor.putInt(WEATHER_ID, weatherId);
                editor.apply();
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

        return Result.SUCCESS;
    }
}