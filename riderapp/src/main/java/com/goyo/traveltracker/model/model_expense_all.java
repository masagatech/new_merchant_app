package com.goyo.traveltracker.model;

/**
 * Created by mis on 14-Aug-17.
 */

public class model_expense_all {


    public String _type;
    public String _disc;
    public String _tags;
    public String _value;
    public  String _is_server;
    public  String _created_by;


    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_disc() {
        return _disc;
    }

    public void set_disc(String _disc) {
        this._disc = _disc;
    }

    public String get_tags() {
        return _tags;
    }

    public void set_tags(String _tags) {
        this._tags = _tags;
    }

    public String get_value() {
        return _value;
    }

    public void set_value(String _value) {
        this._value = _value;
    }

    public String get_is_server() {
        return _is_server;
    }

    public void set_is_server(String _is_server) {
        this._is_server = _is_server;
    }

    public String get_created_by() {
        return _created_by;
    }

    public void set_created_by(String _created_by) {
        this._created_by = _created_by;
    }

    public model_expense_all(String _type, String _value, String _tags, String _disc, String _created_by, String _is_server){
        this._type = _type;
        this._disc = _disc;
        this._value = _value;
        this._value = _value;
        this._tags = _tags;
        this._is_server = _is_server;
        this._created_by = _created_by;
    }
}
