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

import com.example.mamiapp.Database.ProductRoomDatabase;
import com.example.mamiapp.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShopActivity extends AppCompatActivity {

    private float x1, x2, y1, y2;


    private List<Product> products;
    private GestureDetector mGestureDetector;
    private ProductRoomDatabase db;
    private RecyclerView mRecyclerView;
    private GeoObjectAdapter mAdapter;
    private Executor executor = Executors.newSingleThreadExecutor();

    //will be necessary for product details.
    public static final String EXTRA_PRODUCT = "Product";
    public static final int REQUESTCODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        setContentView(R.layout.activity_main);
        db = ProductRoomDatabase.getDatabase(this);


        mRecyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        products = new ArrayList<>();


        GeoObjectAdapter mAdapter = new GeoObjectAdapter(products);
        mRecyclerView.setAdapter(mAdapter);


        //getAllProducts();


        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });



    }

//    private void updateUI() {
//        if (mAdapter == null) {
//            mAdapter = new GeoObjectAdapter(this.products);
//            mRecyclerView.setAdapter(mAdapter);
//        } else {
//            mAdapter.swapList(products);
//        }
//    }


//    private void getAllProducts() {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                products = db.productDao().getAllProducts();
//
//                // In a background thread the user interface cannot be updated from this thread.
//                // This method will perform statements on the main thread again.
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        updateUI();
//                    }
//                });
//            }
//        });
//    }

//    private void populateDB(){
//        Product product1 = new Product("Product1",9.90, R.drawable.bergstein_regenlaarzen_donkergroen_donkergroen_8718191084441, "Loremipsum");
//                db.productDao().insertProduct(product1);
//    }

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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUESTCODE) {
//            if (resultCode == RESULT_OK) {
//                Product updatedReminder = data.getParcelableExtra(MainActivity.EXTRA);
//                // New timestamp: timestamp of update
//                updateProduct(updatedProduct);
//            }
//        }
//    }





    

}


