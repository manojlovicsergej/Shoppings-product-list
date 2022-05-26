package com.example.spl.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.spl.products.ProductsFragment;
import com.example.spl.products.features.ProductDetails;
import com.example.spl.products.model.ProductModel;
import com.example.spl.search.adapter.SearchAdapter;
import com.example.spl.stores.database.StoreDatabase;
import com.example.spl.stores.model.StoreModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView rvSearch;
    private SearchAdapter searchAdapter;
    private SearchView searchView;

    public static List<StoreModel> listStores = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search,container,false);

        rvSearch = view.findViewById(R.id.rvSearch);

        StoreDatabase dbStore = Room.databaseBuilder(getContext(), StoreDatabase.class,"stores").allowMainThreadQueries().build();
        listStores = dbStore.storeDAO().getAllStores();

        Log.d("s",String.valueOf(listStores));

        //Read products from database and add them to recycler view
        //Read products from database and add them to recycler view

        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(ProductsFragment.productList, new SearchAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ProductModel productModel) {
                Intent intent = new Intent(getContext(), ProductDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedProductObject", productModel);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvSearch.setAdapter(searchAdapter);
        
        searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchAdapter.getFilter().filter(s);
                return false;
            }
        });




        return view;
    }

    public List<StoreModel> getListStores() {
        return listStores;
    }
}
