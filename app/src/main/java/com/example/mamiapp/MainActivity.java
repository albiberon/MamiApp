package com.example.mamiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private float x1, x2, y1, y2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView cityName;
    private TextView temperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //method for initialisation
        cityName = findViewById(R.id.locationName);
        temperature = findViewById(R.id.temperature);
        init();
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
