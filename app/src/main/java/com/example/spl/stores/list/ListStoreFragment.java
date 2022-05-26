package com.example.spl.stores.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.spl.R;
import com.example.spl.stores.database.StoreDatabase;
import com.example.spl.stores.features.StoreDetails;
import com.example.spl.stores.model.StoreModel;
import com.example.spl.stores.adapter.StoreAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListStoreFragment extends Fragment {

    public static RecyclerView rvStores;
    public static StoreAdapter storeAdapter;
    private SearchView searchView;

    public static List<StoreModel> storeList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view =inflater.inflate(R.layout.fragment_liststores,container,false);

        rvStores = view.findViewById(R.id.rvSearch);
        searchView = view.findViewById(R.id.searchView);

        loadData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                storeAdapter.getFilter().filter(s);
                return false;
            }
        });




        return view;
    }

    public void loadData(){
        //Read products from database and add them to recycler view
        storeList = new ArrayList<>();
        StoreDatabase db = Room.databaseBuilder(getContext(), StoreDatabase.class,"stores").allowMainThreadQueries().build();
        storeList = db.storeDAO().getAllStores();

        db.close();


        rvStores.setLayoutManager(new LinearLayoutManager(getContext()));
        storeAdapter = new StoreAdapter(storeList, new StoreAdapter.ItemClickListener() {
            @Override
            public void onItemClick(StoreModel storeModel) {
                Intent intent = new Intent(getContext(), StoreDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("storeItemObject",storeModel);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvStores.setAdapter(storeAdapter);
    }

}
