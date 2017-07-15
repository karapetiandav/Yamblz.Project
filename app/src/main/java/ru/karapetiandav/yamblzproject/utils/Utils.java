package ru.karapetiandav.yamblzproject.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.karapetiandav.yamblzproject.R;

public class Utils {
    public static String convertUnixTimeToString(long unixTime, Context context) {
        Date date = new Date(unixTime * 1000);
        return context.getString(R.string.weather_fragment_today) + " " + new SimpleDateFormat("d MMM").format(date);
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
}