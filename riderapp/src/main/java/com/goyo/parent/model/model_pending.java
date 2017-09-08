package com.goyo.parent.model;

import com.google.gson.annotations.SerializedName;
import com.goyo.parent.forms.OrderStatus;

/**
 * Created by fajar on 27-May-17.
 */

public class model_pending {

    @SerializedName("orddid")
    public int orderdetailid;

    @SerializedName("tskid")
    public String tskid;

    @SerializedName("task")
    public String task;

    @SerializedName("frmdt")
    public String frmdt;

    @SerializedName("cuid")
    public String cuid;

    @SerializedName("todt")
    public String todt;

    @SerializedName("tntype")
    public String tntype;

    @SerializedName("value")
    public String value;

    @SerializedName("tstype")
    public String tstype;


    @SerializedName("ordid")
    public int ordid;

    @SerializedName("ordno")
    public String ordno;

    @SerializedName("remark")
    public String remark;

    @SerializedName("olnm")
    public String olnm;

    @SerializedName("cnm")
    public String custname;

    @SerializedName("mob")
    public String custmob;

    @SerializedName("cadr")
    public String custaddr;

    @SerializedName("orddate")
    public String orddate;

    @SerializedName("amt")
    public Double amtcollect;

    @SerializedName("pctm")
    public String picktime;

    @SerializedName("deldate")
    public String deldate;

    @SerializedName("dtm")
    public String deltime;

    @SerializedName("dltm")
    public String dltm;

    @SerializedName("trpid")
    public String tripid;

    @SerializedName("stsi")
    public String stats;


    public OrderStatus status = OrderStatus.ACTIVE;

    public model_pending()
    {}

}
