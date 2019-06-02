package com.example.mamiapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mamiapp.Model.Product;


@Database(entities = {Product.class}, version= 1, exportSchema = false)
public abstract class ProductRoomDatabase extends RoomDatabase {

    private final static String NAME_DATABASE = "product_databse";

    public abstract ProductDao productDao();

    private static volatile ProductRoomDatabase INSTANCE;

    public static ProductRoomDatabase getDatabase(final Context context) {
        if  (INSTANCE == null ){
            synchronized (ProductRoomDatabase.class){
                if(INSTANCE ==null) {
                    //Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ProductRoomDatabase.class, NAME_DATABASE).build();
                }
            }
        }

        return INSTANCE;
    }
}
