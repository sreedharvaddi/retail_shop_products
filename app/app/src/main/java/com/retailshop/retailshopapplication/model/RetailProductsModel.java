package com.retailshop.retailshopapplication.model;

import com.retailshop.retailshopapplication.model.repo.IRepo;
import com.retailshop.retailshopapplication.model.repo.RetailProductResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreedhar on 1/15/18.
 */

public class RetailProductsModel implements IRetailProductsModel, IRepo.ICallbacks {
    IRepo mOnlineRepo;
    RetailProductsData mProductsData = new RetailProductsData();
    IModelCallbacks mPresenter;
    List<RetailProduct> mCachedProducts = new ArrayList<>();
    boolean loading = false;

    @Override
    public List<RetailProduct> loadProducts(int pageNo, int pageSize) {
        if (mOnlineRepo != null) {
            mOnlineRepo.fetchProducts(pageNo, pageSize);
            loading = true;
        }
        return mCachedProducts;
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
        loading = false;
        mProductsData.productList = productResponse.products;
        mProductsData.pageNumber = productResponse.pageNumber;
        mProductsData.pageSize = productResponse.pageSize;
        mProductsData.totalProducts = productResponse.totalProducts;
        mCachedProducts.addAll(mProductsData.productList);
        mPresenter.onLoadFinished(0, mCachedProducts);
    }

    @Override
    public void onCancled() {
        loading = false;
    }

    @Override
    public void onFailuer(String err) {
        loading = false;
    }

    @Override
    public RetailProductsData getCurrentData() {
        return mProductsData;
    }

    @Override
    public boolean isAllDataLoaded() {
        if (mProductsData != null) {
            return (mCachedProducts.size() >= mProductsData.getTotalProducts());
        }
        return false;
    }

    @Override
    public List<RetailProduct> loadProducts() {
        if (mProductsData == null) {

        }
        return loadProducts(mProductsData.getPageNumber() + 1, mProductsData.getPageSize());
    }

    @Override
    public void clear() {
        mCachedProducts.clear();
        mProductsData = null;
    }

    @Override
    public boolean isLoading() {
        return loading;
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
