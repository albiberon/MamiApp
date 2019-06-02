package com.example.mamiapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "product_table")
public class ProductTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;


    @ColumnInfo(name = "GeoName")
    private String GeoName;

    @ColumnInfo(name = "GeoPrice")
    private double GeoPrice;

    @ColumnInfo(name = "GeoImageName")
    private int GeoImageName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGeoName() {
        return GeoName;
    }

    public void setGeoName(String geoName) {
        GeoName = geoName;
    }

    public double getGeoPrice() {
        return GeoPrice;
    }

    public void setGeoPrice(double geoPrice) {
        GeoPrice = geoPrice;
    }

    public int getGeoImageName() {
        return GeoImageName;
    }

    public void setGeoImageName(int geoImageName) {
        GeoImageName = geoImageName;
    }
}