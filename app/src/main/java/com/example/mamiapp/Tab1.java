package com.example.mamiapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mamiapp.Common.Common;
import com.example.mamiapp.Model.WeatherResult;
import com.example.mamiapp.Retrofit.IOpenWeatherMap;
import com.example.mamiapp.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class Tab1 extends Fragment {

    private ImageView image_weather;
    private TextView date;
    public TextView temperature;
    private TextView weatherHeader;
    private TextView weatherDescription;
    private ProgressBar loading;
    private ConstraintLayout weatherDataContainer;
    public String locationName;
    private FragmentAListener listener;

    //to be back
    //private MainActivityViewModel viewModel;
    //

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;




    static Tab1 instance;

    public static Tab1 getInstance(){
        if (instance == null) instance = new Tab1();
        // compositeDisposable = new CompositeDisposable();
        // Retrofit retrofit = new RetrofitClient().getInstance();
        // mService = retrofit.create(IOpenWeatherMap.class);

        return instance;
    }



    public Tab1(){
        compositeDisposable = new CompositeDisposable();
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

    @Subscribe
    public void onEvent(DataSyncEvent syncStatusMessage) {
        if(syncStatusMessage.getSyncStatusMessage().equals("Active")){

            getAddress(MainActivity.mLocation.getLatitude(),MainActivity.mLocation.getLongitude());
            getWeatherInformation();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
        if (context instanceof FragmentAListener) {
            listener = (FragmentAListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement fragment");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
        listener = null;
    }


    public interface FragmentAListener {
        void onInputASent(String input);
    }


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View itemView = inflater.inflate(R.layout.tab1, container, false);

       image_weather = (ImageView)itemView.findViewById(R.id.weatherImage);
       weatherDataContainer = (ConstraintLayout)itemView.findViewById(R.id.weatherDataContainer);
       date = (TextView)itemView.findViewById(R.id.dateInfo);
       temperature = (TextView)itemView.findViewById(R.id.temperature);
       weatherHeader = (TextView)itemView.findViewById(R.id.weatherHeader);
       weatherDescription = (TextView)itemView.findViewById(R.id.WeatherDetails);


       loading = (ProgressBar)itemView.findViewById(R.id.loading);





        // to be back
      //  viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
      //  viewModel.getWeather();
        //

//        viewModel.getError().observe(this, new Observer<String>() {
//
//            @Override
//            public void onChanged(@Nullable String s) {
//                Toast.makeText(Tab1.this, s, Toast.LENGTH_LONG)
//                        .show();
//            }
//        });



        //to be back
//        viewModel.getTemperature().observe(this, new Observer<Double>() {
//            @Override
//            public void onChanged(@Nullable Double s) {
//                temperature.setText(String.valueOf(s));
//                //tvTrivia.setText(s);
//            }
//        });








        return itemView;



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
                                weatherDataContainer.setVisibility(View.VISIBLE);
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



    //putFloat("total", value).apply();


//    private void getWeatherInformation() {
//        compositeDisposable.add(mService
//                .getWeatherByLatLng(String.valueOf(Common.current_location.getLatitude())
//                        ,String.valueOf(Common.current_location.getLongitude())
//                        , Common.APP_ID, "metric")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<WeatherResult>() {
//                    @Override
//                    public void accept(WeatherResult weatherResult) throws Exception {
//
//                        //Load image
//                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
//                                .append(weatherResult.getWeather().get(0).getIcon())
//                                .append(".png").toString()).into(image_weather);
//
//                        // Load information
//                        temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp()))
//                        .append("°C").toString());
//                        date.setText(Common.convertUnixToDate(weatherResult.getDt()));
//
//                        //Display panel
//                        weatherDataContainer.setVisibility(View.VISIBLE);
//                        loading.setVisibility(View.GONE);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(getActivity(), ""+throwable.getMessage() , Toast.LENGTH_SHORT);
//                    }
//                })
//        );
//    }

}




