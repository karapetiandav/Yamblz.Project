package ru.karapetiandav.yamblzproject.di.module;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulers;
import ru.karapetiandav.yamblzproject.utils.rx.RxSchedulersImpl;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    @NonNull
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    @NonNull
    Resources provideResources() {
        return context.getResources();
    }

    @Provides
    @Singleton
    @NonNull
    RxSchedulers provideRxSchedulers() {
        return new RxSchedulersImpl();
    }
}
