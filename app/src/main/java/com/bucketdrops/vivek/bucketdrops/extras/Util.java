package com.bucketdrops.vivek.bucketdrops.extras;

import android.view.View;

import java.util.List;

/**
 * Created by vivek on 2/27/2018.
 */

public class Util {
    public static void showViews(List<View> views){
        for(View view:views){
            view.setVisibility(View.VISIBLE);
        }
    }
    public static void hideViews(List<View> views){
        for(View view:views){
            view.setVisibility(View.GONE);
        }
    }
}
