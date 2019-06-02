package com.example.mamiapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mamiapp.Model.Product;

import java.util.List;

public class GeoObjectAdapter extends RecyclerView.Adapter<com.example.mamiapp.GeoObjectViewHolder> {

    //private Context context;
//    public List<GeoObject> listGeoObject;
    private List<Product> products;



    public GeoObjectAdapter(List<Product> products) {
  //      this.context = context;
        this.products = products;
    }

//    public GeoObjectAdapter(List<Product> products) {
//        this.products = products;
//    }

    @NonNull
    @Override
    public GeoObjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cell, parent, false);
        LayoutInflater inflater =LayoutInflater.from(context);
        return new GeoObjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GeoObjectViewHolder holder, final int position) {
        // Gets a single item in the list from its position
        Product product = products.get(position);
        // The holder argument is used to reference the views inside the viewHolder
        // Populate the views with the data from the list
        holder.geoImage.setImageResource(product.getImage());
        holder.geoName.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public void swapList (List<Product> newList) {
        products = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.weatherImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);

        }
    }


}
