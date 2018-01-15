package com.retailshop.retailshopapplication.presenter;

import com.retailshop.retailshopapplication.model.IRetailProductsModel;
import com.retailshop.retailshopapplication.model.RetailProduct;

import java.util.List;

/**
 * Created by sreedhar on 1/14/18.
 */

public class RetailProductsPresenter implements IRetailProductsPresenter, IRetailProductsModel.IModelCallbacks {
    public static final int SUCCESS = 0;
    public static final int FAILURE = -1;

    IViewCallbacks mView;
    IRetailProductsModel model;
    @Override
    public void loadProducts(int page, int pageSize) {
        model.loadProducts(page, pageSize);
        mView.showProgres();
    }

    @Override
    public void attachView(IViewCallbacks viewCallbacks) {
        mView = viewCallbacks;
    }

    @Override
    public void detachView(IViewCallbacks viewCallbacks) {
        mView = null;
    }

    @Override
    public void onLoadFinished(int status, List<RetailProduct> retailProducts) {
        if (status == SUCCESS) {
            mView.showSuccess();
        }
        else {
            mView.showError();
        }
        mView.showProducts(retailProducts);
    }
}
