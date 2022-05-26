package com.example.spl.stores.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class StoreModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id_store;
    @ColumnInfo(name="storeName")
    private String storeName;
    @ColumnInfo(name="storeDescription")
    private String storeDescription;
    @ColumnInfo(name="storeCountry")
    private String storeCountry;
    @ColumnInfo(name="storeCity")
    private String storeCity;
    @ColumnInfo(name="storeAddress")
    private String storeAddress;
    @ColumnInfo(name="storeCategory")
    private String storeCategory;


    public StoreModel(String storeName, String storeDescription, String storeCountry, String storeCity, String storeAddress, String storeCategory) {
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.storeCountry = storeCountry;
        this.storeCity = storeCity;
        this.storeAddress = storeAddress;
        this.storeCategory = storeCategory;

    }

    @Ignore
    public StoreModel(){
        this.storeName="";
        this.storeAddress="";
        this.storeCategory="";
        this.storeCity="";
        this.storeCountry="";
        this.storeDescription="";
    }

    public int getId_store() {
        return id_store;
    }

    public void setId_store(int id_store) {
        this.id_store = id_store;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public String getStoreCountry() {
        return storeCountry;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    @Override
    public String toString() {
        return "StoreModel{" +
                "id_store=" + id_store +
                ", storeName='" + storeName + '\'' +
                ", storeDescription='" + storeDescription + '\'' +
                ", storeCountry='" + storeCountry + '\'' +
                ", storeCity='" + storeCity + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", storeCategory='" + storeCategory + '\'' +
                '}';
    }
}
