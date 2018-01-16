package com.retailshop.retailshopapplication.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.retailshop.retailshopapplication.R;

/**
 * Created by sreedhar on 1/16/18.
 */

class RetailProductItemViewHolder extends RecyclerView.ViewHolder {
    public TextView txtProductName;
    public TextView txtProductDesc;
    public TextView txtPrice;

    public RetailProductItemViewHolder(View itemView) {
        super(itemView);
        txtProductName = (TextView) itemView.findViewById(R.id.txt_product_name);
        txtProductDesc = (TextView) itemView.findViewById(R.id.txt_product_desc);
        txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
    }
}
