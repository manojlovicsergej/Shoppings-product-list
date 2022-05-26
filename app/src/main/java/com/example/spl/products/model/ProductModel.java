package com.example.spl.products.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ProductModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id_product;

    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "productCategory")
    private String productCategory;

    @ColumnInfo(name = "productDescription")
    private String productDescription;

    @ColumnInfo(name = "productColor")
    private String productColor;

    @ColumnInfo(name = "productSize")
    private String productSize;

    @ColumnInfo(name = "locationOfBought")
    private String locationOfBought;

    @ColumnInfo(name = "id_store")
    private int id_store;

    @ColumnInfo(name = "productPrice")
    private double productPrice;

    @ColumnInfo(name = "productDate")
    private String productDate;


    public ProductModel(String productName, String productCategory, String productDescription, String productColor, String productSize, String locationOfBought, int id_store, double productPrice, String productDate) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productColor = productColor;
        this.productSize = productSize;
        this.locationOfBought = locationOfBought;
        this.id_store = id_store;
        this.productPrice = productPrice;
        this.productDate = productDate;

    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getLocationOfBought() {
        return locationOfBought;
    }

    public void setLocationOfBought(String locationOfBought) {
        this.locationOfBought = locationOfBought;
    }

    public int getId_store() {
        return id_store;
    }

    public void setId_store(int id_store) {
        this.id_store = id_store;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDate() {
        return productDate;
    }



    @Override
    public String toString() {
        return "ProductModel{" +
                "id_product=" + id_product +
                ", productName='" + productName + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productColor='" + productColor + '\'' +
                ", productSize='" + productSize + '\'' +
                ", locationOfBought='" + locationOfBought + '\'' +
                ", id_store=" + id_store +
                ", productPrice=" + productPrice +
                ", productDate='" + productDate + '\''+
                '}';
    }
}
