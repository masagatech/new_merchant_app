package com.goyo.parent.model;

import com.google.gson.annotations.SerializedName;
import com.goyo.parent.forms.OrderStatus;

/**
 * Created by mis on 29-Jul-17.
 */

public class model_expense {

    @SerializedName("hldnm")
    public String hldnm;

    @SerializedName("hlddesc")
    public String hlddesc;

    @SerializedName("hlddate")
    public String hlddate;

    @SerializedName("todt")
    public String todt;


    @SerializedName("expcd")
    public String expcd;

    @SerializedName("expnm")
    public String expnm;

    @SerializedName("expdesc")
    public String expdesc;

    @SerializedName("isactive")
    public boolean isactive;

    @SerializedName("expid")
    public String expid;

    @SerializedName("mob_createdon")
    public String mob_createdon;

    @SerializedName("statusdesc")
    public String statusdesc;

    @SerializedName("appramt")
    public String appramt;

    @SerializedName("name")
    public String name;

    @SerializedName("schoolid")
    public String schoolid;

    @SerializedName("schoolname")
    public String schoolname;

    @SerializedName("schoollogo")
    public String schoollogo;




    public int _id;
    public String _name;
    public String _disc;
    public String _code;
    public String _value;
    public  String _is_server;
    public  String _exp_id;
    public  String _is_active;
    public  String _appr_amt;


    public String get_appr_amt() {
        return _appr_amt;
    }

    public void set_appr_amt(String _appr_amt) {
        this._appr_amt = _appr_amt;
    }

    public String get_exp_id() {
        return _exp_id;
    }

    public void set_exp_id(String _exp_id) {
        this._exp_id = _exp_id;
    }

    public String get_is_active() {
        return _is_active;
    }

    public void set_is_active(String _is_active) {
        this._is_active = _is_active;
    }

    public String get_is_server() {
        return _is_server;
    }

    public void set_is_server(String _is_server) {
        this._is_server = _is_server;
    }

    public String get_value() {
        return _value;
    }

    public void set_value(String _value) {
        this._value = _value;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_disc() {
        return _disc;
    }

    public void set_disc(String _disc) {
        this._disc = _disc;
    }

    public String get_code() {
        return _code;
    }

    public void set_code(String _code) {
        this._code = _code;
    }

    public model_expense( String _exp_id,String _name, String _disc, String _value,String _code,String _is_active,String _is_server, String _appr_amt){
        this._name = _name;
        this._disc = _disc;
        this._code = _code;
        this._value = _value;
        this._exp_id = _exp_id;
        this._is_active = _is_active;
        this._is_server = _is_server;
        this._appr_amt = _appr_amt;
    }


    public OrderStatus status = OrderStatus.ACTIVE;
    public boolean isEnabled = false;
}
