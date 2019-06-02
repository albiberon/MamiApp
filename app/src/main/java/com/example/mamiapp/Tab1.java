package com.example.mamiapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    //to be back
    //private MainActivityViewModel viewModel;
    //

    CompositeDisposable compositeDisposable;


    static Tab1 instance;

    public static Tab1 getInstance(){
        if (instance == null) instance = new Tab1();
        // compositeDisposable = new CompositeDisposable();
        // Retrofit retrofit = new RetrofitClient().getInstance();
        // mService = retrofit.create(IOpenWeatherMap.class);

        return instance;
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



//       loading = (ProgressBar)itemView.findViewById(R.id.loading);

//        getWeatherInformation();


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




