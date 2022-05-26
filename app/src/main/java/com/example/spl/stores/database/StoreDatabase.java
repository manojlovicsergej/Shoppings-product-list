package com.example.spl.stores.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spl.stores.model.StoreModel;

@Database(entities = {StoreModel.class},version = 1)
public abstract class StoreDatabase extends RoomDatabase {
    public abstract StoreDAO storeDAO();
}


