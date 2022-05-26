package com.example.spl.stores.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;

import com.example.spl.stores.model.StoreModel;

import java.util.List;

@Dao
public interface StoreDAO {

    @Query("SELECT * FROM storemodel")
    List<StoreModel> getAllStores();

    @Insert
    void insertAll(StoreModel... storeModels);

    @Query("DELETE FROM storemodel WHERE id_store=:id1")
    void deleteProduct(int id1);

}
