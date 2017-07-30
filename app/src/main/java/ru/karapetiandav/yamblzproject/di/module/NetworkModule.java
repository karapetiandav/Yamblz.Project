package ru.karapetiandav.yamblzproject.di.module;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.data.network.NetworkHelper;
import ru.karapetiandav.yamblzproject.data.network.NetworkHelperImpl;
import ru.karapetiandav.yamblzproject.di.qualifiers.ApiKey;
import ru.karapetiandav.yamblzproject.di.qualifiers.ApiBaseUrl;
import ru.karapetiandav.yamblzproject.data.network.api.WeatherApi;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    @NonNull
    @ApiKey
    String provideWeatherApiKey(Resources resources) {
        return resources.getString(R.string.api_key);
    }

    @Provides
    @Singleton
    @NonNull
    @ApiBaseUrl
    String provideBaseUrl(Resources resources) {
        return resources.getString(R.string.api_base_url);
    }

    @Provides
    @Singleton
    @NonNull
    WeatherApi provideWeatherApi(@ApiBaseUrl String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build().create(WeatherApi.class);
    }

    @Provides
    @Singleton
    @NonNull
    NetworkHelper provideNetworkHelper(WeatherApi weatherApi, @ApiKey String apikey) {
        return new NetworkHelperImpl(weatherApi, apikey);
    }
}
