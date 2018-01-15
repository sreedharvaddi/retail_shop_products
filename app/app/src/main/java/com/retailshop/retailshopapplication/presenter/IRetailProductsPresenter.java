package com.retailshop.retailshopapplication.presenter;

import com.retailshop.retailshopapplication.model.IRetailProductsModel;
import com.retailshop.retailshopapplication.model.RetailProduct;

import java.util.List;

/**
 * Created by sreedhar on 1/14/18.
 */

public interface IRetailProductsPresenter {
    void loadProducts(int page, int pageSize);
    void attachView(IViewCallbacks viewCallbacks);
    void detachView(IViewCallbacks viewCallbacks);
    void setModel(IRetailProductsModel model);

    interface IViewCallbacks {
        void showProgres();
        void showProducts(List<RetailProduct> retailProductList);
        void showError();
        void showSuccess();
    }
}
