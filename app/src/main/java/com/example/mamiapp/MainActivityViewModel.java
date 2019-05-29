package com.example.mamiapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.mamiapp.Retrofit.WeatherRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private WeatherRepository weatherRepository = new WeatherRepository();

    private MutableLiveData<Double> temperature = new MutableLiveData<>();
    private MutableLiveData<String> weatherHeader= new MutableLiveData<>();
    private MutableLiveData<String> weatherDetails = new MutableLiveData<>();
    private MutableLiveData<String> icon = new MutableLiveData<>();

    private MutableLiveData<String> error = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getIcon() {
        return icon;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Double> getTemperature() {
        return temperature;
    }

    public MutableLiveData<String> getWeatherHeader() {
        return weatherHeader;
    }

    public MutableLiveData<String> getWeatherDetails() {
        return weatherDetails;
    }

    public void getWeather() {
        weatherRepository
                .getWeatherData()
                .enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            temperature.setValue(response.body().getTemp());
                            weatherHeader.setValue(response.body().getMain());
                            weatherDetails.setValue(response.body().getDescription());
                            icon.setValue(response.body().getIcon());
                        } else {
                            error.setValue("Api Error: " + response.message());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                        error.setValue("Api Error: " + t.getMessage());
                    }
                });
    }
}
