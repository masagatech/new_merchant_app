package com.goyo.traveltracker.model;

import com.google.gson.annotations.SerializedName;
import com.goyo.traveltracker.forms.OrderStatus;

/**
 * Created by mis on 29-Jul-17.
 */

public class model_expense {




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


    public int _id;
    public String _name;
    public String _disc;
    public String _code;
    public String _value;
    public  String _is_server;
    public  String _exp_id;
    public  String _is_active;


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

    public model_expense( String _exp_id,String _name, String _disc, String _value,String _code,String _is_active,String _is_server){
        this._name = _name;
        this._disc = _disc;
        this._code = _code;
        this._value = _value;
        this._exp_id = _exp_id;
        this._is_active = _is_active;
        this._is_server = _is_server;
    }


    public OrderStatus status = OrderStatus.ACTIVE;
    public boolean isEnabled = false;
}
