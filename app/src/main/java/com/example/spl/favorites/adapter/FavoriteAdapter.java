package com.example.spl.favorites.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.spl.R;
import com.example.spl.favorites.FavoriteFragment;
import com.example.spl.favorites.database.FavoriteDAO;
import com.example.spl.favorites.database.FavoriteDatabase;
import com.example.spl.favorites.model.FavoriteModel;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private List<FavoriteModel> favoriteList;
    private ItemClickListener itemClickListener;


    public FavoriteAdapter(List<FavoriteModel> favoriteList , ItemClickListener itemClickListener){
        this.favoriteList = favoriteList;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_favorite_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        setDefaultImage(holder,favoriteList.get(position).getProductCategory());
        holder.productNameFavorite.setText(favoriteList.get(position).getProductName());
        holder.productPriceFavorite.setText(favoriteList.get(position).getProductPrice()+"");

        holder.buttonDeleteFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteDatabase db = Room.databaseBuilder(holder.itemView.getContext(), FavoriteDatabase.class,"favorites").allowMainThreadQueries().build();
                FavoriteDAO favoriteDAO = db.favoriteDAO();
                favoriteDAO.deleteFavorite(favoriteList.get(position).getId_product());

                Toast.makeText(view.getContext(), "Successfully deleted !" , Toast.LENGTH_SHORT);
                FavoriteFragment.favoriteModelList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(favoriteList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewFavorite;
        TextView productNameFavorite;
        TextView productPriceFavorite;
        Button buttonDeleteFavorite;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
            productNameFavorite = itemView.findViewById(R.id.productNameFavorite);
            productPriceFavorite = itemView.findViewById(R.id.productPriceFavorite);
            buttonDeleteFavorite = itemView.findViewById(R.id.buttonDeleteFavorite);

        }

    }

    public interface ItemClickListener{
        void onItemClick(FavoriteModel favoriteModel);
    }

    public static void setDefaultImage(ViewHolder viewHolder, String type){

        if(type.equals("Electronics")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.electornics);
        }
        if(type.equals("Women's Wardrobe")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.womenwardrobe);
        }
        if(type.equals("Home")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.home);
        }
        if(type.equals("Beauty")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.beauty);
        }
        if(type.equals("Health")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.health);
        }
        if(type.equals("Automobiles")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.automobiles);
        }
        if(type.equals("Watches")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.watches);
        }
        if(type.equals("Men's Wardrobe")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.menwardrobe);
        }
        if(type.equals("Jewelry")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.jewelry);
        }
        if(type.equals("Baby Stuff")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.babystuff);
        }
        if(type.equals("Foot wear")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.footwear);
        }
        if(type.equals("Bags & Luggage")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.bags);
        }
        if(type.equals("Construction and repair")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.repair);
        }
        if(type.equals("Pet products")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.pet);
        }
        if(type.equals("Hobbies")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.hobbies);
        }
        if(type.equals("Garden Supplies")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.garden);
        }
        if(type.equals("Office Supplies")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.office);
        }
        if(type.equals("School Supplies")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.school);
        }
        if(type.equals("Sport")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.sports);
        }
        if(type.equals("Bank")){
            viewHolder.imageViewFavorite.setImageResource(R.drawable.creditcard);
        }

    }


}
