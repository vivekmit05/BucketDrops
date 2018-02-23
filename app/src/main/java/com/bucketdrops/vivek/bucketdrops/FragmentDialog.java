package com.bucketdrops.vivek.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bucketdrops.vivek.bucketdrops.beans.Drop;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by vivek on 2/22/2018.
 */

public class FragmentDialog extends DialogFragment {

    private ImageButton ibClose;
    private EditText etInputWhat;
    private DatePicker dpInputWhen;
    private Button btnAddIt;


    /*Click Listener for close button in dialog fragment*/
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch (id){
                case R.id.btn_add_dialog:
                    addAction();
                    break;
            }
            dismiss();
        }
    };

    //TODO process Date
    private void addAction() {

        String strWhat=etInputWhat.getText().toString();
        long currentTime=System.currentTimeMillis();


        Realm objRealm=Realm.getDefaultInstance();
        Drop objDrop=new Drop(strWhat,currentTime,0,false);
        objRealm.beginTransaction();
        objRealm.copyToRealm(objDrop);
        objRealm.commitTransaction();
        objRealm.close();
    }

    public FragmentDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_xml,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ibClose=(ImageButton) view.findViewById(R.id.iv_close_dialog);
        etInputWhat=(EditText) view.findViewById(R.id.et_drop_dialog);
        dpInputWhen=(DatePicker) view.findViewById(R.id.bpv_dialog);
        btnAddIt=(Button) view.findViewById(R.id.btn_add_dialog);

        ibClose.setOnClickListener(btnClickListener);
        btnAddIt.setOnClickListener(btnClickListener);
    }
}
