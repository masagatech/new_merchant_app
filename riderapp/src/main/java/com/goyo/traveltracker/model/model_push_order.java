package com.goyo.traveltracker.model;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajar on 29-May-17.
 */

public class model_push_order {



    @SerializedName("ordid")
    public String ordid;

    @SerializedName("ordno")
    public JsonArray ordno;

    @SerializedName("olnm")
    public String olnm;

    @SerializedName("city")
    public String city;

    @SerializedName("area")
    public String area;

    @SerializedName("pchtm")
    public String pchtm;

    @SerializedName("nm")
    public String nm;

    @SerializedName("totamt")
    public Double totamt;

    @SerializedName("rdrid")
    public Integer rdrid;

    @SerializedName("geoloc")
    public loc geoloc;

       public class loc {

        @SerializedName("x")
        public String lat;

        @SerializedName("y")
        public String lon;


    }

    public boolean isEnabled = false;

    public model_push_order() {
    }
}

