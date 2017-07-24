package com.goyo.traveltracker.model;

/**
 * Created by mis on 20-Jul-17.
 */

public class model_tag {

    int _id;
    String _is_server_send;
    String _title;
    String _rem_1;
    String _rem_2;
    String _rem_3;
    String _empl_id;
    String _creat_on;

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

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_rem_1() {
        return _rem_1;
    }

    public void set_rem_1(String _rem_1) {
        this._rem_1 = _rem_1;
    }

    public String get_rem_2() {
        return _rem_2;
    }

    public void set_rem_2(String _rem_2) {
        this._rem_2 = _rem_2;
    }

    public String get_rem_3() {
        return _rem_3;
    }

    public void set_rem_3(String _rem_3) {
        this._rem_3 = _rem_3;
    }

    public String get_empl_id() {
        return _empl_id;
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




    // constructor
    public model_tag(String title, String rem_1, String rem_2,String rem_3,String emp_id,String creat_on,String is_server_send){
        this._title = title;
        this._rem_1 = rem_1;
        this._rem_2 = rem_2;
        this._rem_3 = rem_3;
        this._empl_id = emp_id;
        this._creat_on = creat_on;
        this._is_server_send = is_server_send;
    }
}
