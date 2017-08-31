package com.goyo.traveltracker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mis on 22-Aug-17.
 */

public class model_trip_map {

    @SerializedName("strtm")
    public String strtm;

    @SerializedName("trpid")
    public String trpid;

    @SerializedName("endtm")
    public String endtm;

    public model_trip_map()
    {}
}

