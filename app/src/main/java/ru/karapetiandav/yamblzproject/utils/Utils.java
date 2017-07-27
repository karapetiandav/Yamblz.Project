package ru.karapetiandav.yamblzproject.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.karapetiandav.yamblzproject.App;
import ru.karapetiandav.yamblzproject.R;

import static ru.karapetiandav.yamblzproject.mvp.WeatherPresenter.DATE;
import static ru.karapetiandav.yamblzproject.mvp.WeatherPresenter.HUMIDITY;
import static ru.karapetiandav.yamblzproject.mvp.WeatherPresenter.PRESSURE;
import static ru.karapetiandav.yamblzproject.mvp.WeatherPresenter.TEMP;
import static ru.karapetiandav.yamblzproject.mvp.WeatherPresenter.WEATHER_ID;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static String convertUnixTimeToString(long unixTime, Context context) {
        Date date = new Date(unixTime * 1000);
        return context.getString(R.string.weather_fragment_today) + " " + new SimpleDateFormat("d MMM").format(date);
    }

    public static String formatTemperature(Context context, double temperature) {
        if (context.getString(R.string.pressure_measure_unit).equals("hpa")) {
            return (int) Math.floor((temperature - 273) * 1.8 + 32) + context.getString(R.string.temp_measure_unit);
        } else {
            return (int) Math.floor(temperature - 273) + context.getString(R.string.temp_measure_unit);
        }
    }

    public static String formatPressure(Context context, float pressure) {
        int pressureInt = Math.round(pressure);
        if (getCurrentLocale(context) == Locale.US) {
            return context.getString(R.string.weather_fragment_pressure) + " " + pressureInt +
                    " " + context.getString(R.string.pressure_measure_unit);
        } else {
            return context.getString(R.string.weather_fragment_pressure) + " " + Math.round(pressureInt * 0.750) +
                    " " + context.getString(R.string.pressure_measure_unit);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    public static String formatHumidity(Context context, int humidity) {
        return context.getString(R.string.weather_fragment_humidity) + " " + humidity + "%";
    }

    public static int getIconResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
//            return R.drawable.ic_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        } else if (weatherId == 511) {
//            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
//            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
//            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
//            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 781) {
//            return R.drawable.ic_storm;
        } else if (weatherId == 800) {
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
//            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds;
        }
        return -1;
    }

    public static void saveCurrentWeatherInPreference(String date, String temp, String humidity, String pressure, int weatherId) {
        SharedPreferences.Editor editor = App.getSharedPreferences().edit();
        editor.putString(DATE, date);
        editor.putString(TEMP, temp);
        editor.putString(HUMIDITY, humidity);
        editor.putString(PRESSURE, pressure);
        editor.putInt(WEATHER_ID, weatherId);
        editor.apply();
    }
}
