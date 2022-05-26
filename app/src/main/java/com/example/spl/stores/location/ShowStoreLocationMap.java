package com.example.spl.stores.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.spl.R;
import com.example.spl.stores.list.ListStoreFragment;
import com.example.spl.stores.model.StoreModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class ShowStoreLocationMap extends AppCompatActivity implements OnMapReadyCallback, Serializable {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private ImageButton backButtonStoreMapView;

    private StoreModel store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_store_location_map);
        getSupportActionBar().hide();

        Bundle mapViewBundle = null;

        if(savedInstanceState!=null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapView = (MapView) findViewById(R.id.mapViewStoreShow);
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(this);

        backButtonStoreMapView = findViewById(R.id.backButtonStoreMapView);

        store = (StoreModel) getIntent().getSerializableExtra("storeMapView");
        backButtonStoreMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        LatLng g = null;

        String address = store.getStoreAddress()+", "+store.getStoreCity()+", "+store.getStoreCountry();
        try{
            g = getLocationFromAddress(getApplicationContext(),address);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(g.latitude,g.longitude)).title(store.getStoreName()));
        }catch (Exception e){
            g = new LatLng(45.125,54.42352);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(g.latitude,g.longitude)).title("Error , default marker set !"));
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(g,15));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }
    @Override
    public void onStart(){
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onStop(){
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if(mapViewBundle==null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY,mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);

    }



}