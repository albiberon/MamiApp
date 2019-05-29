package com.example.mamiapp.Retrofit;

import com.example.mamiapp.Weather;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherApiService {


    @GET("?lat=52.3679375&lon=4.876652&appid=be545df06e4d6c3621ae3c774a0747fe")
    Call<Weather> getWeatherData();
}
