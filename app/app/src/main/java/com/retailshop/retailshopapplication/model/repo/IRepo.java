package com.retailshop.retailshopapplication.model.repo;

/**
 * Created by sreedhar on 1/15/18.
 */

public interface IRepo {
    boolean isOnline();
    void register(ICallbacks callbacks);
    void unregister();
    void fetchProducts(int pageNo, int pageSize);
    interface ICallbacks {
        void onFinished(RetailProductResponse productResponse);
        void onCancled();
        void onFailuer(String err);
    }
}
