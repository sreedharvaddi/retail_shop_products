package com.retailshop.retailshopapplication.Model;

import android.util.Log;


import com.google.gson.Gson;
import com.retailshop.retailshopapplication.Services.RetailProductResponse;
import com.retailshop.retailshopapplication.Services.RetailProductsService;
import com.retailshop.retailshopapplication.Services.ServiceFactory;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



import static android.content.ContentValues.TAG;

/**
 * Created by sreedhar on 1/25/18.
 */

public class RetailProductsRemoteModel implements IDataModel {
    RetailProductsService service;
    RetailProductResponse response;

    public RetailProductsRemoteModel() {
        service = ServiceFactory.createService(RetailProductsService.class, RetailProductsService.END_POINT_URL);
    }

    @Override
    public Observable<List<RetailProduct>> loadReatilProducts() {
        int pageNumber = 1;
        int pageSize = 30;
        if (response != null) {
            pageNumber = response.getPageNumber() + 1;
            pageSize = response.getPageSize();
        }
        final int finalPageNumber = pageNumber;
        final int finalPageSize = pageSize;
        return  Observable.fromCallable(new Callable<List<RetailProduct>>() {
            @Override
            public List<RetailProduct> call() throws Exception {
                OkHttpClient client = new OkHttpClient();
                String base = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1";
                String url = "/walmartproducts/3e2f38d0-d26c-416e-b028-1d5e0f8350ed/";
                Request request = new Request.Builder().url(base + url + finalPageNumber + "/" + finalPageSize).build();
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();
                try {
                    RetailProductResponse response1 = gson.fromJson(response.body().string(), RetailProductResponse.class);
                    if (response1 != null) {
                        return response1.getProducts();
                    }
                    return Collections.emptyList();
                } catch (Exception ex)  {
                    Log.e(TAG, "exception "+ex.getMessage());
                    ex.printStackTrace();
                    return Collections.emptyList();
                }

            }
        });
    }

    @Override
    public Observable<RetailProductResponse> loadRetailProducts(final int pageNumber, final int pageSize) {
        return Observable.fromCallable(new Callable<RetailProductResponse>() {
            @Override
            public RetailProductResponse call() throws Exception {
                return service.fetchProducts(pageNumber, pageSize).execute().body();
            }
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public boolean isAllDataLoaded() {
        return false;
    }
}
