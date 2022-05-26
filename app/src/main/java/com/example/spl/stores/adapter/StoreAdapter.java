package com.example.spl.stores.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spl.R;
import com.example.spl.stores.model.StoreModel;

import java.util.ArrayList;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> implements Filterable {

    List<StoreModel> storeList;
    List<StoreModel> storeListFull;

    private ItemClickListener itemClickListener;


    public StoreAdapter(List<StoreModel> storeList , ItemClickListener itemClickListener){
        this.storeList = storeList;
        storeListFull= new ArrayList<>(storeList);
        this.itemClickListener = itemClickListener;
    }

    public StoreAdapter(List<StoreModel> storeList){
        this.storeList = storeList;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView storeImage;
        ImageView iconCity;
        ImageView iconCountry;
        ImageView iconAddress;
        TextView descriptionText;
        TextView storeName;
        TextView cityLocation;
        TextView addressLocation;
        TextView countryLocation;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeImage = itemView.findViewById(R.id.storeImage);
            descriptionText = itemView.findViewById(R.id.storeDescription);
            storeName = itemView.findViewById(R.id.storeName);
            cityLocation = itemView.findViewById(R.id.cityLocation);
            addressLocation = itemView.findViewById(R.id.addressLocation);
            countryLocation = itemView.findViewById(R.id.countryLocation);
            iconCity = itemView.findViewById(R.id.iconCity);
            iconCountry = itemView.findViewById(R.id.iconCountry);
            iconAddress = itemView.findViewById(R.id.iconAddress);
        }
    }

    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.single_store_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(position%2==0){
            holder.itemView.setBackgroundColor(Color.parseColor("#f4f7fd"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        setDefaultImage(holder,storeList.get(position).getStoreCategory());
        holder.descriptionText.setText(storeList.get(position).getStoreDescription());
        holder.storeName.setText(storeList.get(position).getStoreName());
        holder.cityLocation.setText(storeList.get(position).getStoreCity());
        holder.addressLocation.setText(storeList.get(position).getStoreAddress());
        holder.countryLocation.setText(storeList.get(position).getStoreCountry());
        holder.iconCountry.setImageResource(R.drawable.ic_baseline_location_on_24);
        holder.iconCity.setImageResource(R.drawable.ic_baseline_location_city_24);


        holder.iconAddress.setImageResource(R.drawable.ic_baseline_my_location_24);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(storeList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    @Override
    public Filter getFilter() {
        return storeListFilter;
    }

    private Filter storeListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<StoreModel> filterList = new ArrayList<>();
            if(charSequence == null || charSequence.length()==0){
                filterList.addAll(storeListFull);
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                storeListFull.forEach((e)->{
                    if( e.getStoreName().toLowerCase().trim().contains(filterPattern)){
                        filterList.add(e);
                    }
                });
            }

            //return result list of search and if above
            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            storeList.clear();
            storeList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };



    public interface ItemClickListener
    {
        void onItemClick(StoreModel storeModel);
    }


    public static void setDefaultImage(ViewHolder viewHolder, String type){

        if(type.equals("Electronics")){
            viewHolder.storeImage.setImageResource(R.drawable.electornics);
        }
        if(type.equals("Women's Wardrobe")){
            viewHolder.storeImage.setImageResource(R.drawable.womenwardrobe);
        }
        if(type.equals("Home")){
            viewHolder.storeImage.setImageResource(R.drawable.home);
        }
        if(type.equals("Beauty")){
            viewHolder.storeImage.setImageResource(R.drawable.beauty);
        }
        if(type.equals("Health")){
            viewHolder.storeImage.setImageResource(R.drawable.health);
        }
        if(type.equals("Automobiles")){
            viewHolder.storeImage.setImageResource(R.drawable.automobiles);
        }
        if(type.equals("Watches")){
            viewHolder.storeImage.setImageResource(R.drawable.watches);
        }
        if(type.equals("Men's Wardrobe")){
            viewHolder.storeImage.setImageResource(R.drawable.menwardrobe);
        }
        if(type.equals("Jewelry")){
            viewHolder.storeImage.setImageResource(R.drawable.jewelry);
        }
        if(type.equals("Baby Stuff")){
            viewHolder.storeImage.setImageResource(R.drawable.babystuff);
        }
        if(type.equals("Foot wear")){
            viewHolder.storeImage.setImageResource(R.drawable.footwear);
        }
        if(type.equals("Bags & Luggage")){
            viewHolder.storeImage.setImageResource(R.drawable.bags);
        }
        if(type.equals("Construction and repair")){
            viewHolder.storeImage.setImageResource(R.drawable.repair);
        }
        if(type.equals("Pet products")){
            viewHolder.storeImage.setImageResource(R.drawable.pet);
        }
        if(type.equals("Hobbies")){
            viewHolder.storeImage.setImageResource(R.drawable.hobbies);
        }
        if(type.equals("Garden Supplies")){
            viewHolder.storeImage.setImageResource(R.drawable.garden);
        }
        if(type.equals("Office Supplies")){
            viewHolder.storeImage.setImageResource(R.drawable.office);
        }
        if(type.equals("School Supplies")){
            viewHolder.storeImage.setImageResource(R.drawable.school);
        }
        if(type.equals("Sport")){
            viewHolder.storeImage.setImageResource(R.drawable.sports);
        }

    }
}
