package ru.karapetiandav.yamblzproject.utils.rx;


import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulersTest implements RxSchedulers {

    @Override
    public Scheduler getMainThreadScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.trampoline();
    }
}
