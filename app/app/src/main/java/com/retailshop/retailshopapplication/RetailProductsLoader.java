package com.retailshop.retailshopapplication;

import android.content.Context;
import android.content.Loader;

import com.retailshop.retailshopapplication.presenter.IRetailProductsPresenter;
import com.retailshop.retailshopapplication.presenter.RetailProductsPresenter;

/**
 * Created by sreedhar on 1/15/18.
 */

class RetailProductsLoader extends Loader<RetailProductsPresenter> {
    RetailProductsPresenter presenter;
    public RetailProductsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (presenter != null) {
            deliverResult(presenter);
        }
        forceLoad();
    }

    @Override
    protected  void onForceLoad(){
        presenter = new RetailProductsPresenter();
        deliverResult(presenter);
    }

    public RetailProductsPresenter getPresenter() {
        return presenter;
    }
}
