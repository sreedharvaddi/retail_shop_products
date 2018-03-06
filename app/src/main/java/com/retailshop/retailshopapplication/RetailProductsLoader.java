package com.retailshop.retailshopapplication;

import android.content.Context;
import android.content.Loader;

import com.retailshop.retailshopapplication.Model.RetailProductsRetrofitModel;
import com.retailshop.retailshopapplication.ViewModel.RetailProductViewModel;


/**
 * Created by sreedhar on 1/15/18.
 */

public class RetailProductsLoader extends Loader<RetailProductViewModel> {
    RetailProductViewModel viewModel;

    public RetailProductsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (viewModel != null) {
            deliverResult(viewModel);
            return;
        }
        forceLoad();
    }

    @Override
    protected  void onForceLoad(){
        viewModel = new RetailProductViewModel(new RetailProductsRetrofitModel());
        deliverResult(viewModel);
    }

    public RetailProductViewModel getPresenter() {
        return viewModel;
    }
}
