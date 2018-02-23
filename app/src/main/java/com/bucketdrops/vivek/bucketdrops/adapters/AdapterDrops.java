package com.bucketdrops.vivek.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bucketdrops.vivek.bucketdrops.R;

import java.util.ArrayList;

/**
 * Created by vivek on 2/23/2018.
 */

public class AdapterDrops extends RecyclerView.Adapter<AdapterDrops.DropHolder> {

    private LayoutInflater mInflater; /*Declared here so that it has global scope in the file*/
    private ArrayList<String> mItems=new ArrayList<>();
    public static final String TAG="Vivek";

    public AdapterDrops(Context context) {
        mInflater=LayoutInflater.from(context);
        mItems=generateValues();
    }

    /*Method to generate dummy values*/
    public static ArrayList<String> generateValues() {
        ArrayList<String> dummyValues=new ArrayList<>();
        for (int i=1;i<101;i++){
            dummyValues.add("Item "+i);
        }
        return dummyValues;
    }


    @Override
    public DropHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view= mInflater.inflate(R.layout.row_drop,parent,false);
        DropHolder holder=new DropHolder(view);
        Log.d(TAG, "onCreateViewHolder: ");
        return holder;
    }

    @Override
    public void onBindViewHolder(DropHolder holder, int position) {
        holder.mTextWhat.setText(mItems.get(position));
        Log.d(TAG, "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class DropHolder extends RecyclerView.ViewHolder{

        TextView mTextWhat;
        public DropHolder(View itemView) {
            super(itemView);
            mTextWhat=(TextView) itemView.findViewById(R.id.tv_what);
        }
    }
}
