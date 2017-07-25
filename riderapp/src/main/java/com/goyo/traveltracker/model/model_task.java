package com.goyo.traveltracker.model;

import com.goyo.traveltracker.forms.OrderStatus;

/**
 * Created by mis on 14-Jul-17.
 */

public class model_task {
    int _id;
    String _is_server_send;
    String _title;
    String _body;
    String _lat;
    String _lon;
    String _tags;
    String _creat_on;
    String _time;

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_tags() {
        return _tags;
    }

    public void set_tags(String _tags) {
        this._tags = _tags;
    }

    public String get_is_server_send() {
        return _is_server_send;
    }

    public void set_is_server_send(String _is_server_send) {
        this._is_server_send = _is_server_send;
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
    public model_task(String s, String title, String body, String lat, String lon, String tags, String creat_on, String is_server_send, String time){

    }

    // constructor
    public model_task(String _title, String _body, String _lat,String _lon,String tags,String _creat_on,String _is_server_send,String _time_){
        this._title = _title;
        this._body = _body;
        this._lat = _lat;
        this._lon = _lon;
        this._tags = tags;
        this._creat_on = _creat_on;
        this._is_server_send = _is_server_send;
        this._time = _time_;
    }

    public OrderStatus status = OrderStatus.ACTIVE;
    public boolean isEnabled = false;
}
