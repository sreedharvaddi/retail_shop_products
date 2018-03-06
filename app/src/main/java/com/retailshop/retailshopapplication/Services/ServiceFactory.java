package com.retailshop.retailshopapplication.Services;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sreedhar on 1/25/18.
 */

public class ServiceFactory {
    public static <T> T createService(final Class<T> clazz, final String endPointUrl) {

       Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(endPointUrl)
                .addConverterFactory(GsonConverterFactory.create())
               // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        T service = retrofit.create(clazz);
        return service;
    }
}
