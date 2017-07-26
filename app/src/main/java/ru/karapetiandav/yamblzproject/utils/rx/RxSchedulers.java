package ru.karapetiandav.yamblzproject.utils.rx;


import io.reactivex.Scheduler;

public interface RxSchedulers {
    Scheduler getMainThreadScheduler();
    Scheduler getIOScheduler();
}
