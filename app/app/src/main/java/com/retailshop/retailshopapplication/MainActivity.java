package com.retailshop.retailshopapplication;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.retailshop.retailshopapplication.model.RetailProduct;
import com.retailshop.retailshopapplication.presenter.IRetailProductsPresenter;
import com.retailshop.retailshopapplication.presenter.RetailProductsPresenter;
import com.retailshop.retailshopapplication.view.RetailProductDetailFragment;
import com.retailshop.retailshopapplication.view.RetailProductsListFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setup();
            }
        }, 1000);
    }

    private void setup() {
        showListFragment();
    }

    public void showListFragment() {
        if(getSupportFragmentManager().findFragmentByTag("retail_product_list_fragment") == null) {
            RetailProductsListFragment fragment = new RetailProductsListFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container_id, fragment, "retail_product_list_fragment");
            ft.commit();
        }
    }

    public void showDetailFragment(RetailProduct product) {
        RetailProductDetailFragment fragment = new RetailProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container_id, fragment, "retail_product_detail_fragment");
        ft.addToBackStack("detailed_fragment");
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
