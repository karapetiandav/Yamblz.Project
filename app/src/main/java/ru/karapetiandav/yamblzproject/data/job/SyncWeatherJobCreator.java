package ru.karapetiandav.yamblzproject.data.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import javax.inject.Inject;

import ru.karapetiandav.yamblzproject.App;
import ru.karapetiandav.yamblzproject.data.network.NetworkHelper;
import ru.karapetiandav.yamblzproject.data.prefs.PreferenceHelper;


public class SyncWeatherJobCreator implements JobCreator {

    @Inject PreferenceHelper preferenceHelper;
    @Inject NetworkHelper networkHelper;

    @Override
    public Job create(String tag) {
        App.getAppComponent().inject(this);
        switch (tag) {
            case SyncWeatherJob.TAG:
                return new SyncWeatherJob(preferenceHelper, networkHelper);
            default:
                return null;
        }
    }
}
