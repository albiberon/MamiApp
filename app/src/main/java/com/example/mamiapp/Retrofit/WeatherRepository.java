package com.example.mamiapp.Retrofit;

import com.example.mamiapp.Weather;

import retrofit2.Call;

public class WeatherRepository {

    private WeatherApiService weatherApiService = WeatherApi.create();


    public Call<Weather> getWeatherData() {

        return weatherApiService.getWeatherData();

    }


}
