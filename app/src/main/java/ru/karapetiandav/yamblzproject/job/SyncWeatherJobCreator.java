package ru.karapetiandav.yamblzproject.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;


public class SyncWeatherJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case SyncWeatherJob.TAG:
                return new SyncWeatherJob();
            default:
                return null;
        }
    }
}
