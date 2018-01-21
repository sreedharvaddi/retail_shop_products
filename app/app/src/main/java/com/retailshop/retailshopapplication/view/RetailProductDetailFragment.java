package com.retailshop.retailshopapplication.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.retailshop.retailshopapplication.R;
import com.retailshop.retailshopapplication.model.RetailProduct;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailProductDetailFragment extends Fragment {


    TextView txtProductName;
    TextView txtProductShortDesc;
    TextView txtProductPrice;
    ImageView imgProductImage;
    TextView txtProductLongDesc;
    TextView txtProductRating;
    TextView txtInStock;
    private Button btnDone;

    public RetailProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retail_product_detail, container, false);
        setup(view);
        return view;
    }

    private void setup(View view) {
        txtProductName = view.findViewById(R.id.txt_detail_product_name);
        txtProductShortDesc = view.findViewById(R.id.txt_detail_product_short_desc);
        txtProductLongDesc = view.findViewById(R.id.txt_detail_product_long_desc);
        txtProductPrice = view.findViewById(R.id.txt_detail_product_price);
        imgProductImage = view.findViewById(R.id.img_detail_product_image);
        txtProductRating = view.findViewById(R.id.txt_detail_rating);
        txtInStock = view.findViewById(R.id.txt_detail_instock);
        btnDone = view.findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        RetailProduct product = bundle.getParcelable("product");
        updateUI(product);
    }

    private void updateUI(RetailProduct product) {
        txtProductName.setText(product.getProductName());
        String shortDesc = product.getShortDescription() != null ? product.getShortDescription() : "";
        String longDesc = product.getLongDescription() != null ? product.getLongDescription() : "";
        txtProductShortDesc.setText(Html.fromHtml(shortDesc));
        txtProductLongDesc.setText(Html.fromHtml(longDesc));
        txtProductPrice.setText(String.format(getActivity().getString(R.string.price), product.getPrice()));
        if (product.isInStock()) {
            txtInStock.setTextColor(getActivity().getColor(android.R.color.holo_green_light));
            txtInStock.setText("In Stock");
        }
        else {
            txtInStock.setTextColor(getActivity().getColor(android.R.color.darker_gray));
            txtInStock.setText("Out of stock");
        }
        txtProductRating.setText(String.format(getActivity().getString(R.string.rating), product.getReviewRating()));
        Picasso.with(txtProductPrice.getContext()).load(product.getProductImage()).into(imgProductImage);
    }
}
