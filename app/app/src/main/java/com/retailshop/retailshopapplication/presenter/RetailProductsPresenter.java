package com.retailshop.retailshopapplication.presenter;

import com.retailshop.retailshopapplication.model.IRetailProductsModel;
import com.retailshop.retailshopapplication.model.RetailProduct;
import com.retailshop.retailshopapplication.model.RetailProductsModel;

import java.util.List;

/**
 * Created by sreedhar on 1/14/18.
 */

public class RetailProductsPresenter implements IRetailProductsPresenter, IRetailProductsModel.IModelCallbacks {
    public static final int SUCCESS = 0;
    public static final int FAILURE = -1;

    IViewCallbacks mView;
    IRetailProductsModel model;
    boolean isLoading = false;

    @Override
    public void setModel(IRetailProductsModel model) {
        this.model = model;
    }

    @Override
    public IRetailProductsModel getModel() { return model; }

    @Override
    public boolean isAllDataLoaded() {
        return getModel().isAllDataLoaded();
    }

    @Override
    public void loadProducts() {
        if (!isLoading) {
            isLoading = true;
            model.loadProducts();
        }
        mView.showProgres();
    }

    @Override
    public void loadProducts(int page, int pageSize) {
        if (!isLoading) {
            isLoading = true;
            model.loadProducts(page, pageSize);
        }
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
        isLoading = false;
        if (mView == null) {
            return;
        }
        if (status == SUCCESS) {
            mView.showSuccess();
        }
        else {
            mView.showError();
        }
        mView.showProducts(retailProducts);
    }

    public boolean isLoading() { return  isLoading; }

    static public class Builder {
        IViewCallbacks viewCallbacks;
        IRetailProductsModel model;
        public Builder() {

        }
        public Builder setModel(IRetailProductsModel model) {
            this.model = model;
            return this;
        }
        Builder setView(IViewCallbacks view) {
            this.viewCallbacks = view;
            return this;
        }
        public IRetailProductsPresenter build() {
            IRetailProductsPresenter productsPresenter = new RetailProductsPresenter();
            if (viewCallbacks != null) {
                productsPresenter.attachView(viewCallbacks);
            }
            if (model != null) {
                productsPresenter.setModel(model);
                model.setPresenter((IRetailProductsModel.IModelCallbacks) productsPresenter);
            }
            return productsPresenter;
        }
    }
}
