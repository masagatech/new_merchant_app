package com.goyo.parent.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mis on 14-Sep-17.
 */

public class modal_data {


    @SerializedName("grpid")
    public String grpid;
    @SerializedName("grpname")
    public String grpname;
    @SerializedName("countannc")
    public String countannc;
    @SerializedName("createdon")
    public String createdon;

    @SerializedName("frmdt")
    public String frmdt;

    @SerializedName("todt")
    public String todt;

    @SerializedName("lvtype")
    public String lvtype;

    @SerializedName("reason")
    public String reason;

    @SerializedName("appldate")
    public String appldate;

    public boolean isEnabled = false;


}
