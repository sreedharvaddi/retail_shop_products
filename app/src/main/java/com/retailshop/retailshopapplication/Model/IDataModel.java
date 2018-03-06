package com.retailshop.retailshopapplication.Model;

import com.retailshop.retailshopapplication.Services.RetailProductResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by sreedhar on 1/23/18.
 */

public interface IDataModel {
    Observable<List<RetailProduct>> loadReatilProducts();
    Observable<RetailProductResponse> loadRetailProducts(int pageNumber, int pageSize);
    boolean isAllDataLoaded();
}
