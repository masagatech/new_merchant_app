package com.goyo.parent.model;

import com.google.gson.annotations.SerializedName;
import com.goyo.parent.forms.OrderStatus;

/**
 * Created by fajar on 27-May-17.
 */

public class model_pending {

    @SerializedName("studentname")
    public String studentname;

    @SerializedName("studentphoto")
    public String studentphoto;

    @SerializedName("gender")
    public String gender;

    @SerializedName("schoolname")
    public String schoolname;

    @SerializedName("enrollmentno")
    public String enrollmentno;

    @SerializedName("classname")
    public String classname;

    @SerializedName("mothername")
    public String mothername;

    @SerializedName("fathername")
    public String fathername;

    @SerializedName("mobileno1")
    public String mobileno1;

    @SerializedName("mobileno2")
    public String mobileno2;


    public OrderStatus status = OrderStatus.ACTIVE;

    public model_pending()
    {}

}
