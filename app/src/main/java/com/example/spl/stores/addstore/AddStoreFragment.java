package com.example.spl.stores.addstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.spl.R;
import com.example.spl.stores.database.StoreDatabase;
import com.example.spl.stores.list.ListStoreFragment;
import com.example.spl.stores.model.StoreModel;

public class AddStoreFragment extends Fragment {

    private Button buttonAdd;

    //Variables for databaseInput

    public EditText nameText = null;
    public EditText descriptionText = null;
    public EditText cityText = null;
    public EditText addressText = null;
    public Spinner countrySpinner = null;
    public Spinner categoriesSpinner = null;

    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addstores,container,false);

        buttonAdd = view.findViewById(R.id.buttonAdd);
        this.nameText =view.findViewById(R.id.productName);
        this.descriptionText = view.findViewById(R.id.productDescription);
        this.cityText = view.findViewById(R.id.cityText);
        this.addressText = view.findViewById(R.id.addressText);
        this.countrySpinner = view.findViewById(R.id.colorSpinner);
        this.categoriesSpinner = view.findViewById(R.id.categorySpinner);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStoreToDatabase();
            }
        });


        return view;
    }
    public void resetFields(){
        nameText.setText("");
        cityText.setText("");
        addressText.setText("");
        descriptionText.setText("");
    }
    public void addStoreToDatabase(){
        StoreDatabase db = Room.databaseBuilder(getContext(),StoreDatabase.class,"stores").allowMainThreadQueries().build();
        StoreModel sd = new StoreModel(nameText.getText().toString(),descriptionText.getText().toString(),countrySpinner.getSelectedItem().toString(),cityText.getText().toString(),addressText.getText().toString(),categoriesSpinner.getSelectedItem().toString());
        db.storeDAO().insertAll(sd);

        db.close();

        Toast.makeText(getContext(),"Added successfully !",Toast.LENGTH_SHORT).show();
        resetFields();
    }
}
