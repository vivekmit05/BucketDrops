package com.bucketdrops.vivek.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bucketdrops.vivek.bucketdrops.adapters.CompleteListener;

/**
 * Created by vivek on 3/4/2018.
 */

public class DialogMark extends DialogFragment {
    ImageButton mBtnClose;
    Button mBtnCompleted;

    private View.OnClickListener mBtnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           switch (v.getId()){
               case R.id.btn_completed:
               markAsComplete();
               break;
           }
           dismiss();
        }
    };
    private CompleteListener mListener;

    private void markAsComplete() {
        Bundle arguments=getArguments();
        if(mListener!=null && arguments!=null){
            int position=arguments.getInt("POSITION");
            mListener.onComplete(position);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mark, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose=view.findViewById(R.id.btn_close);
        mBtnCompleted=view.findViewById(R.id.btn_completed);
        mBtnClose.setOnClickListener(mBtnClickListener);
        mBtnCompleted.setOnClickListener(mBtnClickListener);
    }

    public void setCompleteListener(CompleteListener mCompleteListener) {
        mListener=mCompleteListener;
    }
}
