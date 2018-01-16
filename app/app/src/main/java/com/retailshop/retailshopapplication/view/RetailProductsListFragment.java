package com.retailshop.retailshopapplication.view;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.retailshop.retailshopapplication.MainActivity;
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
public class RetailProductsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<RetailProductsPresenter>,IRetailProductsPresenter.IViewCallbacks {

    Button btnLoad;
    RecyclerView retailProductsRecyclerView;
    RetailProductsPresenter productsPresenter;
    private RetailProductsAdaper retailProductsAdapter;

    public RetailProductsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                //((MainActivity)getActivity()).showDetailFragment();
                productsPresenter.loadProducts(1, 10);
            }
        });
        retailProductsRecyclerView = view.findViewById(R.id.ratailProductsRecyclerView);
        if (retailProductsRecyclerView  != null) {
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            retailProductsRecyclerView.setLayoutManager(llm);
            retailProductsAdapter = new RetailProductsAdaper();
            retailProductsRecyclerView.setAdapter(retailProductsAdapter);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onResume() {
        super.onResume();
        productsPresenter.attachView(this);
        ((RetailApplication)getActivity().getApplicationContext()).getModel().setPresenter(productsPresenter);
        productsPresenter.setModel(((RetailApplication)getActivity().getApplicationContext()).getModel());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        productsPresenter.detachView(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (productsPresenter == null) {
            getActivity().getLoaderManager().initLoader(101, savedInstanceState, this);
        }
        else {
            Loader<RetailProductsPresenter> loader = getActivity().getLoaderManager().getLoader(101);
            productsPresenter = ((RetailProductsLoader)loader).getPresenter();
        }
    }

    @Override
    public Loader<RetailProductsPresenter> onCreateLoader(int id, Bundle args) {
        return new RetailProductsLoader((Context) getActivity());
    }

    @Override
    public void onLoadFinished(Loader<RetailProductsPresenter> loader, RetailProductsPresenter data) {
        productsPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<RetailProductsPresenter> loader) {
        productsPresenter = null;
    }

    @Override
    public void showProgres() {
        Toast.makeText(getActivity(), "Progress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProducts(List<RetailProduct> retailProductList) {
        if (retailProductList != null) {
            retailProductsAdapter.update(retailProductList);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "Error occured ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
    }
}
