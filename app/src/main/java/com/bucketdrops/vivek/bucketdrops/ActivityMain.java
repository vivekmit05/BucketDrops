package com.bucketdrops.vivek.bucketdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bucketdrops.vivek.bucketdrops.adapters.AdapterDrops;

public class ActivityMain extends AppCompatActivity{
    Toolbar mToolbar;
    Button mAddDrop;
    RecyclerView mRecyclerView;

    private View.OnClickListener mBtnAddListener= new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            FragmentDialog dialogAdd=new FragmentDialog();
            dialogAdd.show(getSupportFragmentManager(),"Add Drop");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddDrop=(Button) findViewById(R.id.btnAddDrop);
        mRecyclerView=(RecyclerView) findViewById(R.id.rv_drops);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new AdapterDrops(this));

        mAddDrop.setOnClickListener(mBtnAddListener);
        setSupportActionBar(mToolbar);
        initBackgroundImage();
    }

    private void initBackgroundImage() {
        ImageView ivBackground = (ImageView) findViewById(R.id.iv_background);
        GlideApp.with(this).load(R.drawable.background).centerCrop().into(ivBackground);

    }


}
