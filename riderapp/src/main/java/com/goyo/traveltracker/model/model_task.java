package com.goyo.traveltracker.model;

/**
 * Created by mis on 14-Jul-17.
 */

public class model_task {
    int _id;
    int _is_server_send;
    String _title;
    String _body;
    String _lat;
    String _lon;
    String _empl_id;
    String _creat_on;

    public String get_empl_id() {
        return _empl_id;
    }

    public int get_is_server_send() {
        return _is_server_send;
    }

    public void set_is_server_send(int _is_server_send) {
        this._is_server_send = _is_server_send;
    }

    public void set_empl_id(String _empl_id) {
        this._empl_id = _empl_id;
    }

    public String get_creat_on() {
        return _creat_on;
    }

    public void set_creat_on(String _creat_on) {
        this._creat_on = _creat_on;
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    // Empty constructor
    public model_task(){

    }
    // constructor
    public model_task(int id, String title, String body, String lat,String lon,String emp_id,String creat_on,int is_server_send){
        this._id = id;
        this._title = title;
        this._body = body;
        this._lat = lat;
        this._lon = lon;
        this._empl_id = emp_id;
        this._creat_on = creat_on;
        this._is_server_send = is_server_send;
    }

    // constructor
    public model_task(String _title, String _body, String _lat,String _lon,String _emp_id,String _creat_on,int _is_server_send){
        this._title = _title;
        this._body = _body;
        this._lat = _lat;
        this._lon = _lon;
        this._empl_id = _emp_id;
        this._creat_on = _creat_on;
        this._is_server_send = _is_server_send;
    }
}
