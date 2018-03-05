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

import java.util.Calendar;

import io.realm.Realm;

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
            int id = v.getId();
            switch (id) {
                case R.id.btn_add_dialog:
                    addAction();
                    break;
            }
            dismiss();
        }
    };

    //TODO process Date
    private void addAction() {

        String strWhat = etInputWhat.getText().toString();
        long currentTime = System.currentTimeMillis();
        String date=dpInputWhen.getDayOfMonth()+"/"+dpInputWhen.getMonth()+"/"+dpInputWhen.getYear();
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,dpInputWhen.getDayOfMonth());
        calendar.set(Calendar.MONTH,dpInputWhen.getMonth());
        calendar.set(Calendar.YEAR,dpInputWhen.getYear());
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Realm objRealm = Realm.getDefaultInstance();
        Drop objDrop = new Drop(strWhat, currentTime, calendar.getTimeInMillis(), false);
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
        return inflater.inflate(R.layout.dialog_add_xml, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ibClose = (ImageButton) view.findViewById(R.id.iv_close_dialog);
        etInputWhat = (EditText) view.findViewById(R.id.et_drop_dialog);
        dpInputWhen = (DatePicker) view.findViewById(R.id.bpv_dialog);
        btnAddIt = (Button) view.findViewById(R.id.btn_add_dialog);

        ibClose.setOnClickListener(btnClickListener);
        btnAddIt.setOnClickListener(btnClickListener);
    }
}
