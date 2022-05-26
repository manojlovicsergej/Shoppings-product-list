package com.example.spl.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.spl.R;
import com.example.spl.favorites.database.FavoriteDatabase;
import com.example.spl.favorites.features.FavoriteDetails;
import com.example.spl.favorites.model.FavoriteModel;
import com.example.spl.favorites.adapter.FavoriteAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView rvFavorites;
    private FavoriteAdapter rvAdapter;
    public static List<FavoriteModel> favoriteModelList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites,container,false);


        rvFavorites = view.findViewById(R.id.rvFavorites);
        loadDatabase();


        rvAdapter = new FavoriteAdapter(favoriteModelList, new FavoriteAdapter.ItemClickListener() {
            @Override
            public void onItemClick(FavoriteModel favoriteModel) {
                Intent intent = new Intent(getContext(), FavoriteDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedFavoriteObject", favoriteModel);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        rvFavorites.setLayoutManager(gridLayoutManager);
        rvFavorites.setAdapter(rvAdapter);

        return view;
    }
    public void loadDatabase(){
        FavoriteDatabase dbFavorite = Room.databaseBuilder(getContext(), FavoriteDatabase.class,"favorites").allowMainThreadQueries().build();
        favoriteModelList = dbFavorite.favoriteDAO().getAllProducts();
        dbFavorite.close();
    }

}
