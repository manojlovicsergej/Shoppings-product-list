package com.example.spl.products.addproduct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.spl.MainActivity;
import com.example.spl.R;
import com.example.spl.products.ProductsFragment;
import com.example.spl.products.database.ProductDatabase;
import com.example.spl.products.model.ProductModel;
import com.example.spl.stores.StoreFragment;
import com.example.spl.stores.database.StoreDatabase;
import com.example.spl.stores.list.ListStoreFragment;
import com.example.spl.stores.model.StoreModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {

    private ImageButton backButton;
    int SELECT_IMAGE_CODE = 1;

    //Product info tab
    private EditText productName;
    private Spinner colorSpinner;
    private EditText productSize;
    private Spinner categorySpinner;
    //Location info tab
    private Spinner storeSpinner;
    //Additional info tab
    private EditText productPrice;

    private EditText productDescription;

    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Initialize rest variables
        productName = findViewById(R.id.productName);
        colorSpinner = findViewById(R.id.colorSpinner);
        productSize = findViewById(R.id.productSize);
        categorySpinner = findViewById(R.id.categorySpinner);
        storeSpinner = findViewById(R.id.storeSpinner);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);

        addButton = findViewById(R.id.addButton);


        //Adding stores to storeSpinner

        List<StoreModel> lista = new ArrayList<>();
        StoreDatabase dbStore = Room.databaseBuilder(getApplicationContext(), StoreDatabase.class,"stores").allowMainThreadQueries().build();
        lista = dbStore.storeDAO().getAllStores();

        dbStore.close();

        ArrayList<String> listStoreName = new ArrayList<>();
        lista.forEach(e->{
            listStoreName.add(e.getStoreName());
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listStoreName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        storeSpinner.setAdapter(arrayAdapter);

        //

        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),ProductDatabase.class,"products").allowMainThreadQueries().build();
        List<StoreModel> finalLista = new ArrayList<>();
        finalLista.addAll(lista);

        db.close();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String locationOfBought = "";
                    int id_store = 0;
                    double price = 0;

                    if(storeSpinner==null || storeSpinner.getSelectedItem() == null){
                        Toast.makeText(AddProductActivity.this,"To enter product please enter store first !",Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            price = Double.parseDouble(productPrice.getText().toString());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

                            //Searching for store ID based on Store Name
                            String find = storeSpinner.getSelectedItem().toString();

                            for (StoreModel storeModel : finalLista) {

                                if (storeModel.getStoreName().trim().equals(find.trim())) {
                                    id_store = storeModel.getId_store();
                                }
                            }


                            ProductModel pd = new ProductModel(productName.getText().toString(), categorySpinner.getSelectedItem().toString(), productDescription.getText().toString(), colorSpinner.getSelectedItem().toString(), productSize.getText().toString(), locationOfBought, id_store, price, sdf.format(new Date()));
                            db.productDAO().insertAll(pd);


                            startActivity(new Intent(AddProductActivity.this, MainActivity.class));

                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Price has to be number value !", Toast.LENGTH_SHORT).show();
                        }
                    }






            }
        });






    }


}