package com.example.spl.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.spl.R;
import com.example.spl.products.addproduct.AddProductActivity;
import com.example.spl.products.database.ProductDatabase;
import com.example.spl.products.features.ProductDetails;
import com.example.spl.products.model.ProductModel;
import com.example.spl.products.adapter.ProductAdapter;
import com.example.spl.products.adapter.ProductAdapterRecently;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment  implements Serializable {

    private FloatingActionButton addProductButton;
    public RecyclerView rvProducts;
    public RecyclerView rvRecently;
    public ProductAdapter productAdapter;
    public ProductAdapterRecently productAdapterRecently;

    public static List<ProductModel> productList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,container,false);


        addProductButton = (FloatingActionButton)view.findViewById(R.id.addProduct);
        rvProducts = view.findViewById(R.id.rvProducts);
        rvRecently = view.findViewById(R.id.rvRecently);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddProductActivity.class));
            }
        });

        //Read products from database and add them to recycler view
        productList = new ArrayList<>();
        productList = loadProductFromDatabase();
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(productList, new ProductAdapter.ItemClickListener() {
            @Override 
            public void onItemClick(ProductModel productModel) {
                Intent intent = new Intent(getContext(),ProductDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedProductObject", productModel);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvProducts.setAdapter(productAdapter);



        List<ProductModel> filterList = new ArrayList<>();

        if(productList.size() <5){
            filterList.addAll(productList);
        }else{

            filterList.add(productList.get(productList.size()-1));
            filterList.add(productList.get(productList.size()-2));
            filterList.add(productList.get(productList.size()-3));
            filterList.add(productList.get(productList.size()-4));
            filterList.add(productList.get(productList.size()-5));

        }

        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rvRecently.setLayoutManager(lm);
        rvRecently.setItemAnimator(new DefaultItemAnimator());
        productAdapterRecently = new ProductAdapterRecently(filterList);
        rvRecently.setAdapter(productAdapterRecently);

        return view;
    }

    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    public List<ProductModel> loadProductFromDatabase(){
        List<ProductModel> lista = new ArrayList<>();

        ProductDatabase db = Room.databaseBuilder(getContext(),ProductDatabase.class,"products").allowMainThreadQueries().build();
        lista = db.productDAO().getAllProducts();
        db.close();

        return lista;

    }



}
