package com.goyo.traveltracker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fajar on 08-Jun-17.
 */

public class model_order_details {


    @SerializedName("dt")
    public String dt;

    @SerializedName("del")
    public int del;

    @SerializedName("ret")
    public int ret;

    @SerializedName("trpdate")
    public String strtm;

    @SerializedName("chkin")
    public String chkin;

    @SerializedName("chkout")
    public String chkout;

    @SerializedName("stops")
    public String stops;

    @SerializedName("task")
    public String task;


    @SerializedName("km")
    public int km;

    public String mDate;
    public String mStops;
    public String mKM;
    public String mCheckIn;
    public String mCheckOut;
    public String mTask;


    public model_order_details(String mDate,String mStops,String mKM,String mCheckIn,String mCheckOut,String mRemark)
    {
        this.mDate = mDate;
        this.mStops = mStops;
        this.mKM = mKM;
        this.mCheckIn = mCheckIn;
        this.mCheckOut = mCheckOut;
        this.mTask = mRemark;
    }

}
