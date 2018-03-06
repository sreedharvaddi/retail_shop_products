package com.retailshop.retailshopapplication.View;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.retailshop.retailshopapplication.Model.RetailProduct;
import com.retailshop.retailshopapplication.R;
import com.retailshop.retailshopapplication.RetailProductsLoader;
import com.retailshop.retailshopapplication.ViewModel.RetailProductViewModel;


import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 * A simple {@link Fragment} subclass.
 */
public class RetailProductsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<RetailProductViewModel>, EndlessScrollListener.IScrollListener {

    private final String TAG = "RetProductsListFragment";
    Button btnLoad;
    RecyclerView retailProductsRecyclerView;
    RetailProductViewModel viewModel;
    TextView emptyList;
    private RetailProductsAdaper retailProductsAdapter;
    public final int PAGE_SIZE = 5;
    CompositeDisposable disposable = new CompositeDisposable();

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
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.d(TAG, "onBackStackChanged()"+getActivity().getSupportFragmentManager().getBackStackEntryCount());
                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    Log.d(TAG, "onBackStackChanged insise ");
                    refresh();
                }
            }
        });
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
        subscribe();
    }

    private void refresh() {
        if (getActivity().getSupportFragmentManager().findFragmentByTag("retail_product_detail_fragment") != null) {
            return;
        }
        viewModel.loadProducts();
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
        unsubscribe();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated()");
        Loader<RetailProductViewModel> loader = getActivity().getLoaderManager().getLoader(101);
        if (loader == null) {
            getActivity().getLoaderManager().initLoader(101, savedInstanceState, this);
        }
        else {
            viewModel = ((RetailProductsLoader)loader).getPresenter();
            refresh();
        }
    }

    @Override
    public Loader<RetailProductViewModel> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader()");
        return new RetailProductsLoader((Context) getActivity());
    }

    @Override
    public void onLoadFinished(Loader<RetailProductViewModel> loader, RetailProductViewModel retailProductViewModel) {
        if (viewModel == null) {
            viewModel = retailProductViewModel;
            subscribe();
            refresh();
        }
    }

    @Override
    public void onLoaderReset(Loader<RetailProductViewModel> loader) {
        unsubscribe();
        viewModel.clear();
        viewModel = null;
    }


    public void showProgres() {
        Snackbar.make(retailProductsRecyclerView, "Progress", Snackbar.LENGTH_SHORT).show();
    }

    public void showProducts(List<RetailProduct> retailProductList) {
        if (retailProductList != null) {
            retailProductsAdapter.update(retailProductList);
        }
    }

    public void showError() {
        Snackbar.make(retailProductsRecyclerView, "Error occured", Snackbar.LENGTH_SHORT).show();
    }

    public void showSuccess() {
        Snackbar.make(retailProductsRecyclerView, "loaded successfully", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loadMore() {
        viewModel.loadProducts();
    }

    public void subscribe() {
        disposable.add(viewModel.getListProducts().subscribe(new Consumer<List<RetailProduct>>() {
            @Override
            public void accept(List<RetailProduct> retailProducts) throws Exception {
                showSuccess();
                showProducts(retailProducts);
            }
        }));

        disposable.add(viewModel.getIsLoading().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean != false) {
                    showProgres();
                }
                else {

                }
            }
        }));
    }

    public void unsubscribe() {
        disposable.clear();
    }
}
