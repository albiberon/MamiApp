package com.example.mamiapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface ProductDao {
    @Insert
    void insert(List<ProductTable> myTable);

    @Query("SELECT * from product_table ")
    List<ProductTable> getWholeList();



}
