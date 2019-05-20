package com.example.mamiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class ShopActivity extends AppCompatActivity {

    private float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }


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



}
