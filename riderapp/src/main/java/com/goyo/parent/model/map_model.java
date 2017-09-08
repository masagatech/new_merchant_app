package com.goyo.parent.model;

/**
 * Created by mis on 05-Aug-17.
 */

public class map_model {
    String _title;
    String _body;
    String _lat;
    String _lon;
    String _Type;

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_body() {
        return _body;
    }

    public void set_body(String _body) {
        this._body = _body;
    }

    public String get_lat() {
        return _lat;
    }

    public void set_lat(String _lat) {
        this._lat = _lat;
    }

    public String get_lon() {
        return _lon;
    }

    public void set_lon(String _lon) {
        this._lon = _lon;
    }


    public map_model(String _title, String _body, String _lat, String _lon,String _Type){
        this._lat = _lat;
        this._lon = _lon;
        this._title = _title;
        this._body = _body;
        this._Type = _Type;
    }
}
