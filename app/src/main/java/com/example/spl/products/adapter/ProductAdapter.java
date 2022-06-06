package com.example.spl.products.adapter;

import android.graphics.Color;
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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {


    List<ProductModel> productList;
    private ItemClickListener itemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView productItemName;
        TextView productItemDate;
        TextView productItemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            productItemName = itemView.findViewById(R.id.productItemName);
            productItemDate = itemView.findViewById(R.id.productItemDate);
            productItemPrice = itemView.findViewById(R.id.productItemPrice);

        }
    }
    public ProductAdapter(List<ProductModel> productList , ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
        this.productList = productList;
    }



    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view  = inflater.inflate(R.layout.single_product_item,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if(position%2==0){
            holder.itemView.setBackgroundColor(Color.parseColor("#f4f7fd"));
        }
        else{
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        setDefaultImage(holder,productList.get(position).getProductCategory());
        holder.productItemName.setText(productList.get(position).getProductName());
        holder.productItemPrice.setText(""+productList.get(position).getProductPrice());
        holder.productItemDate.setText(productList.get(position).getProductDate());


        holder.itemView.setOnClickListener(view->{
            itemClickListener.onItemClick(productList.get(position));
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public interface ItemClickListener{
        void onItemClick(ProductModel productModel);
    }

    public static void setDefaultImage(ViewHolder viewHolder, String type){

        if(type.equals("Electronics")){
            viewHolder.imageView.setImageResource(R.drawable.electornics);
        }
        if(type.equals("Women's Wardrobe")){
            viewHolder.imageView.setImageResource(R.drawable.womenwardrobe);
        }
        if(type.equals("Home")){
            viewHolder.imageView.setImageResource(R.drawable.home);
        }
        if(type.equals("Beauty")){
            viewHolder.imageView.setImageResource(R.drawable.beauty);
        }
        if(type.equals("Health")){
            viewHolder.imageView.setImageResource(R.drawable.health);
        }
        if(type.equals("Automobiles")){
            viewHolder.imageView.setImageResource(R.drawable.automobiles);
        }
        if(type.equals("Watches")){
            viewHolder.imageView.setImageResource(R.drawable.watches);
        }
        if(type.equals("Men's Wardrobe")){
            viewHolder.imageView.setImageResource(R.drawable.menwardrobe);
        }
        if(type.equals("Jewelry")){
            viewHolder.imageView.setImageResource(R.drawable.jewelry);
        }
        if(type.equals("Baby Stuff")){
            viewHolder.imageView.setImageResource(R.drawable.babystuff);
        }
        if(type.equals("Foot wear")){
            viewHolder.imageView.setImageResource(R.drawable.footwear);
        }
        if(type.equals("Bags & Luggage")){
            viewHolder.imageView.setImageResource(R.drawable.bags);
        }
        if(type.equals("Construction and repair")){
            viewHolder.imageView.setImageResource(R.drawable.repair);
        }
        if(type.equals("Pet products")){
            viewHolder.imageView.setImageResource(R.drawable.pet);
        }
        if(type.equals("Hobbies")){
            viewHolder.imageView.setImageResource(R.drawable.hobbies);
        }
        if(type.equals("Garden Supplies")){
            viewHolder.imageView.setImageResource(R.drawable.garden);
        }
        if(type.equals("Office Supplies")){
            viewHolder.imageView.setImageResource(R.drawable.office);
        }
        if(type.equals("School Supplies")){
            viewHolder.imageView.setImageResource(R.drawable.school);
        }
        if(type.equals("Sport")){
            viewHolder.imageView.setImageResource(R.drawable.sports);
        }

        if(type.equals("Bank")){
            viewHolder.imageView.setImageResource(R.drawable.creditcard);
        }

    }

}
