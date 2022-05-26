package com.example.spl.products.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spl.products.model.ProductModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Database(entities = {ProductModel.class},version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductDAO productDAO();

}





