package com.bucketdrops.vivek.bucketdrops.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.bucketdrops.vivek.bucketdrops.extras.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by vivek on 2/27/2018.
 */

public class BucketRecyclerView extends RecyclerView {
    private List<View> mNonEmptyViews = Collections.emptyList(); /*emptyList() called to initialize teh list variable to avoid NULL POINTER EXCEPTION*/
    private List<View> mEmptyViews = Collections.emptyList();
    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };

    private void toggleViews() {
        if (getAdapter() != null && !mNonEmptyViews.isEmpty() && !mEmptyViews.isEmpty()) {
            if (getAdapter().getItemCount() == 0) {
                //show all the Empty View
                Util.showViews(mEmptyViews);
                //hide the RecyclerView
                setVisibility(View.GONE);
            } else {
                //show all the
                Util.showViews(mNonEmptyViews);
                //show the RecyclerView
                setVisibility(View.VISIBLE);
                //hide empty view elements
                Util.hideViews(mEmptyViews);
            }
        }
    }

    public BucketRecyclerView(Context context) {
        super(context);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
        }
        mObserver.onChanged();
    }

    public void hideIfEmpty(View... views) {
        mNonEmptyViews = Arrays.asList(views);
    }

    public void showIfEmpty(View... mEmptyView) {
        mEmptyViews = Arrays.asList(mEmptyView);
    }
}
