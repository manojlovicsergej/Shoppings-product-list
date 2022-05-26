package com.example.spl.products.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.spl.products.model.ProductModel;

import java.util.List;

@Dao
public interface ProductDAO {
    @Query("SELECT * FROM productmodel")
    List<ProductModel> getAllProducts();

    @Insert
    void insertAll(ProductModel... productModels);

    @Query("DELETE FROM productmodel WHERE id_product=:id1")
    void deleteProduct(int id1);

}
