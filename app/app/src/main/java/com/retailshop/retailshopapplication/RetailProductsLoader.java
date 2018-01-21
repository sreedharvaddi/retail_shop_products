package com.retailshop.retailshopapplication;

import android.content.Context;
import android.content.Loader;

import com.retailshop.retailshopapplication.model.IRetailProductsModel;
import com.retailshop.retailshopapplication.model.RetailProductsModel;
import com.retailshop.retailshopapplication.model.repo.OnlineRepo;
import com.retailshop.retailshopapplication.presenter.IRetailProductsPresenter;
import com.retailshop.retailshopapplication.presenter.RetailProductsPresenter;

/**
 * Created by sreedhar on 1/15/18.
 */

public class RetailProductsLoader extends Loader<IRetailProductsPresenter> {
    IRetailProductsPresenter presenter;
    OnlineRepo repo;
    IRetailProductsModel model;


    public RetailProductsLoader(Context context) {
        super(context);

    }

    @Override
    protected void onStartLoading() {
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }
        forceLoad();
    }

    @Override
    protected  void onForceLoad(){
        // create repo
        repo = new OnlineRepo();

        // build model
        RetailProductsModel.Builder modelBuilder = new RetailProductsModel.Builder();
        model = modelBuilder.setOnlineRepo(repo).build();

        // build presenter
        RetailProductsPresenter.Builder presenterBuilder = new RetailProductsPresenter.Builder();
        presenter = presenterBuilder.setModel(model).build();

        deliverResult(presenter);
    }

    public IRetailProductsPresenter getPresenter() {
        return presenter;
    }
}
