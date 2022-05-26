package com.example.spl.favorites.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spl.R;
import com.example.spl.favorites.model.FavoriteModel;
import com.example.spl.stores.database.StoreDatabase;
import com.example.spl.stores.location.ShowStoreLocationMap;
import com.example.spl.stores.model.StoreModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDetails extends AppCompatActivity {

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

    private ImageButton buttonBackFavorite;
    
    private Button buttonShowProductMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_favorite_details);
        getSupportActionBar().hide();

        FavoriteModel p = (FavoriteModel) getIntent().getSerializableExtra("selectedFavoriteObject");

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
        buttonBackFavorite = findViewById(R.id.buttonBackFavorite);
        buttonShowProductMapView = findViewById(R.id.buttonShowProductMapView);

        setDefaultImage(imageProductView,p.getProductCategory());
        

        //yet to be done with product id

        List<StoreModel> listStores = new ArrayList<>();
        StoreDatabase dbStore = Room.databaseBuilder(getApplicationContext(), StoreDatabase.class,"stores").allowMainThreadQueries().build();
        listStores = dbStore.storeDAO().getAllStores();

        dbStore.close();

        StoreModel storeView = new StoreModel();
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


        buttonBackFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }

    public static void setDefaultImage( ImageView image ,String type){

        if(type.equals("Electronics")){
            image.setImageResource(R.drawable.electornics);
        }
        if(type.equals("Women's Wardrobe")){
            image.setImageResource(R.drawable.womenwardrobe);
        }
        if(type.equals("Home")){
            image.setImageResource(R.drawable.home);
        }
        if(type.equals("Beauty")){
            image.setImageResource(R.drawable.beauty);
        }
        if(type.equals("Health")){
            image.setImageResource(R.drawable.health);
        }
        if(type.equals("Automobiles")){
            image.setImageResource(R.drawable.automobiles);
        }
        if(type.equals("Watches")){
            image.setImageResource(R.drawable.watches);
        }
        if(type.equals("Men's Wardrobe")){
            image.setImageResource(R.drawable.menwardrobe);
        }
        if(type.equals("Jewelry")){
            image.setImageResource(R.drawable.jewelry);
        }
        if(type.equals("Baby Stuff")){
            image.setImageResource(R.drawable.babystuff);
        }
        if(type.equals("Foot wear")){
            image.setImageResource(R.drawable.footwear);
        }
        if(type.equals("Bags & Luggage")){
            image.setImageResource(R.drawable.bags);
        }
        if(type.equals("Construction and repair")){
            image.setImageResource(R.drawable.repair);
        }
        if(type.equals("Pet products")){
            image.setImageResource(R.drawable.pet);
        }
        if(type.equals("Hobbies")){
            image.setImageResource(R.drawable.hobbies);
        }
        if(type.equals("Garden Supplies")){
            image.setImageResource(R.drawable.garden);
        }
        if(type.equals("Office Supplies")){
            image.setImageResource(R.drawable.office);
        }
        if(type.equals("School Supplies")){
            image.setImageResource(R.drawable.school);
        }
        if(type.equals("Sport")){
            image.setImageResource(R.drawable.sports);
        }

    }
}