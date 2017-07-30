package ru.karapetiandav.yamblzproject.ui.weather.model;


public final class WeatherViewModel {

    private final int imageResourceId;
    private final String temp;
    private final String pressure;
    private final String humidity;
    private final String date;

    public WeatherViewModel(int imageResourceId, String temp, String pressure,
                            String humidity, String date) {
        this.imageResourceId = imageResourceId;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.date = date;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getTemp() {
        return temp;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getDate() {
        return date;
    }
}
