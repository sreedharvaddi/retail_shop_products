package com.retailshop.retailshopapplication.model;

import com.retailshop.retailshopapplication.model.repo.IRepo;
import com.retailshop.retailshopapplication.presenter.IRetailProductsPresenter;

import java.util.List;

/**
 * Created by sreedhar on 1/14/18.
 */

public interface IRetailProductsModel {
    List<RetailProduct> loadProducts(int pageNo, int pageSize);
    void setPresenter(IModelCallbacks presenter);
    void setOnlineRepo(IRepo repo);
    boolean isOnline();
    void unregisterPresenter();
    RetailProductsData getCurrentData();
    interface IModelCallbacks {
        void onLoadFinished(int status, List<RetailProduct> retailProducts);
    }
}
