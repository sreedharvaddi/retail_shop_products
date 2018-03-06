package com.retailshop.retailshopapplication.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.retailshop.retailshopapplication.R;

/**
 * Created by sreedhar on 1/16/18.
 */

class RetailProductItemViewHolder extends RecyclerView.ViewHolder {
    public TextView txtProductName;
    public TextView txtPrice;
    public ImageView imgProductImage;
    public TextView txtRating;

    public RetailProductItemViewHolder(View itemView) {
        super(itemView);
        txtProductName = (TextView) itemView.findViewById(R.id.txt_product_name);
        txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
        imgProductImage= (ImageView) itemView.findViewById(R.id.img_product);
        txtRating = (TextView) itemView.findViewById(R.id.txt_rating);
    }
}
