package com.retailshop.retailshopapplication.Model;

import com.retailshop.retailshopapplication.Services.RetailProductResponse;
import com.retailshop.retailshopapplication.Services.RetailProductsService;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sreedhar on 1/28/18.
 */

public class RetailProductsRetrofitModel implements IDataModel {
    RetailProductsService service;
    RetailProductResponse mCurrentResponse = null;
    public RetailProductsRetrofitModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RetailProductsService.class);

    }
    @Override
    public Observable<List<RetailProduct>> loadReatilProducts() {
        return Observable.fromCallable(new Callable<RetailProductResponse>() {
                    @Override
                    public RetailProductResponse call() throws Exception {
                        int pageNumber = 1;
                        int pageSize = 30;
                        if (mCurrentResponse != null) {
                            pageNumber = mCurrentResponse.getPageNumber();
                            pageSize = mCurrentResponse.getPageSize();
                        }
                        Call<RetailProductResponse> call = service.fetchProducts(pageNumber, pageSize);
                        return call.execute().body();
                    }
                })
                .map(new Function<RetailProductResponse, List<RetailProduct>>() {
                    @Override
                    public List<RetailProduct> apply(RetailProductResponse respone) throws Exception {
                        mCurrentResponse = respone;
                        return respone.getProducts();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RetailProductResponse> loadRetailProducts(final int pageNumber, final int pageSize) {
        return Observable.fromCallable(new Callable<RetailProductResponse>() {
                    @Override
                    public RetailProductResponse call() throws Exception {
                        return service.fetchProducts(pageNumber, pageSize).execute().body();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public boolean isAllDataLoaded() {
        return false;
    }
}
