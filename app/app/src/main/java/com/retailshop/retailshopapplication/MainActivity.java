package com.retailshop.retailshopapplication;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.retailshop.retailshopapplication.model.RetailProduct;
import com.retailshop.retailshopapplication.presenter.IRetailProductsPresenter;
import com.retailshop.retailshopapplication.presenter.RetailProductsPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<RetailProductsPresenter>,IRetailProductsPresenter.IViewCallbacks {

    RetailProductsPresenter productsPresenter;
    TextView txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (productsPresenter == null) {
            getLoaderManager().initLoader(100, savedInstanceState, this);
        }
        else {
            Loader<RetailProductsPresenter> loader = getLoaderManager().getLoader(100);
            productsPresenter = ((RetailProductsLoader)loader).getPresenter();
        }
        setup();
    }

    private void setup() {
        findViewById(R.id.btnLoad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productsPresenter.loadProducts(1, 10);
            }
        });
        txtStatus = (TextView) findViewById(R.id.txtStatus);
    }

    @Override
    public void onStart() {
        super.onStart();
        productsPresenter.attachView(this);
        ((RetailApplication)getApplicationContext()).getModel().setPresenter(productsPresenter);
        productsPresenter.setModel(((RetailApplication)getApplicationContext()).getModel());

    }

    @Override
    public void onStop() {
        super.onStop();
        productsPresenter.detachView(this);
    }

    @Override
    public Loader<RetailProductsPresenter> onCreateLoader(int i, Bundle bundle) {
        return new RetailProductsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<RetailProductsPresenter> loader, RetailProductsPresenter retailProductsPresenter) {
        productsPresenter = retailProductsPresenter;
    }

    @Override
    public void onLoaderReset(Loader<RetailProductsPresenter> loader) {
        productsPresenter =  null;

    }

    @Override
    public void showProgres() {
        Toast.makeText(this, "Progress", Toast.LENGTH_SHORT).show();
        txtStatus.setText("Progress");
    }

    @Override
    public void showProducts(List<RetailProduct> retailProductList) {
        if (retailProductList != null) {
            txtStatus.setText(" totalSize " + retailProductList.size());
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error occured ", Toast.LENGTH_SHORT).show();
        txtStatus.setText("Error");
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        txtStatus.setText("success");
    }
}
