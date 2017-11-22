package com.goyo.marchant.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mis on 25-Sep-17.
 */

public class MyKidsModel {

    @SerializedName("studid")
    public int StudId = 0;

    @SerializedName("nm")
    public String Name = "";

    @SerializedName("div")
    public String  Div = "";

    @SerializedName("gen")
    public String Gen = "";

    @SerializedName("sch")
    public String School = "";

    @SerializedName("activated")
    public Boolean Activated = false;
}
