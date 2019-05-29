package com.example.mamiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private float x1, x2, y1, y2;


    List<GeoObject> mGeoObjects;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mGeoObjects = new ArrayList<>();


        for (int i = 0; i < GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES.length; i++) {
            mGeoObjects.add(new GeoObject(GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES[i], GeoObject.PRE_DEFINED_GEO_OBJECT_IMAGE_IDS[i], GeoObject.PRE_DEFINED_PRICES[i]));
        }

        final RecyclerView mGeoRecyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        mGeoRecyclerView.setLayoutManager(mLayoutManager);
        mGeoRecyclerView.setHasFixedSize(true);
        GeoObjectAdapter mAdapter = new GeoObjectAdapter(this, mGeoObjects);
        mGeoRecyclerView.setAdapter(mAdapter);

//        mGeoRecyclerView.addOnItemTouchListener((RecyclerView.OnItemTouchListener) this);


        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


    }

//    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//        View child = rv.findChildViewUnder(e.getX(), e.getY());
//        int mAdapterPosition = rv.getChildAdapterPosition(child);
//        if (child != null && mGestureDetector.onTouchEvent(e)) {
//            Toast.makeText(this, mGeoObjects.get(mAdapterPosition).getmGeoName(), Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }


    public boolean onTouchEvent(MotionEvent touchEvent){
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2= touchEvent.getX();
                y2= touchEvent.getY();
                if(y2>y1) {
                    Intent i = new Intent(ShopActivity.this, MainActivity.class);
                    startActivity(i);
                }
                break;

        }
        return false;

    }



    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }



}
