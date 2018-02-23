package com.bucketdrops.vivek.bucketdrops.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vivek on 2/23/2018.
 */

public class Drop extends RealmObject{
    private String strWhat;
    @PrimaryKey
    private long addedtime;
    private long whenTime;
    private boolean boolCompleted;

    /*Parametrised Constructor*/
    public Drop(String strWhat, long addedtime, long whenTime, boolean boolCompleted) {
        this.strWhat = strWhat;
        this.addedtime = addedtime;
        this.whenTime = whenTime;
        this.boolCompleted = boolCompleted;
    }
    /*Defau;t Constructot*/
    public Drop() {
    }

    public String getStrWhat() {
        return strWhat;
    }

    public void setStrWhat(String strWhat) {
        this.strWhat = strWhat;
    }

    public long getAddedtime() {
        return addedtime;
    }

    public void setAddedtime(long addedtime) {
        this.addedtime = addedtime;
    }

    public long getWhenTime() {
        return whenTime;
    }

    public void setWhenTime(long whenTime) {
        this.whenTime = whenTime;
    }

    public boolean isBoolCompleted() {
        return boolCompleted;
    }

    public void setBoolCompleted(boolean boolCompleted) {
        this.boolCompleted = boolCompleted;
    }
}
