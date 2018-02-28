package com.bucketdrops.vivek.bucketdrops.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by vivek on 3/1/2018.
 */

public class SimpleTouchCallBack extends ItemTouchHelper.Callback {
    private SwipeListener mListener;
    public SimpleTouchCallBack(SwipeListener listener) {
        mListener=listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0,ItemTouchHelper.END);
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListener.onSwipe(viewHolder.getLayoutPosition());

    }
}
