package com.retailshop.retailshopapplication.Services;

import com.retailshop.retailshopapplication.Model.RetailProduct;

import java.util.List;

/**
 * Created by sreedhar on 1/26/18.
 */

public class RetailProductResponse {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<RetailProduct> getProducts() {
        return products;
    }

    public void setProducts(List<RetailProduct> productList) {
        this.products = productList;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String id;
    public List<RetailProduct> products;
    public int totalProducts;
    public int pageNumber;
    public int pageSize;
    public int status;
    String kind;
    String etag;
}
