package com.example.spl.products.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spl.R;
import com.example.spl.products.model.ProductModel;

import java.util.List;

public class ProductAdapterRecently extends RecyclerView.Adapter<ProductAdapterRecently.ViewHolder> {

    List<ProductModel> productList ;


    public ProductAdapterRecently(List<ProductModel> productList){
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapterRecently.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_product_recently_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        setDefaultImage(holder,productList.get(position).getProductCategory());
        holder.textViewProductPrice.setText(productList.get(position).getProductPrice()+"");
        holder.textViewProductSize.setText(productList.get(position).getProductSize());
        holder.textViewProductName.setText(productList.get(position).getProductName());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewProductPicture;
        TextView textViewProductName;
        TextView textViewProductSize;
        TextView textViewProductPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProductPicture = itemView.findViewById(R.id.imageViewFavorite);
            textViewProductName = itemView.findViewById(R.id.productNameFavorite);
            textViewProductSize = itemView.findViewById(R.id.productPriceFavorite);
            textViewProductPrice = itemView.findViewById(R.id.textViewPrice);



        }
    }

    public static void setDefaultImage(ViewHolder viewHolder, String type){

        if(type.equals("Electronics")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.electornics);
        }
        if(type.equals("Women's Wardrobe")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.womenwardrobe);
        }
        if(type.equals("Home")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.home);
        }
        if(type.equals("Beauty")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.beauty);
        }
        if(type.equals("Health")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.health);
        }
        if(type.equals("Automobiles")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.automobiles);
        }
        if(type.equals("Watches")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.watches);
        }
        if(type.equals("Men's Wardrobe")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.menwardrobe);
        }
        if(type.equals("Jewelry")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.jewelry);
        }
        if(type.equals("Baby Stuff")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.babystuff);
        }
        if(type.equals("Foot wear")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.footwear);
        }
        if(type.equals("Bags & Luggage")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.bags);
        }
        if(type.equals("Construction and repair")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.repair);
        }
        if(type.equals("Pet products")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.pet);
        }
        if(type.equals("Hobbies")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.hobbies);
        }
        if(type.equals("Garden Supplies")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.garden);
        }
        if(type.equals("Office Supplies")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.office);
        }
        if(type.equals("School Supplies")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.school);
        }
        if(type.equals("Sport")){
            viewHolder.imageViewProductPicture.setImageResource(R.drawable.sports);
        }

    }


}
