package com.example.mamiapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.mamiapp.Common.Common;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

//import com.example.mamiapp.Common.Common;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private float x1, x2, y1, y2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageView mamiImage;
    //private MainActivityViewModel viewModel;

    //location related
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mamiImage = (ImageView)findViewById(R.id.mami_Image);
        mamiImage.setImageResource(R.drawable.mami_talking);

        //method for initialisation
        // cityName = findViewById(R.id.locationName);
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

    }

    private void buildLocationCallBack() {


        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);


                Common.current_location = locationResult.getLastLocation();

                viewPager = findViewById(R.id.pager);
                //                   setupViewPager2(viewPager);

                //checking if the location tracking is working.
                Log.d("Location_Now", locationResult.getLastLocation().getLatitude() + "/" + locationResult.getLastLocation().getLongitude());
            }

        };

    }


//    private void setupViewPager2(ViewPager viewPager) {
//        ViewPagerAdapter2 adapter2 = new ViewPagerAdapter2(getSupportFragmentManager());
//        adapter2.addFragment(Tab1.getInstance(), "Today");
//        viewPager.setAdapter(adapter2);
//
//    }

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
