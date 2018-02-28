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

import io.realm.RealmResults;

/**
 * Created by vivek on 2/23/2018.
 */

public class AdapterDrops extends RecyclerView.Adapter<AdapterDrops.DropHolder> {

    private LayoutInflater mInflater; /*Declared here so that it has global scope in the file*/
    private RealmResults<Drop> mResults;
    public static final String TAG = "Vivek";


    public AdapterDrops(Context context, RealmResults<Drop> results) {
        mInflater = LayoutInflater.from(context);
        update(results);
    }

    public void update(RealmResults<Drop> results) {
        mResults = results;
        notifyDataSetChanged();
        Log.d(TAG, "update: " + mResults.size());
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
    public DropHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            DropHolder holder = new DropHolder(view);
            return holder;


    }

    @Override
    public void onBindViewHolder(DropHolder holder, int position) {


            Drop objDrop = mResults.get(position);
            holder.mTextWhat.setText(objDrop.getStrWhat());


    }

    @Override
    public int getItemCount() {

        return mResults.size();
    }

    public static class DropHolder extends RecyclerView.ViewHolder {

        TextView mTextWhat;

        public DropHolder(View itemView) {
            super(itemView);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {

        Button mBtnAddFooter;

        public FooterHolder(View itemView) {
            super(itemView);
            mBtnAddFooter = (Button) itemView.findViewById(R.id.btn_footer);
        }
    }
}
