package com.retailshop.retailshopapplication.view;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.retailshop.retailshopapplication.R;
import com.retailshop.retailshopapplication.model.RetailProduct;

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
        return new RetailProductItemViewHolder(inflater.inflate(R.layout.retail_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RetailProductItemViewHolder holder, int position) {
        RetailProduct product=  productList.get(position);
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductDesc.setText(product.getShortDescription() != null ? Html.fromHtml(product.getShortDescription()) : "");
        holder.txtPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void update(List<RetailProduct> products) {
        this.productList.clear();
        this.productList.addAll(products);
        notifyDataSetChanged();
    }
}
