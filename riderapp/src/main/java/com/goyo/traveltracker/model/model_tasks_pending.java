package com.goyo.traveltracker.model;

/**
 * Created by mis on 28-Jul-17.
 */

public class model_tasks_pending {
    int _id;
    String _is_server_send;
    String _Nature_task;
    String _value;
    String _remark;
    String _status_task;
    String _tags;
    String _creat_on;
    String _tsk_id;
    String _exp_id;
    String _exp_value;
    String _exp_disc;
    String _lat;
    String _lon;
    String _time;


    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
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

    public String get_exp_id() {
        return _exp_id;
    }

    public void set_exp_id(String _exp_id) {
        this._exp_id = _exp_id;
    }

    public String get_exp_value() {
        return _exp_value;
    }

    public void set_exp_value(String _exp_value) {
        this._exp_value = _exp_value;
    }

    public String get_exp_disc() {
        return _exp_disc;
    }

    public void set_exp_disc(String _exp_disc) {
        this._exp_disc = _exp_disc;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_is_server_send() {
        return _is_server_send;
    }

    public void set_is_server_send(String _is_server_send) {
        this._is_server_send = _is_server_send;
    }

    public String get_Nature_task() {
        return _Nature_task;
    }

    public void set_Nature_task(String _Nature_task) {
        this._Nature_task = _Nature_task;
    }

    public String get_value() {
        return _value;
    }

    public void set_value(String _value) {
        this._value = _value;
    }

    public String get_remark() {
        return _remark;
    }

    public void set_remark(String _remark) {
        this._remark = _remark;
    }

    public String get_status_task() {
        return _status_task;
    }

    public void set_status_task(String _status_task) {
        this._status_task = _status_task;
    }

    public String get_tags() {
        return _tags;
    }

    public void set_tags(String _tags) {
        this._tags = _tags;
    }

    public String get_creat_on() {
        return _creat_on;
    }

    public void set_creat_on(String _creat_on) {
        this._creat_on = _creat_on;
    }

    public String get_tsk_id() {
        return _tsk_id;
    }

    public void set_tsk_id(String _tsk_id) {
        this._tsk_id = _tsk_id;
    }

    public model_tasks_pending(String tsk_id, String Nature_task, String value, String remark, String status_task, String tags, String creat_on, String is_server_send,String _exp_id,String _exp_value,String _exp_disc, String _lat, String _lon,String _time){
        this._tsk_id = tsk_id;
        this._Nature_task = Nature_task;
        this._value = value;
        this._remark = remark;
        this._tags = tags;
        this._status_task = status_task;
        this._is_server_send = is_server_send;
        this._creat_on = creat_on;
        this._exp_id = _exp_id;
        this._exp_value = _exp_value;
        this._exp_disc = _exp_disc;
        this._lat = _lat;
        this._lon = _lon;
        this._time = _time;
    }
}
