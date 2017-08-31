package com.goyo.traveltracker.model;

import com.google.gson.annotations.SerializedName;
import com.goyo.traveltracker.forms.OrderStatus;

/**
 * Created by mis on 08-Aug-17.
 */

public class modal_leave {

    @SerializedName("frmdt")
    public String frmdt;

    @SerializedName("todt")
    public String todt;

    @SerializedName("statusdesc")
    public String statusdesc;

    @SerializedName("mob_createdon")
    public String mob_createdon;


    int _id;
    String _is_server_send;
    String _leave_from;
    String _leave_to;
    String _leave_type;
    String _leave_details;
    String _leave_created;
    String _leave_status;

    public String get_leave_status() {
        return _leave_status;
    }

    public void set_leave_status(String _leave_status) {
        this._leave_status = _leave_status;
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

    public String get_leave_from() {
        return _leave_from;
    }

    public void set_leave_from(String _leave_from) {
        this._leave_from = _leave_from;
    }

    public String get_leave_to() {
        return _leave_to;
    }

    public void set_leave_to(String _leave_to) {
        this._leave_to = _leave_to;
    }

    public String get_leave_type() {
        return _leave_type;
    }

    public void set_leave_type(String _leave_type) {
        this._leave_type = _leave_type;
    }

    public String get_leave_details() {
        return _leave_details;
    }

    public void set_leave_details(String _leave_details) {
        this._leave_details = _leave_details;
    }

    public String get_leave_created() {
        return _leave_created;
    }

    public void set_leave_created(String _leave_created) {
        this._leave_created = _leave_created;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

        public modal_leave(String _leave_from, String _leave_to, String _leave_type, String _leave_details, String _leave_created, String _is_server_send, String _leave_status){
        this._leave_from = _leave_from;
        this._leave_to = _leave_to;
        this._leave_type = _leave_type;
        this._leave_details = _leave_details;
        this._leave_created = _leave_created;
        this._is_server_send = _is_server_send;
        this._leave_status = _leave_status;
    }

    public OrderStatus status = OrderStatus.ACTIVE;
    public boolean isEnabled = false;
}
