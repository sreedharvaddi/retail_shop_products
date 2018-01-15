package com.retailshop.retailshopapplication.model;

import com.retailshop.retailshopapplication.presenter.IRetailProductsPresenter;

import java.util.List;

/**
 * Created by sreedhar on 1/14/18.
 */

public interface IRetailProductsModel {
    List<RetailProduct> loadProducts(int pageNo, int pageSize);
    IRetailProductsPresenter setPresenter(IRetailProductsPresenter presenter);
    void unregisterPresenter();
    interface IModelCallbacks {
        void onLoadFinished(int status, List<RetailProduct> retailProducts);
    }
}
