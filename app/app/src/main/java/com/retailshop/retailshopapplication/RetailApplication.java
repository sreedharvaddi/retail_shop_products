package com.retailshop.retailshopapplication;

import android.app.Application;
import android.os.Bundle;

import com.retailshop.retailshopapplication.model.IRetailProductsModel;
import com.retailshop.retailshopapplication.model.RetailProductsModel;
import com.retailshop.retailshopapplication.model.repo.OnlineRepo;

/**
 * Created by sreedhar on 1/15/18.
 */

public class RetailApplication extends Application {
    IRetailProductsModel model;
    @Override
    public void onCreate() {
        super.onCreate();

        OnlineRepo repo = new OnlineRepo();
        RetailProductsModel.Builder builder = new RetailProductsModel.Builder();
        model = builder.setOnlineRepo(repo).build();
    }

    public IRetailProductsModel getModel() {
        return model;
    }
}
