package com.example.mamiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private ImageView closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productImage = findViewById(R.id.pPhoto);
        productName = findViewById(R.id.pName);
        productPrice = findViewById(R.id.pPrice);
        closeButton = findViewById(R.id.closeButton);


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetailsActivity.this, ShopActivity.class);
                startActivity(i);
            }
        });


        productImage.setImageResource(getIntent().getIntExtra("productImage",0000000));

        //Picasso.get().load(getIntent().getStringExtra("productImage")).into(productImage);
        productName.setText(getIntent().getStringExtra("productName"));
        //productPrice.setText(String.valueOf(getIntent().getStringExtra("productPrice")));
        productPrice.setText(String.valueOf(getIntent().getDoubleExtra("productPrice",0.00)));




    }
}
