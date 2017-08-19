package com.goyo.traveltracker.model;

import com.google.gson.annotations.SerializedName;
import com.goyo.traveltracker.forms.OrderStatus;

/**
 * Created by fajar on 06-Jun-17.
 */

public class model_completed {

    @SerializedName("elid")
    public String elid;

    @SerializedName("frmdt")
    public String frmdt;

    @SerializedName("todt")
    public String todt;

    @SerializedName("restype")
    public String restype;


    @SerializedName("ordid")
    public String ordid;


    @SerializedName("stpnm")
    public String stpnm;

    @SerializedName("tagnm")
    public String tagnm;

    @SerializedName("remark")
    public String remark1;

    @SerializedName("remark1")
    public String remark2;

    @SerializedName("remark2")
    public String remark3;

    @SerializedName("remark3")
    public String remark33;

    @SerializedName("createdby")
    public String createdby;

    @SerializedName("createdon")
    public String createdon;

    @SerializedName("stpdesc")
    public String stpdesc;

    @SerializedName("stpid")
    public String stpid;

    @SerializedName("ordno")
    public String ordno;

    @SerializedName("olnm")
    public String olnm;

    @SerializedName("orddid")
    public String orderdetailid;

    @SerializedName("amtrec")
    public Double amtrec;

    @SerializedName("stsi")
    public int stsi;

    @SerializedName("cnm")
    public String custname;

    @SerializedName("mob")
    public String custmob;

    @SerializedName("cadr")
    public String custaddr;

    @SerializedName("amt")
    public Double amtcollect;

    @SerializedName("trpid")
    public String tripid;

    @SerializedName("rm")
    public String remark;

    @SerializedName("dtm")
    public String deltime;

    @SerializedName("dltm")
    public String dltm;

    public boolean isEnabled = false;

    public OrderStatus status = OrderStatus.ACTIVE;
}
