package com.retailshop.retailshopapplication.ViewModel;


import com.retailshop.retailshopapplication.Model.IDataModel;
import com.retailshop.retailshopapplication.Model.RetailProduct;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


import java.util.List;

/**
 * Created by sreedhar on 1/24/18.
 */

public class RetailProductViewModel {
    Observable<Boolean> isLoading;
    Observable<List<RetailProduct>> listProducts;
    CompositeDisposable disposable = new CompositeDisposable();
    final IDataModel dataModel;
    private ObservableEmitter<List<RetailProduct>> mListProductsEmiter;
    private ObservableEmitter<Boolean> isLoadingEmiter;
    private ObservableEmitter<Boolean> isAllDataLoadedEmiter;

    public RetailProductViewModel(IDataModel dataModel) {
        this.dataModel = dataModel;
    }
    public void loadProducts() {
        disposable.add(dataModel.loadReatilProducts()
                .subscribe(new Consumer<List<RetailProduct>>() {
                    @Override
                    public void accept(List<RetailProduct> retailProducts) throws Exception {
                        isLoading = getIsLoading();
                        isLoadingEmiter.onNext(false);
                        if (mListProductsEmiter == null) {
                            listProducts = getListProducts();
                        }
                        mListProductsEmiter.onNext(retailProducts);

                    }
                }));
        isLoading = getIsLoading();
        isLoadingEmiter.onNext(true);
    }

    public void clear() {
        disposable.clear();
    }

    public Observable<List<RetailProduct>> getListProducts() {
        if (listProducts == null) {
            listProducts = Observable.create(new ObservableOnSubscribe<List<RetailProduct>>() {
                @Override
                public void subscribe(ObservableEmitter<List<RetailProduct>> e) throws Exception {
                    mListProductsEmiter = e;
                }
            });
        }
        return listProducts;
    }

    public Observable<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                    isLoadingEmiter = e;
                }
            });
        }
        return isLoading;
    }
}
