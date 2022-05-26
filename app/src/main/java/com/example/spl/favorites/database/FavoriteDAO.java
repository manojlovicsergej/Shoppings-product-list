package com.example.spl.favorites.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.spl.favorites.model.FavoriteModel;

import java.util.List;

@Dao
public interface FavoriteDAO {

    @Query("SELECT * FROM favoritemodel")
    List<FavoriteModel> getAllProducts();

    @Insert
    void insertAll(FavoriteModel... favoriteModels);

    @Query("DELETE FROM favoritemodel WHERE id_product=:id1")
    void deleteFavorite(int id1);

}