package com.example.spl.products.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spl.R;
import com.example.spl.favorites.database.FavoriteDatabase;
import com.example.spl.favorites.model.FavoriteModel;
import com.example.spl.products.model.ProductModel;
import com.example.spl.stores.database.StoreDatabase;
import com.example.spl.stores.location.ShowStoreLocationMap;
import com.example.spl.stores.model.StoreModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDetails extends AppCompatActivity implements Serializable {

    private ImageButton buttonBack;
    private Button buttonCancel;


    private ImageView imageProductView;
    private TextView textProductName;
    private TextView textProductDescription;
    private TextView textProductCategory;
    private TextView textProductColor;
    private TextView textProductDateOfBought;
    private TextView textProductPrice;
    private String productLocation;
    private TextView textProductSize;

    private TextView textStoreName;
    private TextView textStoreDescription;

    private Button buttonFavorites;
    private Button buttonShowProductMapView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().hide();


        ProductModel p = (ProductModel) getIntent().getSerializableExtra("selectedProductObject");

        imageProductView =findViewById(R.id.storeDetailsImageView);
        textProductName = findViewById(R.id.textProductName);
        textProductDescription = findViewById(R.id.textProductDescription);
        textProductCategory = findViewById(R.id.textProductCategory);
        textProductColor = findViewById(R.id.textProductColor);
        textProductDateOfBought = findViewById(R.id.textDateOfBought);
        textProductPrice = findViewById(R.id.textProductPrice);
        productLocation = "";
        textProductSize = findViewById(R.id.textProductSize);
        textStoreName = findViewById(R.id.textStoreName);
        textStoreDescription = findViewById(R.id.textStoreDescription);

        textProductName.setText(p.getProductName());
        textProductDescription.setText(p.getProductDescription());
        textProductCategory.setText(p.getProductCategory());
        textProductColor.setText(p.getProductColor());
        textProductDateOfBought.setText(p.getProductDate());
        textProductPrice.setText(p.getProductPrice()+"");
        textProductSize.setText(p.getProductSize());

        buttonShowProductMapView= findViewById(R.id.buttonShowProductMapView);

        setDefaultImage(p.getProductCategory());


        //yet to be done with product id

        List<StoreModel> listStores = new ArrayList<>();
        StoreDatabase dbStore = Room.databaseBuilder(getApplicationContext(), StoreDatabase.class,"stores").allowMainThreadQueries().build();
        listStores = dbStore.storeDAO().getAllStores();

        dbStore.close();

        StoreModel storeView =new StoreModel();
        for (StoreModel listStore : listStores) {
            if(listStore.getId_store()==p.getId_store()){

                textStoreName.setText(listStore.getStoreName());
                textStoreDescription.setText(listStore.getStoreDescription());
                storeView = listStore;
            }
        }

        StoreModel finalStoreView = storeView;
        buttonShowProductMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowStoreLocationMap.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("storeMapView", finalStoreView);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        buttonBack = findViewById(R.id.buttonBackFavorite);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonFavorites = findViewById(R.id.buttonFavorites);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), ProductsFragment.class));
                finish();
            }
        });

        buttonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FavoriteDatabase db = Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class,"favorites").allowMainThreadQueries().build();
                FavoriteModel fv = new FavoriteModel();
                fv.setProductName(p.getProductName());
                fv.setProductCategory(p.getProductCategory());
                fv.setProductDescription(p.getProductDescription());
                fv.setProductColor(p.getProductColor());
                fv.setProductSize(p.getProductSize());
                fv.setLocationOfBought(p.getLocationOfBought());
                fv.setId_store(p.getId_store());
                fv.setProductPrice(p.getProductPrice());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                fv.setProductDate(sdf.format(new Date()));

                db.favoriteDAO().insertAll(fv);


                finish();

            }
        });




    }

    public void setDefaultImage(String type){

        if(type.equals("Electronics")){
            imageProductView.setImageResource(R.drawable.electornics);
        }
        if(type.equals("Women's Wardrobe")){
            imageProductView.setImageResource(R.drawable.womenwardrobe);
        }
        if(type.equals("Home")){
            imageProductView.setImageResource(R.drawable.home);
        }
        if(type.equals("Beauty")){
            imageProductView.setImageResource(R.drawable.beauty);
        }
        if(type.equals("Health")){
            imageProductView.setImageResource(R.drawable.health);
        }
        if(type.equals("Automobiles")){
            imageProductView.setImageResource(R.drawable.automobiles);
        }
        if(type.equals("Watches")){
            imageProductView.setImageResource(R.drawable.watches);
        }
        if(type.equals("Men's Wardrobe")){
            imageProductView.setImageResource(R.drawable.menwardrobe);
        }
        if(type.equals("Jewelry")){
            imageProductView.setImageResource(R.drawable.jewelry);
        }
        if(type.equals("Baby Stuff")){
            imageProductView.setImageResource(R.drawable.babystuff);
        }
        if(type.equals("Foot wear")){
            imageProductView.setImageResource(R.drawable.footwear);
        }
        if(type.equals("Bags & Luggage")){
            imageProductView.setImageResource(R.drawable.bags);
        }
        if(type.equals("Construction and repair")){
            imageProductView.setImageResource(R.drawable.repair);
        }
        if(type.equals("Pet products")){
            imageProductView.setImageResource(R.drawable.pet);
        }
        if(type.equals("Hobbies")){
            imageProductView.setImageResource(R.drawable.hobbies);
        }
        if(type.equals("Garden Supplies")){
            imageProductView.setImageResource(R.drawable.garden);
        }
        if(type.equals("Office Supplies")){
            imageProductView.setImageResource(R.drawable.office);
        }
        if(type.equals("School Supplies")){
            imageProductView.setImageResource(R.drawable.school);
        }
        if(type.equals("Sport")){
            imageProductView.setImageResource(R.drawable.sports);
        }

    }

}