package com.retailshop.retailshopapplication.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.retailshop.retailshopapplication.MainActivity;
import com.retailshop.retailshopapplication.Model.RetailProduct;
import com.retailshop.retailshopapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreedhar on 1/16/18.
 */

class RetailProductsAdaper extends android.support.v7.widget.RecyclerView.Adapter<RetailProductItemViewHolder> {
    List<RetailProduct> productList = new ArrayList<>();

    @Override
    public RetailProductItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return (new RetailProductItemViewHolder(inflater.inflate(R.layout.retail_item_layout, parent, false)));
    }

    @Override
    public void onBindViewHolder(final RetailProductItemViewHolder holder, int position) {

        final RetailProduct product=  productList.get(position);
        holder.txtProductName.setText(product.getProductName());
        holder.txtPrice.setText(product.getPrice());
        Picasso.with(holder.imgProductImage.getRootView().getContext())
                .load(product.getProductImage()).into(holder.imgProductImage);
        holder.txtRating.setText(String.format(holder.txtRating.getContext().getString(R.string.rating), product.getReviewRating()));
        holder.txtProductName.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).showDetailFragment(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void update(List<RetailProduct> products) {
        this.productList.addAll(products);
        notifyDataSetChanged();
    }

}
