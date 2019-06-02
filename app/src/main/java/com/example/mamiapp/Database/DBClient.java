package com.example.mamiapp.Database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DBClient {

    private Context mCtx;
    private static DBClient mInstance;

    //our app database object
    private ProductDB appDatabase;

    private DBClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, ProductDB.class, "ProductsDB").build();
    }

    public static synchronized DBClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DBClient(mCtx);
        }
        return mInstance;
    }

    public ProductDB getAppDatabase() {
        return appDatabase;
    }
}