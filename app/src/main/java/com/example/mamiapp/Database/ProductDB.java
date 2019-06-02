package com.example.mamiapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ProductTable.class}, version = 1)
public abstract class ProductDB extends RoomDatabase {

    private static volatile ProductDB INSTANCE;
    public abstract ProductDao productDao();

    static ProductDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductDB.class) {
                if (INSTANCE == null) {
                    // Create database here
                }
            }
        }
        return INSTANCE;
    }
}
