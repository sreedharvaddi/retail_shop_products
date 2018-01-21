package com.retailshop.retailshopapplication.view;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.retailshop.retailshopapplication.R;
import com.retailshop.retailshopapplication.RetailApplication;
import com.retailshop.retailshopapplication.RetailProductsLoader;
import com.retailshop.retailshopapplication.model.RetailProduct;
import com.retailshop.retailshopapplication.presenter.IRetailProductsPresenter;
import com.retailshop.retailshopapplication.presenter.RetailProductsPresenter;

import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class RetailProductsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<IRetailProductsPresenter>,IRetailProductsPresenter.IViewCallbacks, EndlessScrollListener.IScrollListener {

    private final String TAG = "RetProductsListFragment";
    Button btnLoad;
    RecyclerView retailProductsRecyclerView;
    IRetailProductsPresenter productsPresenter;
    TextView emptyList;
    private RetailProductsAdaper retailProductsAdapter;
    public final int PAGE_SIZE = 30;

    public RetailProductsListFragment() {
        // Required empty public constructor
        Log.d(TAG, "RetailProductsListFragment()");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retail_products_list, container, false);
        setup(view);
        return view;
    }

    private void setup(View view) {
        btnLoad = (Button) view.findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        retailProductsRecyclerView = view.findViewById(R.id.ratail_products_recycler_view);
        if (retailProductsRecyclerView  != null) {
            final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            retailProductsRecyclerView.setLayoutManager(llm);
            retailProductsAdapter = new RetailProductsAdaper();
            retailProductsRecyclerView.setAdapter(retailProductsAdapter);
            retailProductsRecyclerView.addOnScrollListener(new EndlessScrollListener(llm, PAGE_SIZE, this));
        }
        emptyList = view.findViewById(R.id.empty_list);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    private void refresh() {
        if (getActivity().getSupportFragmentManager().findFragmentByTag("retail_product_detail_fragment") != null) {
            return;
        }
        List<RetailProduct> list = productsPresenter.getModel().getCurrentData().getProductList();
        if (list != null && list.size() > 0) {
            hideEmptyList();
            showProducts(list);
        }
        else {
            showEmptyList();
        }
        productsPresenter.loadProducts();
    }

    private void hideEmptyList() {
        emptyList.setVisibility(View.GONE);
    }

    private void showEmptyList() {
        emptyList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
        if (productsPresenter != null) {
            productsPresenter.detachView(this);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated()");
        Loader<IRetailProductsPresenter> loader = getActivity().getLoaderManager().getLoader(101);
        if (loader == null) {
            getActivity().getLoaderManager().initLoader(101, savedInstanceState, this);
        }
        else {
            productsPresenter = ((RetailProductsLoader)loader).getPresenter();
            productsPresenter.attachView(this);
            refresh();
        }
    }

    @Override
    public Loader<IRetailProductsPresenter> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader()");
        return new RetailProductsLoader((Context) getActivity());
    }

    @Override
    public void onLoadFinished(Loader<IRetailProductsPresenter> loader, IRetailProductsPresenter data) {
        Log.d(TAG, "onLoadFinished()");
        if (productsPresenter == null) {
            Log.d(TAG, "productsPresenter loaded ");
            productsPresenter = data;
            productsPresenter.attachView(this);
            refresh();
        }
    }

    @Override
    public void onLoaderReset(Loader<IRetailProductsPresenter> loader) {
        Log.d(TAG, "onLoaderReset()");
        productsPresenter = null;
    }

    @Override
    public void showProgres() {

        Snackbar.make(retailProductsRecyclerView, "Progress", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void showProducts(List<RetailProduct> retailProductList) {
        if (retailProductList != null) {
            retailProductsAdapter.update(retailProductList);
        }
    }

    @Override
    public void showError() {
        Snackbar.make(retailProductsRecyclerView, "Error occured", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        Snackbar.make(retailProductsRecyclerView, "loaded successfully", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loadMore() {

        if (productsPresenter.isAllDataLoaded() == false) {
            productsPresenter.loadProducts();
        }
        else {
            Snackbar.make(retailProductsRecyclerView, "Loaded all data", Snackbar.LENGTH_SHORT).show();
        }
    }
}
