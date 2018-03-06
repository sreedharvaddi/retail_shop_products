package com.retailshop.retailshopapplication.Services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sreedhar on 1/25/18.
 */

public interface RetailProductsService {

    public static String END_POINT_URL = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/";

    @GET("walmartproducts/3e2f38d0-d26c-416e-b028-1d5e0f8350ed/{pageNumber}/{pageSize}")
    Call<RetailProductResponse> fetchProducts(
            @Path("pageNumber") int pageNumber,
            @Path("pageSize") int pageSize);

}
