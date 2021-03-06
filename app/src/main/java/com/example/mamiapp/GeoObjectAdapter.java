package com.example.mamiapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class GeoObjectAdapter extends RecyclerView.Adapter<com.example.mamiapp.GeoObjectViewHolder> {

    private Context context;
    public List<GeoObject> listGeoObject;

    public GeoObjectAdapter(Context context, List<GeoObject> listGeoObject) {
        this.context = context;
        this.listGeoObject = listGeoObject;
    }

    @NonNull
    @Override
    public GeoObjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cell, parent, false);
        return new GeoObjectViewHolder(view);




    }

    @Override
    public void onBindViewHolder(@NonNull final GeoObjectViewHolder holder, final int position) {
        // Gets a single item in the list from its position
        final GeoObject geoObject = listGeoObject.get(position);
        // The holder argument is used to reference the views inside the viewHolder
        // Populate the views with the data from the list


        holder.geoImage.setImageResource(geoObject.getmGeoImageName());
        holder.geoName.setText(geoObject.getmGeoName());
        holder.geoPrice.setText(String.valueOf(geoObject.getmGeoPrice()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productImage", listGeoObject.get(position).getmGeoImageName());
                intent.putExtra("productName", listGeoObject.get(position).getmGeoName());
                intent.putExtra("productPrice", listGeoObject.get(position).getmGeoPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGeoObject.size();
    }


}