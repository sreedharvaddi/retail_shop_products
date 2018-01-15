package com.retailshop.retailshopapplication.presenter;

import com.retailshop.retailshopapplication.model.IRetailProductsModel;
import com.retailshop.retailshopapplication.model.RetailProduct;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sreedhar on 1/14/18.
 */
public class RetailProductsPresenterTest {
    @Test
    public void loadProducts() throws Exception {
        RetailProductsPresenter presenter = new RetailProductsPresenter();
    }

    @Test
    public void attachView() throws Exception {
    }

    @Test
    public void detachView() throws Exception {
    }

    IRetailProductsModel model = new IRetailProductsModel() {
        @Override
        public List<RetailProduct> loadProducts(int pageNo, int pageSize) {
            return Arrays.asList(new RetailProduct(), new RetailProduct(), new RetailProduct());
        }

        @Override
        public IRetailProductsPresenter setPresenter(IRetailProductsPresenter presenter) {
            return null;
        }

        @Override
        public void unregisterPresenter() {

        }
    };

    IRetailProductsPresenter.IViewCallbacks viewCallbacks = new IRetailProductsPresenter.IViewCallbacks() {
        @Override
        public void showProgres() {

        }

        @Override
        public void showProducts(List<RetailProduct> retailProductList) {

        }

        @Override
        public void showError() {

        }

        @Override
        public void showSuccess() {

        }
    };
}
