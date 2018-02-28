package com.bucketdrops.vivek.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bucketdrops.vivek.bucketdrops.R;
import com.bucketdrops.vivek.bucketdrops.beans.Drop;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vivek on 2/23/2018.
 */

public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener{

    private LayoutInflater mInflater; /*Declared here so that it has global scope in the file*/
    private RealmResults<Drop> mResults;
    public static final String TAG = "Vivek";
    public static final int ITEM = 0;
    public static final int FOOTER = 1;
    private AddListener mAddListener;
    private Realm mRealm;

    public AdapterDrops(Context context, Realm realm,RealmResults<Drop> results) {
        mInflater = LayoutInflater.from(context);
        mRealm=realm;
        update(results);
    }
    public AdapterDrops(Context context, Realm realm,RealmResults<Drop> results,AddListener listener) {
        mInflater = LayoutInflater.from(context);
        update(results);
        mRealm=realm;
        mAddListener=listener;
    }

    public void update(RealmResults<Drop> results) {
        mResults = results;
        notifyDataSetChanged();
        Log.d(TAG, "update: " + mResults.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (mResults == null || position < mResults.size()) {
            return ITEM;
        } else {
            return FOOTER;
        }
    }

    /*Method to generate dummy values*/
    public static ArrayList<String> generateValues() {
        ArrayList<String> dummyValues = new ArrayList<>();
        for (int i = 1; i < 101; i++) {
            dummyValues.add("Item " + i);
        }
        return dummyValues;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View view = mInflater.inflate(R.layout.footer, parent, false);
            FooterHolder holder = new FooterHolder(view,mAddListener);
            return holder;
        } else {
            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            DropHolder holder = new DropHolder(view);
            return holder;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder) {
            DropHolder objDropHolder = (DropHolder) holder;
            Drop objDrop = mResults.get(position);
            objDropHolder.mTextWhat.setText(objDrop.getStrWhat());
        }

        Log.d(TAG, "onBindViewHolder: " + position);
    }

    @Override
    public int getItemCount() {

        if(mResults==null || mResults.isEmpty()){
            return 0;
        }
        else{
            return mResults.size()+1;
        }
    }

    @Override
    public void onSwipe(int position) {
        if(position<mResults.size()){
            mRealm.beginTransaction();
            mResults.get(position).deleteFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }

    }

    public static class DropHolder extends RecyclerView.ViewHolder {

        TextView mTextWhat;

        public DropHolder(View itemView) {
            super(itemView);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button mBtnAddFooter;
        AddListener mListener;

        public FooterHolder(View itemView) {
            super(itemView);
            mBtnAddFooter = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAddFooter.setOnClickListener(this);
        }
        public FooterHolder(View itemView,AddListener listener) {
            super(itemView);
            mBtnAddFooter = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAddFooter.setOnClickListener(this);
            mListener=listener;
        }

        @Override
        public void onClick(View v) {
            mListener.add();
        }
    }
}
