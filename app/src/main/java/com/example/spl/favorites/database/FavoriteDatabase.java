package com.example.spl.favorites.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spl.favorites.model.FavoriteModel;


@Database(entities = {FavoriteModel.class},version = 1)
public abstract class FavoriteDatabase extends RoomDatabase{

    public abstract FavoriteDAO favoriteDAO();

}





