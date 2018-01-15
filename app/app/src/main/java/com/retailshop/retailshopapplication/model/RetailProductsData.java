package com.retailshop.retailshopapplication.model;

import java.util.List;

/**
 * Created by sreedhar on 1/14/18.
 */

public class RetailProductsData {
    String id;
    List<RetailProduct> productList;
    int totalProducts;
    int pageNumber;
    int pageSize;
    int status;
    String kind;
    String etag;
}
