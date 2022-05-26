package com.example.spl.locations;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.spl.MainActivity;
import com.example.spl.R;
import com.example.spl.stores.database.StoreDatabase;
import com.example.spl.stores.list.ListStoreFragment;
import com.example.spl.stores.model.StoreModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 44;
    private FusedLocationProviderClient mFusedLocationClient;

    List<StoreModel> storeList = new ArrayList<>();
    List<StoreModel> temporaryStoreList = new ArrayList<>();


    private Button buttonFindNearest;
    private Button buttonResetMarkers;

    private double geoLatitude;
    private double geoLongitude;

    private GoogleMap googleMap;

    private Spinner categorySpinner;


    private List<MarkerOptions> listOfMarkers = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location,container,false);

        buttonResetMarkers = view.findViewById(R.id.buttonResetMarkers);
        buttonFindNearest = view.findViewById(R.id.buttonFindNearest);
        categorySpinner = view.findViewById(R.id.categorySpinner2);



        Bundle mapViewBundle = null;

        if(savedInstanceState!=null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        loadData();

        mapView = (MapView) view.findViewById(R.id.mapViewLocation);
        mapView.onCreate(mapViewBundle);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mapView.getMapAsync(this);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                googleMap.clear();
                temporaryStoreList.clear();
                for(int i = 0; i < storeList.size(); i++){
                    if(storeList.get(i).getStoreCategory().equals(categorySpinner.getSelectedItem().toString())) {
                        String address = storeList.get(i).getStoreAddress() + ", " + storeList.get(i).getStoreCity() + ", " + storeList.get(i).getStoreCountry();

                        LatLng g = null;
                        try {
                            g = getLocationFromAddress(getContext(), address);
                            String title = storeList.get(i).getStoreName();
                            MarkerOptions mo = new MarkerOptions().position(new LatLng(g.latitude, g.longitude)).title(title).snippet(storeList.get(i).getStoreCategory());

                            mo = checkMarkerColor(mo,storeList.get(i).getStoreCategory());



                            googleMap.addMarker(mo);

                        } catch (Exception e) {
                            Log.d("s", "Error");
                        }

                        temporaryStoreList.add(storeList.get(i));
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonFindNearest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMap.clear();

                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());


                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();


                    MarkerOptions mo;
                    mo = getNearestLocationStoreMarker(temporaryStoreList);
                    if(mo == null){
                       Toast.makeText(getContext(),"No selected stores on map !",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        googleMap.addMarker(mo);
                    }



                } else{
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }



            }
        });



        buttonResetMarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allStores();
            }

        });
        getLocation();

        return view;
    }

    public MarkerOptions getNearestLocationStoreMarker(List<StoreModel> lista){
        MarkerOptions mo =null;
        LatLng g ;
        LatLng gSave = null;
        double sirina = 0 ;
        double duzina = 0 ;
        double razdaljina = 0;
        double razdaljina1 = 0;
        int k = 0 ;
        double geoLatitude = this.geoLatitude;
        double geoLongitude = this.geoLongitude;



        for(int i  = 0 ; i < lista.size();i++){
            String address = lista.get(i).getStoreAddress() + ", "+lista.get(i).getStoreCity()+", "+lista.get(i).getStoreCountry();
            g = getLocationFromAddress(getContext(),address);


            if(i == 0){
                if(geoLatitude >= g.latitude){
                    sirina = geoLatitude-g.latitude;
                }else{
                    sirina = g.latitude-geoLatitude;
                }
                if(geoLongitude >= g.longitude){
                    duzina = geoLongitude-g.longitude;
                }else{
                    duzina = g.longitude-geoLongitude;
                }
                razdaljina = sirina + duzina ;
                k = i;
                gSave = new LatLng(g.latitude,g.longitude);
            }else{

                if(geoLatitude >= g.latitude){
                    sirina = geoLatitude-g.latitude;
                }else{
                    sirina = g.latitude-geoLatitude;
                }
                if(geoLongitude >= g.longitude){
                    duzina = geoLongitude-g.longitude;
                }else{
                    duzina = g.longitude-geoLongitude;
                }

                razdaljina1 = sirina+duzina;

                if(razdaljina1 < razdaljina){
                    razdaljina = razdaljina1;
                    k = i;
                    gSave = new LatLng(g.latitude,g.longitude);
                }

            }

        }



        if(gSave!=null){
            mo =  new MarkerOptions().position(new LatLng(gSave.latitude,gSave.longitude)).title(lista.get(k).getStoreName()).snippet(lista.get(k).getStoreCategory());
            mo = checkMarkerColor(mo,lista.get(k).getStoreCategory());
        }

        return mo;
    }

    private void resetTemporaryStoreList(List<StoreModel> lista,List<StoreModel>lista1){
        lista.clear();
        lista.addAll(lista1);
    }

    public MarkerOptions checkMarkerColor(MarkerOptions mo,String category){
        if(category.equals("Electronics")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        }
        if(category.equals("Women's Wardrobe")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        }
        if(category.equals("Home")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
        if(category.equals("Beauty")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        }
        if(category.equals("Health")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        if(category.equals("Automobiles")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        }
        if(category.equals("Watches")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        }
        if(category.equals("Men's Wardrobe")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        }
        if(category.equals("Jewelry")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        }
        if(category.equals("Baby Stuff")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        if(category.equals("Foot wear")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
        if(category.equals("Bags & Luggage")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        if(category.equals("Construction and repair")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        }
        if(category.equals("Pet products")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        }
        if(category.equals("Hobbies")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        }
        if(category.equals("Garden Supplies")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
        if(category.equals("Office Supplies")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        if(category.equals("School Supplies")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        }
        if(category.equals("Sport")){
            mo.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        }

        return mo;
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {


        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location!=null){
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        geoLongitude = addressList.get(0).getLongitude();
                        geoLatitude = addressList.get(0).getLatitude();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void loadData(){

        storeList.clear();

        StoreDatabase db = Room.databaseBuilder(getContext(), StoreDatabase.class,"stores").allowMainThreadQueries().build();
        storeList = db.storeDAO().getAllStores();

        db.close();

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

    public void allStores(){
        googleMap.clear();
        for(int i = 0; i < storeList.size(); i++){
            String address = storeList.get(i).getStoreAddress() + ", "+storeList.get(i).getStoreCity()+", "+storeList.get(i).getStoreCountry();

            LatLng g = null;
            try{
                g = getLocationFromAddress(getContext(),address);
                String title =  storeList.get(i).getStoreName();
                MarkerOptions mo = new MarkerOptions().position(new LatLng(g.latitude,g.longitude)).title(title).snippet(storeList.get(i).getStoreCategory());
                mo = checkMarkerColor(mo,storeList.get(i).getStoreCategory());
                googleMap.addMarker(mo);

                listOfMarkers.add(mo);

            }catch(Exception e){
                Log.d("s","Error");
            }

        }

        resetTemporaryStoreList(temporaryStoreList,storeList);
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        this.googleMap = googleMap;

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


}
