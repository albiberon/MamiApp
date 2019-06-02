package com.example.mamiapp.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static final String APP_ID = "be545df06e4d6c3621ae3c774a0747fe";
    public static Location current_location = null;


    public static String convertUnixToDate(long dt) {

        Date date = new Date(dt * 1000L);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm EEE MM yyyy");
        String formattedDate = formatter.format(date);

        return formattedDate;

    }

}


