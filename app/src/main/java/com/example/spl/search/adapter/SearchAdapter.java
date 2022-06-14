package com.example.spl.search.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.spl.R;
import com.example.spl.products.ProductsFragment;
import com.example.spl.products.database.ProductDAO;
import com.example.spl.products.database.ProductDatabase;
import com.example.spl.products.model.ProductModel;
import com.example.spl.search.SearchFragment;
import com.example.spl.stores.list.ListStoreFragment;
import com.example.spl.stores.model.StoreModel;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {

    List<ProductModel> productListFull;
    List<ProductModel> productList;

    ListStoreFragment ls = new ListStoreFragment();
    List<StoreModel> listStores = new ArrayList<>();

    private ItemClickListener itemClickListener;
    private SearchFragment ma = new SearchFragment();

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater  = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_search_item,parent,false);

        return new ViewHolder(view);
    }

    public SearchAdapter(List<ProductModel> productList , ItemClickListener itemClickListener){
        this.productList = productList;
        productListFull = new ArrayList<>(productList);
        this.itemClickListener  = itemClickListener;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(position%2==0){
            holder.itemView.setBackgroundColor(Color.parseColor("#f4f7fd"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        setDefaultImage(holder,ProductsFragment.productList.get(position).getProductCategory());
        holder.searchSizeImageView.setImageResource(R.drawable.ic_baseline_height_24);
        holder.searchStoreImageView.setImageResource(R.drawable.ic_baseline_store_24);
        holder.searchDateImageView.setImageResource(R.drawable.ic_baseline_calendar_month_24);

        holder.searchProductName.setText(ProductsFragment.productList.get(position).getProductName());
        holder.searchProductPrice.setText(ProductsFragment.productList.get(position).getProductPrice()+"");
        holder.searchProductDate.setText(ProductsFragment.productList.get(position).getProductDate());


        for(StoreModel storeList: SearchFragment.listStores){
            if(storeList.getId_store()==ProductsFragment.productList.get(position).getId_store()){
                holder.searchProductStore.setText(storeList.getStoreName());
            }
        }

        //holder.searchProductStore.setText(ProductsFragment.productList.get(position).getId_store()+"");


        holder.searchProductSize.setText(ProductsFragment.productList.get(position).getProductSize());

        holder.itemView.setOnClickListener(view->{
            itemClickListener.onItemClick(productList.get(position));
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDatabase db = Room.databaseBuilder(holder.itemView.getContext(),ProductDatabase.class,"products").allowMainThreadQueries().build();
                ProductDAO productDAO = db.productDAO();
                productDAO.deleteProduct(ProductsFragment.productList.get(position).getId_product());
                ProductsFragment.productList.remove(position);

                Toast.makeText(view.getContext(), "Successfully deleted !" , Toast.LENGTH_SHORT);

                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return productListFilter;
    }

    private Filter productListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ProductModel> filterList = new ArrayList<>();

            if(charSequence == null || charSequence.length()==0){
                filterList.addAll(productListFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                productListFull.forEach((e)->{
                    if(e.getProductName().toLowerCase().trim().contains(filterPattern)){
                        filterList.add(e);
                    }

                });
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            productList.clear();
            productList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };




    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView searchProductImageView;
        ImageView searchSizeImageView;
        ImageView searchStoreImageView;
        ImageView searchDateImageView;

        TextView searchProductName;
        TextView searchProductPrice;
        TextView searchProductDate;
        TextView searchProductStore;
        TextView searchProductSize;

        ImageButton buttonDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            searchProductImageView = itemView.findViewById(R.id.searchImageView);
            searchSizeImageView = itemView.findViewById(R.id.searchSizeImageView);
            searchStoreImageView = itemView.findViewById(R.id.searchStoreImageView);
            searchDateImageView = itemView.findViewById(R.id.searchCalendarImageView);

            searchProductName = itemView.findViewById(R.id.searchProductName);
            searchProductPrice = itemView.findViewById(R.id.searchProductPrice);
            searchProductDate = itemView.findViewById(R.id.searchProductDate);
            searchProductStore = itemView.findViewById(R.id.searchStoreName);
            searchProductSize = itemView.findViewById(R.id.searchSizeName);

            buttonDelete = itemView.findViewById(R.id.buttonDelete);

        }
    }

    public interface ItemClickListener{
        void onItemClick(ProductModel productModel);
    }

    public static void setDefaultImage(ViewHolder viewHolder, String type){

        if(type.equals("Electronics")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.electornics);
        }
        if(type.equals("Women's Wardrobe")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.womenwardrobe);
        }
        if(type.equals("Home")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.home);
        }
        if(type.equals("Bank")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.creditcard);
        }
        if(type.equals("Beauty")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.beauty);
        }
        if(type.equals("Health")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.health);
        }
        if(type.equals("Automobiles")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.automobiles);
        }
        if(type.equals("Watches")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.watches);
        }
        if(type.equals("Men's Wardrobe")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.menwardrobe);
        }
        if(type.equals("Jewelry")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.jewelry);
        }
        if(type.equals("Baby Stuff")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.babystuff);
        }
        if(type.equals("Foot wear")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.footwear);
        }
        if(type.equals("Bags & Luggage")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.bags);
        }
        if(type.equals("Construction and repair")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.repair);
        }
        if(type.equals("Pet products")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.pet);
        }
        if(type.equals("Hobbies")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.hobbies);
        }
        if(type.equals("Garden Supplies")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.garden);
        }
        if(type.equals("Office Supplies")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.office);
        }
        if(type.equals("School Supplies")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.school);
        }
        if(type.equals("Sport")){
            viewHolder.searchProductImageView.setImageResource(R.drawable.sports);
        }

    }

}
