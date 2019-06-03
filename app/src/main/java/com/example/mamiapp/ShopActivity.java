package com.example.mamiapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;


import com.example.mamiapp.Database.DBClient;
import com.example.mamiapp.Database.ProductTable;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private float x1, x2, y1, y2;

    private RecyclerView mGeoRecyclerView;
    private List<GeoObject> mGeoObjects;
    private GestureDetector mGestureDetector;
    private CardView cardView;

    private List<ProductTable> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mGeoObjects = new ArrayList<>();
        mGeoRecyclerView = findViewById(R.id.recyclerView);
        cardView = findViewById(R.id.productCell);

        getTasks();



        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


    }


    private void getTasks() {

        class GetTasks extends AsyncTask<Void, Void, List<ProductTable>> {

            @Override
            protected List<ProductTable> doInBackground(Void... voids) {
                productList = DBClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .productDao()
                        .getWholeList();


                RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

                if (!productList.isEmpty()) {

                    for (int i = 0; i < productList.size(); i++) {

                        mGeoObjects.add(new GeoObject(productList.get(i).getGeoName(), productList.get(i).getGeoImageName(), productList.get(i).getGeoPrice()));
                    }

                }
                mGeoRecyclerView.setLayoutManager(mLayoutManager);
                mGeoRecyclerView.setHasFixedSize(true);
                GeoObjectAdapter mAdapter = new GeoObjectAdapter(ShopActivity.this, mGeoObjects);
                mGeoRecyclerView.setAdapter(mAdapter);


                return productList;
            }

            @Override
            protected void onPostExecute(List<ProductTable> tasks) {
                super.onPostExecute(tasks);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
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
                if (y2 > y1) {
                    Intent i = new Intent(ShopActivity.this, MainActivity.class);
                    startActivity(i);
                }
                break;

        }
        return false;

    }



}



