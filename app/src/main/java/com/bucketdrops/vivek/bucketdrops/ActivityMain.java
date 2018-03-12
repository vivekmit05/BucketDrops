package com.bucketdrops.vivek.bucketdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bucketdrops.vivek.bucketdrops.adapters.AdapterDrops;
import com.bucketdrops.vivek.bucketdrops.adapters.AddListener;
import com.bucketdrops.vivek.bucketdrops.adapters.CompleteListener;
import com.bucketdrops.vivek.bucketdrops.adapters.Divider;
import com.bucketdrops.vivek.bucketdrops.adapters.Filter;
import com.bucketdrops.vivek.bucketdrops.adapters.MarkListener;
import com.bucketdrops.vivek.bucketdrops.adapters.SimpleTouchCallBack;
import com.bucketdrops.vivek.bucketdrops.beans.Drop;
import com.bucketdrops.vivek.bucketdrops.widgets.BucketRecyclerView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class ActivityMain extends AppCompatActivity {
    public static final String TAG = "vivekMainThread";
    Toolbar mToolbar;
    View mEmptyView;
    Button mAddDrop;
    BucketRecyclerView mRecyclerView;
    Realm mRealm;
    RealmResults<Drop> mResults;
    AdapterDrops mAdapter;

    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            showDialogAdd();
        }
    };

    private AddListener mAddListener = new AddListener() {
        @Override
        public void add() {
            showDialogAdd();
        }
    };

    private MarkListener markListener = new MarkListener() {
        @Override
        public void onMark(int position) {
            showDialogMark(position);
        }
    };

    private CompleteListener mCompleteListener = new CompleteListener() {
        @Override
        public void onComplete(int position) {
            mAdapter.markComplete(position);
        }
    };

    private void showDialogAdd() {
        FragmentDialog dialogAdd = new FragmentDialog();
        dialogAdd.show(getSupportFragmentManager(), "Add Drop");
    }

    private void showDialogMark(int position) {
        DialogMark dialogMark = new DialogMark();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        dialogMark.setArguments(bundle);
        dialogMark.setCompleteListener(mCompleteListener);
        dialogMark.show(getSupportFragmentManager(), "Mark");
    }

    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            mAdapter.update(mResults); /*To call Update method in AdapterDrop so that the change can be reflected after addition of an item*/
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int filterOption=AppBucketDrops.load(this);

        mRealm = Realm.getDefaultInstance();
        loadResult(filterOption);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddDrop = (Button) findViewById(R.id.btnAddDrop);
        mEmptyView = (View) findViewById(R.id.empty_drops);
        mRecyclerView = (BucketRecyclerView) findViewById(R.id.rv_drops);
        try {
            mRecyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mRecyclerView.hideIfEmpty(mToolbar);
        mRecyclerView.showIfEmpty(mEmptyView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterDrops(this, mRealm, mResults, mAddListener, markListener);
        mAdapter.setHasStableIds(true); /*To set animation for recyler view rows*/

        mRecyclerView.setAdapter(mAdapter);

        SimpleTouchCallBack callBack = new SimpleTouchCallBack(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callBack);
        helper.attachToRecyclerView(mRecyclerView);

        mAddDrop.setOnClickListener(mBtnAddListener);

        setSupportActionBar(mToolbar);

        initBackgroundImage();
    }

    private void initBackgroundImage() {
        ImageView ivBackground = (ImageView) findViewById(R.id.iv_background);
        GlideApp.with(this).load(R.drawable.background).centerCrop().into(ivBackground);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean handled = true;
        int filterOption=Filter.NONE;
        switch (id) {
            case R.id.action_Add:
                showDialogAdd();
                break;
            case R.id.action_sort_desecending_dates:
                filterOption=Filter.MOST_TIME_LEFT;
                break;
            case R.id.action_sort_ascending_dates:
                filterOption=Filter.LEAST_TIME_LEFT;
                break;
            case R.id.action_show_complete:
                filterOption=Filter.COMPLETE;
                break;
            case R.id.action_show_incomplete:
                filterOption=Filter.INCOMPLETE;
                break;
            default:
                handled = false;
                break;
        }
        AppBucketDrops.save(this,filterOption);
        loadResult(filterOption);
        return handled;
    }



    private void loadResult(int filterOption) {
        switch (filterOption) {
            case Filter.NONE:
                mResults = mRealm.where(Drop.class).findAllAsync();
                break;
            case Filter.MOST_TIME_LEFT:
                mResults = mRealm.where(Drop.class).sort("whenTime", Sort.DESCENDING).findAllAsync();
                break;
            case Filter.LEAST_TIME_LEFT:
                mResults = mRealm.where(Drop.class).sort("whenTime").findAllAsync();
                break;
            case Filter.COMPLETE:
                mResults = mRealm.where(Drop.class).equalTo("boolCompleted", true).findAllAsync();
                break;
            case Filter.INCOMPLETE:
                mResults = mRealm.where(Drop.class).equalTo("boolCompleted", false).findAllAsync();
                break;
        }
        mResults.addChangeListener(mChangeListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mResults.addChangeListener(mChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mResults.removeChangeListener(mChangeListener);
    }
}
