package com.example.mamiapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mamiapp.Common.Common;
import com.example.mamiapp.Model.WeatherResult;
import com.example.mamiapp.Retrofit.IOpenWeatherMap;
import com.example.mamiapp.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Tab1ViewModel extends AndroidViewModel {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;




    public Tab1ViewModel(@NonNull Application application) {

        super(application);
        Retrofit retrofit = new RetrofitClient().getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);

    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            //Log.v("IGA", "Address" + add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getWeatherInformation() {
        compositeDisposable.add(mService
                        .getWeatherByLatLng(String.valueOf(MainActivity.mLocation.getLatitude())
                                ,String.valueOf(MainActivity.mLocation.getLongitude())
                                , Common.APP_ID, "metric")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<WeatherResult>() {
                            @Override
                            public void accept(WeatherResult weatherResult) throws Exception {

                                //Load image
                                Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                        .append(weatherResult.getWeather().get(0).getIcon())
                                        .append(".png").toString()).into(image_weather);

                                // Loading information
                                temperature.setText(new StringBuilder(String.valueOf(Math.round(weatherResult.getMain().getTemp())))
//                        temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp()))
                                        .append("°c").toString());
                                date.setText(Common.convertUnixToDate(weatherResult.getDt()));

                                //Display panel
                                Tab1.getInstance().weatherDataContainer.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.GONE);

                                locationName = weatherResult.getName();
                                Log.v("LOCATIONNAME", locationName);
                                listener.onInputASent(locationName);

                                weatherHeader.setText(new StringBuilder(weatherResult.getWeather().get(0).getMain()));
                                weatherDescription.setText(new StringBuilder(weatherResult.getWeather().get(0).getDescription()));


                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getActivity(), ""+throwable.getMessage() , Toast.LENGTH_SHORT);
                            }
                        })
        );
    }
}
