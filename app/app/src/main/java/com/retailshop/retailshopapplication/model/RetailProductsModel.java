package com.retailshop.retailshopapplication.model;

import com.retailshop.retailshopapplication.model.repo.IRepo;
import com.retailshop.retailshopapplication.model.repo.RetailProductResponse;

import java.util.List;

/**
 * Created by sreedhar on 1/15/18.
 */

public class RetailProductsModel implements IRetailProductsModel, IRepo.ICallbacks {
    IRepo mOnlineRepo;
    RetailProductsData mProductsData = new RetailProductsData();
    IModelCallbacks mPresenter;
    @Override
    public List<RetailProduct> loadProducts(int pageNo, int pageSize) {
        if (mOnlineRepo != null) {
            mOnlineRepo.fetchProducts(pageNo, pageSize);
        }
        return mProductsData.productList;
    }

    @Override
    public void setPresenter(IModelCallbacks presenter) {
        mPresenter =presenter;
    }

    @Override
    public void setOnlineRepo(IRepo repo) {
        mOnlineRepo = repo;
    }

    @Override
    public boolean isOnline() {
        return (mOnlineRepo != null && mOnlineRepo.isOnline());
    }

    @Override
    public void unregisterPresenter() {

    }

    @Override
    public void onFinished(RetailProductResponse productResponse) {
        // upate model data.
        mProductsData.productList = productResponse.products;
        mProductsData.pageNumber = productResponse.pageNumber;
        mProductsData.pageSize = productResponse.pageSize;
        mProductsData.totalProducts = productResponse.totalProducts;

        mPresenter.onLoadFinished(0, mProductsData.productList);

    }

    @Override
    public void onCancled() {

    }

    @Override
    public void onFailuer(String err) {

    }

    public static class Builder {
        IRepo onlineRepo;
        public Builder setOnlineRepo(IRepo onlineRepo) {
            this.onlineRepo = onlineRepo;
            return this;
        }
        public IRetailProductsModel build() {
            IRetailProductsModel model = new RetailProductsModel();
            if (onlineRepo != null) {
                onlineRepo.register((IRepo.ICallbacks) model);
                model.setOnlineRepo(onlineRepo);
                return model;
            }
            return model;
        }
    }
}
