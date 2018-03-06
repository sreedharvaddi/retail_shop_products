package com.retailshop.retailshopapplication.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by sreedhar on 1/20/18.
 */

public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private final LinearLayoutManager linearLayoutManager;
    private final int threshold;
    IScrollListener listener;

    public interface IScrollListener {
        void loadMore();
    }
    EndlessScrollListener(LinearLayoutManager llm, int threshold, IScrollListener listener) {
        this.linearLayoutManager = llm;
        this.threshold = threshold;
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

        if ((totalItemCount - visibleItemCount) < (firstVisibleItemPosition + threshold)) {
            listener.loadMore();
        }
    }
}
