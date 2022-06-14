package com.example.spl.stores.features;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spl.MainActivity;
import com.example.spl.R;
import com.example.spl.stores.StoreFragment;
import com.example.spl.stores.database.StoreDAO;
import com.example.spl.stores.database.StoreDatabase;
import com.example.spl.stores.list.ListStoreFragment;
import com.example.spl.stores.location.ShowStoreLocationMap;
import com.example.spl.stores.model.StoreModel;

import java.io.File;

public class StoreDetails extends AppCompatActivity {


    private ImageButton buttonBack;
    private ImageButton imageButtonDeleteStore;
    private ImageView storeDetailsImageView;

    private TextView textDetailsStoreName;
    private TextView textDetailsStoreDescription;
    private TextView textDetailsStoreCountry;
    private TextView textDetailsStoreCategory;
    private TextView textDetailsAddress;
    private TextView textDetailsCity;

    private Button buttonShowLocationOnMap;
    private StoreModel s;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_store_details);
        getSupportActionBar().hide();

        s = (StoreModel) getIntent().getSerializableExtra("storeItemObject");

        textDetailsStoreName = findViewById(R.id.textDetailsStoreName);
        textDetailsStoreDescription = findViewById(R.id.textDetailsStoreDescription);
        textDetailsStoreCountry = findViewById(R.id.textDetailsStoreCountry);
        textDetailsStoreCategory = findViewById(R.id.textDetailsStoreCategory);
        textDetailsAddress = findViewById(R.id.textDetailsAddress);
        textDetailsCity = findViewById(R.id.textDetailsCity);
        storeDetailsImageView = findViewById(R.id.storeDetailsImageView);

        textDetailsStoreName.setText(s.getStoreName());
        textDetailsStoreDescription.setText(s.getStoreDescription());
        textDetailsStoreCountry.setText(s.getStoreCountry());
        textDetailsStoreCategory.setText(s.getStoreCategory());
        textDetailsAddress.setText(s.getStoreAddress());
        textDetailsCity.setText(s.getStoreCity());

        setDefaultImage(storeDetailsImageView,s.getStoreCategory());

        buttonShowLocationOnMap = findViewById(R.id.buttonShowLocationOnMap);
        buttonShowLocationOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowStoreLocationMap.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("storeMapView",s);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        buttonBack = findViewById(R.id.buttonBackFavorite);
        imageButtonDeleteStore = findViewById(R.id.imageButtonDeleteStore);

        imageButtonDeleteStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreDatabase db = Room.databaseBuilder(getApplicationContext(),StoreDatabase.class,"stores").allowMainThreadQueries().build();
                StoreDAO storeDAO = db.storeDAO();
                storeDAO.deleteProduct(s.getId_store());
                ListStoreFragment.storeList.remove(s);

                db.close();

                Toast.makeText(getApplicationContext(), "Successfully deleted !" , Toast.LENGTH_SHORT);

                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
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
        if(type.equals("Bank")){
            image.setImageResource(R.drawable.creditcard);
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