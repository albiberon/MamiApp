package com.example.mamiapp;

import com.example.mamiapp.R;


public class GeoObject {

    private String mGeoName;
    private double mGeoPrice;
    private int mGeoImageName;

    public GeoObject(String mGeoName, int mGeoImageName, double mGeoPrice) {
        this.mGeoName = mGeoName;
        this.mGeoImageName = mGeoImageName;
        this.mGeoPrice = mGeoPrice;
    }



    public String getmGeoName() {
        return mGeoName;
    }

    public void setmGeoName(String mGeoName) {
        this.mGeoName = mGeoName;
    }


    public void setmGeoPrice (double mGeoPrice){
        this.mGeoPrice = mGeoPrice;
    }

    public int getmGeoImageName() {
        return mGeoImageName;
    }


    public double getmGeoPrice(){
        return mGeoPrice;
    }

    public void setmGeoImageName(int mGeoImageName) {
        this.mGeoImageName = mGeoImageName;
    }

    public static final String[] PRE_DEFINED_GEO_OBJECT_NAMES = {
            "Bergstein, Donkergroen",
            "Bergstein, Geel",
            "Bergstein, Rood",
            "Clockhouse, Zwart-Wit",
            "Hunter, Zwart",
            "Lemon Jelly, Geel",
            "MS Mode, Geel",
            "MS Mode, Rood",
            "Only, Rood"
    };

    public static final int[] PRE_DEFINED_GEO_OBJECT_IMAGE_IDS = {
            R.drawable.bergstein_regenlaarzen_donkergroen_donkergroen_8718191084441,
            R.drawable.bergstein_regenlaarzen_geel_geel_8718191084519,
            R.drawable.bergstein_regenlaarzen_rood_rood_8718191084304,
            R.drawable.clockhouse_regenjas_met_stippen_aszwart_wit_zwart,
            R.drawable.hunter_regenlaarzen_zwart_8719448558982,
            R.drawable.lemon_jelly_chelsea_boots_okergeel_oker_5608736106277,
            R.drawable.ms_mode_regenjas_geel_geel_8719216575616,
            R.drawable.ms_mode_regenjas_rood_rood_8719216575692,
            R.drawable.only_regenjas_rood_rood_5713755997215
    };



    public static final double[] PRE_DEFINED_PRICES = {
            2.55,
            4.95,
            12.15,
            5.95,
            10.00,
            36.25,
            2.25,
            6.69,
            1.25
    };


}
