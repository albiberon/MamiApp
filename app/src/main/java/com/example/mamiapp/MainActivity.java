package com.example.mamiapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mamiapp.Common.Common;
import com.example.mamiapp.Database.DBClient;
import com.example.mamiapp.Database.ProductTable;
import com.example.mamiapp.Retrofit.IOpenWeatherMap;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

//import com.example.mamiapp.Common.Common;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private float x1, x2, y1, y2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    private List<ProductTable> productList = new ArrayList<>();


    // I will need this to populate city name in correct place
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    private TextView cityName;

    private ImageView mamiImage;
    private ImageView backgroundImage;

    public static Location mLocation;




    //location related
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mamiImage = (ImageView)findViewById(R.id.mamiImage);
       backgroundImage = (ImageView)findViewById(R.id.backgroundImage);

       mamiImage.setImageDrawable(getDrawable(R.drawable.mami_talking));
       backgroundImage.setImageDrawable(getDrawable(R.drawable.rain));


        //mami animation
        Animation mamiAnimation = new TranslateAnimation(Animation.ABSOLUTE,0,Animation.ABSOLUTE,-730);
        mamiAnimation.setDuration(500);
        mamiAnimation.setFillAfter(true);
       mamiImage.startAnimation(mamiAnimation);


        //background animation

        Animation backgroundAnimation = new AlphaAnimation(0f, 1.0f);
        backgroundAnimation.setDuration(800);
        backgroundAnimation.setFillAfter(true);
        backgroundImage.startAnimation(backgroundAnimation);


        viewPager = findViewById(R.id.pager);
        //method for initialisation
        cityName = findViewById(R.id.locationName);
        //temperature = findViewById(R.id.temperature);

        //request permission
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                                  @Override
                                  public void onPermissionsChecked(MultiplePermissionsReport report) {
                                      if (report.areAllPermissionsGranted()) {

                                          buildLocationRequest();
                                          buildLocationCallBack();


                                          if (ActivityCompat.checkSelfPermission
                                                  (MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                                  && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                                  != PackageManager.PERMISSION_GRANTED) {

                                              return;
                                          }
                                          fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                                          fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                                      }
                                  }

                                  @Override
                                  public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                      Snackbar.make(viewPager, "Permission Denied", Snackbar.LENGTH_LONG)
                                              .show();
                                  }
                              }


                ).check();

        init();

        //DB population at start

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                if( DBClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .productDao()
                        .getWholeList().isEmpty()) {

                    for (int i = 0; i < GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES.length; i++) {
                        ProductTable productTable = new ProductTable();
                        productTable.setGeoName(GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES[i]);
                        productTable.setGeoPrice(GeoObject.PRE_DEFINED_PRICES[i]);
                        productTable.setGeoImageName(GeoObject.PRE_DEFINED_GEO_OBJECT_IMAGE_IDS[i]);
                        productList.add(productTable);

                    }

                    DBClient.getInstance(MainActivity.this).getAppDatabase().productDao().insert(productList);
                }
                return null;
            }
        }.execute();



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocation != null) {
            //Greenrobots EventBus provides simplified communication between Activities, Fragments, Threads, Services, etc. with less code, better
            EventBus.getDefault().post(new DataSyncEvent("Active"));
        }
    }


    private void buildLocationCallBack() {


        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

//                Common.current_location = locationResult.getLastLocation();

                mLocation = locationResult.getLastLocation();
                if (mLocation != null) {

                    EventBus.getDefault().post(new DataSyncEvent("Active"));


                }


                //Log.d("Location", locationResult.getLastLocation().getLatitude() + "/" + locationResult.getLastLocation().getLongitude());
            }

        };

    }




    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (y1 > y2) {
                    Intent i = new Intent(MainActivity.this, ShopActivity.class);
                    startActivity(i);
                }
                break;

        }
        return false;

    }

    private void init() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

//Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Forecast"));
        tabLayout.addTab(tabLayout.newTab().setText("Tips"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorHeight(0);

//Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

//Initializing view pager adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

//Adding adapter to pager
        viewPager.setAdapter(viewPagerAdapter);

//Adding onTabSelectedListener to swipe views
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }





}
