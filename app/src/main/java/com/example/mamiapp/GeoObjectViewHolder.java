package com.example.mamiapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mamiapp.R;

public class GeoObjectViewHolder extends RecyclerView.ViewHolder {

    public TextView geoName;
    public TextView geoPrice;
    public ImageView geoImage;
    public View view;
    CardView cardView;

    public GeoObjectViewHolder(@NonNull View itemView) {
        super(itemView);
        geoImage = itemView.findViewById(R.id.geoImageView);
        geoName = itemView.findViewById(R.id.productName);
        geoPrice = itemView.findViewById(R.id.productPrice);
        cardView = itemView.findViewById(R.id.productCell);

        view = itemView;
    }

}
